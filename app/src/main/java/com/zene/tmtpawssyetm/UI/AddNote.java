package com.zene.tmtpawssyetm.UI;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.zene.tmtpawssyetm.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddNote#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddNote extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    FirebaseFirestore fStore;
    EditText noteTitle,noteContent, dateTimeIn, dateTimeOut;
    TextView first, second;
    FirebaseUser user;

    public AddNote() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddNote.
     */
    // TODO: Rename and change types and number of parameters
    public static AddNote newInstance(String param1, String param2) {
        AddNote fragment = new AddNote();
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
        View view = inflater.inflate(R.layout.fragment_add_note, container, false);

        fStore = FirebaseFirestore.getInstance();
        noteContent = view.findViewById(R.id.addNoteContent);
        noteTitle = view.findViewById(R.id.addNoteTitle);
        dateTimeIn = view.findViewById(R.id.dateTimeIn);
        dateTimeOut = view.findViewById(R.id.dateTimeOut);
        first = view.findViewById(R.id.first);
        second = view.findViewById(R.id.second);

        dateTimeIn.setInputType(InputType.TYPE_NULL);
        dateTimeOut.setInputType(InputType.TYPE_NULL);

        user = FirebaseAuth.getInstance().getCurrentUser();

        first.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateDialog(dateTimeIn);
            }
        });

        second.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateDialog(dateTimeOut);
            }
        });

        FloatingActionButton fab = view.findViewById(R.id.saveNoteFloat);
        fab.setOnClickListener(view1 -> {
            String nTitle = noteTitle.getText().toString();
            String nContent = noteContent.getText().toString();
            String nDateTimeIn = dateTimeIn.getText().toString();
            String nDateTimeOut = dateTimeOut.getText().toString();

            if(nTitle.isEmpty() || nContent.isEmpty() || nDateTimeIn.isEmpty() || nDateTimeOut.isEmpty()){
                Toast.makeText(getContext(), "Can not Save note with Empty Field.", Toast.LENGTH_SHORT).show();
                return;
            }

            DocumentReference docref = fStore.collection("notes").document(user.getUid()).collection("myNotes").document();
            Map<String,Object> note = new HashMap<>();
            note.put("firstDate", nDateTimeIn);
            note.put("secondDate", nDateTimeOut);
            note.put("title",nTitle);
            note.put("content",nContent);
            docref.set(note).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    AppCompatActivity activity = (AppCompatActivity) view.getContext();
                    Toast.makeText(getContext(), "Note Added.", Toast.LENGTH_SHORT).show();
                    activity.onBackPressed();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getContext(), "Error, Try again.", Toast.LENGTH_SHORT).show();
                }
            });

        });

        return view;
    }

    private void showDateDialog(EditText dateTimeIn) {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        calendar.set(Calendar.MINUTE, minute);

                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("E, dd MMM yyyy hh:mm a");

                        dateTimeIn.setText(simpleDateFormat.format(calendar.getTime()));
                    }
                };

                new TimePickerDialog(getContext(), timeSetListener, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false).show();
            }
        };

        new DatePickerDialog(getContext(), dateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }
}