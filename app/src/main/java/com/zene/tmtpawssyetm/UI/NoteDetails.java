package com.zene.tmtpawssyetm.UI;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.zene.tmtpawssyetm.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NoteDetails#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NoteDetails extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public NoteDetails() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NoteDetails.
     */
    // TODO: Rename and change types and number of parameters
    public static NoteDetails newInstance(String param1, String param2) {
        NoteDetails fragment = new NoteDetails();
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
        View view = inflater.inflate(R.layout.fragment_note_details, container, false);
        String titles = this.getArguments().getString("title");
        String contents = this.getArguments().getString("content");
        String firstDate = this.getArguments().getString("first");
        String secondDate = this.getArguments().getString("second");
        String noteId = this.getArguments().getString("noteId");
  
        TextView content = view.findViewById(R.id.addNoteContent);
        TextView title = view.findViewById(R.id.addNoteTitle);
        TextView first = view.findViewById(R.id.first);
        TextView second = view.findViewById(R.id.second);

        content.setMovementMethod(new ScrollingMovementMethod());

        title.setText(titles);
        content.setText(contents);
        first.setText(firstDate);
        second.setText(secondDate);

        FloatingActionButton fab = view.findViewById(R.id.editNoteFloat);
        fab.setOnClickListener(view1 -> {
            AppCompatActivity activity = (AppCompatActivity) view1.getContext();
            EditNote editNote = new EditNote();
            Bundle bundle = new Bundle();
            bundle.putString("title", titles);
            bundle.putString("content", contents);
            bundle.putString("first", firstDate);
            bundle.putString("second", secondDate);
            bundle.putString("noteId", noteId);
            editNote.setArguments(bundle);
            activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, editNote).addToBackStack(null).commit();
        });

        return view;
    }

}