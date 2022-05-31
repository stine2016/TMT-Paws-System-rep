package com.zene.tmtpawssyetm.UI;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zene.tmtpawssyetm.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link notificationdetails#newInstance} factory method to
 * create an instance of this fragment.
 */
public class notificationdetails extends Fragment {

    TextView textView3, textView6, temperature2;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public notificationdetails() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment notificationdetails.
     */
    // TODO: Rename and change types and number of parameters
    public static notificationdetails newInstance(String param1, String param2) {
        notificationdetails fragment = new notificationdetails();
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
        View view = inflater.inflate(R.layout.fragment_notificationdetails, container, false);
        String notification = this.getArguments().getString("notification");
        String date = this.getArguments().getString("date");
        String temp = this.getArguments().getString("temp");

        textView3 = view.findViewById(R.id.textView3);
        textView6 = view.findViewById(R.id.textView6);
        temperature2 = view.findViewById(R.id.temperature2);

        textView3.setText(notification);
        textView6.setText(date);
        temperature2.setText(temp + "°C");

        return view;
    }
}