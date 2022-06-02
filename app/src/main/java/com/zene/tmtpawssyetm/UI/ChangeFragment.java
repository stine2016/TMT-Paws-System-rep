package com.zene.tmtpawssyetm.UI;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.safetynet.SafetyNet;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.zene.tmtpawssyetm.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChangeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChangeFragment extends Fragment {
    TextView email, dummy;
    CheckBox checkBox;
    MaterialButton materialButton;
    GoogleApiClient googleApiClient;
    String Sitekey = "6LdFyzggAAAAADs4dCGkPXdUbZ72-o4bq3DIvmg5";
    FirebaseDatabase firebaseDatabase;
    FirebaseUser user;
    DatabaseReference databaseReference;
    FirebaseAuth fAuth;
    String email1;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ChangeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChangeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChangeFragment newInstance(String param1, String param2) {
        ChangeFragment fragment = new ChangeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_change, container, false);

        email = view.findViewById(R.id.email);
        materialButton = view.findViewById(R.id.ForgotButton);
        dummy = view.findViewById(R.id.dummy);

        firebaseDatabase = FirebaseDatabase.getInstance();
        fAuth = FirebaseAuth.getInstance();
        user = fAuth.getCurrentUser();

        isUser();

        materialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email1 = dummy.getText().toString();

                if(email1.isEmpty()){
                    Toast.makeText(getContext(), "Please provide your email", Toast.LENGTH_SHORT).show();
                }
                else{
                    fAuth.sendPasswordResetEmail(email1)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(getContext(), "Check your email", Toast.LENGTH_SHORT).show();
                                        ProfileFragment profileFragment = new ProfileFragment();
                                        getActivity().getSupportFragmentManager().beginTransaction()
                                                .replace(((ViewGroup)getView().getParent()).getId(), profileFragment, "findThisFragment")
                                                .addToBackStack(null)
                                                .commit();
                                    }
                                    else{
                                        Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });


        return view;
    }

    private void isUser() {


        String emailPattern = "[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*"
                + "      @[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})";

        emailPattern = "(?<emailHead>[_A-Za-z0-9-\\+]{1,3})+?(?<replacementEmailPart>[_A-Za-z0-9-\\+]*)*?(?<emailTail>@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})){1}";

        Pattern p = Pattern.compile(emailPattern);

        databaseReference = firebaseDatabase.getReference("userInfo").child(user.getUid());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String emails = snapshot.child("email").getValue(String.class);

                    Matcher m = p.matcher(emails);

                    StringBuffer sb = new StringBuffer();
                    while (m.find()) {
                        String replStr = m.group("replacementEmailPart");
                        if (replStr != null) {
                            replStr = replStr.replaceAll("[_A-Za-z0-9-\\+]", "*");
                        } else {
                            replStr = "****";
                        }
                        m.appendReplacement(sb, m.group("emailHead")
                                + replStr
                                + m.group("emailTail"));
                    }
                    m.appendTail(sb);

                    dummy.setText(emails);
                    email.setText("Are you sure you want to change your password using this email:"+ sb.toString());
                }
                else{
                    Toast.makeText(getContext(), "Intruder Alert!!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Fail to get data.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}