package com.zene.tmtpawssyetm;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditNote#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditNote extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    EditText editNoteTitle,editNoteContent;
    FirebaseFirestore fStore;
    ProgressBar spinner;
    FirebaseUser user;

    public EditNote() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EditNote.
     */
    // TODO: Rename and change types and number of parameters
    public static EditNote newInstance(String param1, String param2) {
        EditNote fragment = new EditNote();
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
        View view = inflater.inflate(R.layout.fragment_edit_note, container, false);
        String titles = this.getArguments().getString("title");
        String contents = this.getArguments().getString("content");
        String noteId = this.getArguments().getString("noteId");

        fStore = fStore.getInstance();
        spinner = view.findViewById(R.id.progressBar2);
        user = FirebaseAuth.getInstance().getCurrentUser();

        editNoteContent = view.findViewById(R.id.editNoteContent);
        editNoteTitle = view.findViewById(R.id.editNoteTitle);

        editNoteTitle.setText(titles);
        editNoteContent.setText(contents);

        FloatingActionButton fab = view.findViewById(R.id.saveEditedNote);
        fab.setOnClickListener((v) -> {
            String nTitle = editNoteTitle.getText().toString();
            String nContent = editNoteContent.getText().toString();

            if(nTitle.isEmpty() || nContent.isEmpty()){
                Toast.makeText(getContext(), "Can not Save note with Empty Field.", Toast.LENGTH_SHORT).show();
                return;
            }

            spinner.setVisibility(View.VISIBLE);

            // save note

            DocumentReference docref = fStore.collection("notes").document(user.getUid()).collection("myNotes").document(noteId);

            Map<String,Object> note = new HashMap<>();
            note.put("title",nTitle);
            note.put("content",nContent);

            docref.update(note).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(getContext(), "Note Saved.", Toast.LENGTH_SHORT).show();
                    NotesFragment notesFragment = new NotesFragment();
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(((ViewGroup)getView().getParent()).getId(), notesFragment, "findThisFragment")
                            .addToBackStack(null)
                            .commit();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getContext(), "Error, Try again.", Toast.LENGTH_SHORT).show();
                    spinner.setVisibility(View.VISIBLE);
                }
            });
        });

        return view;
    }
}