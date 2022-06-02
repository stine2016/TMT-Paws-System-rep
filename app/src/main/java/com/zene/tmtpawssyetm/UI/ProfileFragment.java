package com.zene.tmtpawssyetm.UI;

import static android.app.Activity.RESULT_OK;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.zene.tmtpawssyetm.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment implements PopupMenu.OnMenuItemClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    private EditText Name;
    private Button cancel, save;
    private TextView dummy;
    private Uri imageUri;

    TextView nameTextView, emailTextView, phoneTextView, serialTextView;
    Button profileSettings;
    FirebaseDatabase firebaseDatabase;
    FirebaseUser user;
    DatabaseReference databaseReference;
    FirebaseAuth fAuth;
    ImageView profileImage;
    StorageReference storageReference = FirebaseStorage.getInstance().getReference();

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        nameTextView = view.findViewById(R.id.name);
        emailTextView = view.findViewById(R.id.email);
        phoneTextView = view.findViewById(R.id.phone);
        serialTextView = view.findViewById(R.id.serial);
        profileSettings = view.findViewById(R.id.profileSettings);
        dummy = view.findViewById(R.id.dummy);

        isUser();

        profileSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopup(view);
            }
        });

        return view;
    }



    private void isUser() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        fAuth = FirebaseAuth.getInstance();
        user = fAuth.getCurrentUser();

        String emailPattern = "[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*"
                + "      @[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})";

        emailPattern = "(?<emailHead>[_A-Za-z0-9-\\+]{1,3})+?(?<replacementEmailPart>[_A-Za-z0-9-\\+]*)*?(?<emailTail>@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})){1}";

        Pattern p = Pattern.compile(emailPattern);

        databaseReference = firebaseDatabase.getReference("userInfo").child(user.getUid());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String name = snapshot.child("name").getValue(String.class);
                    String email = snapshot.child("email").getValue(String.class);
                    String phonenumber = snapshot.child("phonenumber").getValue(String.class);
                    String serialnumber = snapshot.child("serialnumber").getValue(String.class);

                    Matcher m = p.matcher(email);

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

                    dummy.setText(email);
                    nameTextView.setText(name);
                    emailTextView.setText(sb.toString());
                    phoneTextView.setText(phonenumber);
                    serialTextView.setText(serialnumber);
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

    public void showPopup(View v){
        PopupMenu popupMenu = new PopupMenu(getContext(), v);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.inflate(R.menu.profile_menu);
        popupMenu.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()){
//            case R.id.editProfile:
//                Toast.makeText(getContext(), "sample", Toast.LENGTH_SHORT).show();
//
//                return true;
            case R.id.changePassword:
//                Toast.makeText(getContext(), "password", Toast.LENGTH_SHORT).show();
                AppCompatActivity activity = (AppCompatActivity) getContext();
                ChangeFragment changeFragment = new ChangeFragment();
                Bundle bundle = new Bundle();
                bundle.putString("email", dummy.toString());
                changeFragment.setArguments(bundle);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, changeFragment).addToBackStack(null).commit();
                return true;
            default:
                return false;
        }
    }

    public void createNewProfileDialog(){
        builder = new AlertDialog.Builder(getContext());
        final View profilePopupView = getLayoutInflater().inflate(R.layout.popup_profile, null);
    }
}