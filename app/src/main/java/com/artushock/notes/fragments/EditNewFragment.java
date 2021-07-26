package com.artushock.notes.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.artushock.notes.R;
import com.google.android.material.textfield.TextInputEditText;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditNewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditNewFragment extends Fragment {

    private TextInputEditText noteCaptureInputText;
    private TextInputEditText noteDescriptionInputText;
    private TextInputEditText noteDateInputText;
    private TextInputEditText noteContentInputText;
    private Button addNoteButton;
    private Button cancelNoteButton;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public EditNewFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EditNewFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EditNewFragment newInstance(String param1, String param2) {
        EditNewFragment fragment = new EditNewFragment();
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

        View view = inflater.inflate(R.layout.fragment_edit_new, container, false);

        initView(view);

        return view;
    }

    private void initView(View view) {
        noteCaptureInputText = view.findViewById(R.id.note_capture_input_edit_text);
        noteDescriptionInputText = view.findViewById(R.id.note_description_input_edit_text);
        noteDateInputText = view.findViewById(R.id.note_date_input_edit_text);
        noteContentInputText = view.findViewById(R.id.note_content_input_edit_text);
        addNoteButton = view.findViewById(R.id.add_note_button);
        cancelNoteButton = view.findViewById(R.id.cancel_note_button);

        addNoteButton.setOnClickListener(v -> addNoteButtonHandling(v));
        cancelNoteButton.setOnClickListener(v -> cancelNoteButtonHandling(v));
    }

    private void cancelNoteButtonHandling(View v) {
        //TODO
        Toast.makeText(getContext(), "From cancelNoteButtonHandling()", Toast.LENGTH_SHORT).show();
    }

    private void addNoteButtonHandling(View v) {
        //TODO
        Toast.makeText(getContext(), "From addNoteButtonHandling()", Toast.LENGTH_SHORT).show();
    }
}