package com.artushock.notes.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.artushock.notes.R;
import com.google.android.material.textfield.TextInputEditText;

public class EditNewFragment extends Fragment {

    private TextInputEditText noteCaptureInputText;
    private TextInputEditText noteDescriptionInputText;
    private TextInputEditText noteDateInputText;
    private TextInputEditText noteContentInputText;
    private Button addNoteButton;
    private Button cancelNoteButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_edit_new, container, false);
        initView(view);

        return view;
    }

    private void initView(View view) {
        noteCaptureInputText = view.findViewById(R.id.edit_note_capture_input_edit_text);
        noteDescriptionInputText = view.findViewById(R.id.edit_note_description_input_edit_text);

        noteDateInputText = view.findViewById(R.id.edit_note_date_input_edit_text);
        noteDateInputText.setOnClickListener(this::noteDateInputTextHandling);
        noteContentInputText = view.findViewById(R.id.edit_note_content_input_edit_text);

        addNoteButton = view.findViewById(R.id.edit_note_button);
        addNoteButton.setOnClickListener(this::addNoteButtonHandling);

        cancelNoteButton = view.findViewById(R.id.cancel_edit_note_button);
        cancelNoteButton.setOnClickListener(this::cancelNoteButtonHandling);


    }

    private void noteDateInputTextHandling(View v) {

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