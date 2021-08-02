package com.artushock.notes.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import com.artushock.notes.R;
import com.artushock.notes.data.Note;
import com.google.android.material.textfield.TextInputEditText;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddNoteFragment extends Fragment {

    private TextInputEditText noteCaptureInputText;
    private TextInputEditText noteDescriptionInputText;
    private TextInputEditText noteDateInputText;
    private TextInputEditText noteContentInputText;

    long date;

    private Button addNoteButton;
    private Button cancelNoteButton;


    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getParentFragmentManager().setFragmentResultListener("requestForAddNoteDate", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull @NotNull String requestKey, @NonNull @NotNull Bundle result) {
                date = result.getLong("addNoteDate");
            }
        });

        if (date == 0) {
            date = new Date().getTime();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        setDateText();
    }

    private void setDateText() {
        String pattern = "dd.MM.yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        noteDateInputText.setText(simpleDateFormat.format(date));
    }

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

        setDateText();

        noteDateInputText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                noteDateInputTextHandling(v);
                return true;
            }
        });

        noteContentInputText = view.findViewById(R.id.edit_note_content_input_edit_text);

        addNoteButton = view.findViewById(R.id.edit_note_button);
        addNoteButton.setOnClickListener(this::addNoteButtonHandling);

        cancelNoteButton = view.findViewById(R.id.cancel_edit_note_button);
        cancelNoteButton.setOnClickListener(this::cancelNoteButtonHandling);
    }

    private void noteDateInputTextHandling(View v) {
        getParentFragmentManager()
                .beginTransaction()
                .addToBackStack(null)
                .replace(R.id.fragment_container, new SetDateFragment())
                .commit();
    }

    private void cancelNoteButtonHandling(View v) {
        getParentFragmentManager().popBackStack();
    }

    private void addNoteButtonHandling(View v) {

        Note newNote = new Note(
                noteCaptureInputText.getText().toString(),
                noteDescriptionInputText.getText().toString(),
                date,
                noteContentInputText.getText().toString()
        );

        Bundle result = new Bundle();
        result.putParcelable("addNewNote", newNote);
        getParentFragmentManager().setFragmentResult("requestForAddingNote", result);
        getParentFragmentManager().popBackStack();

        Toast.makeText(getContext(), "From addNoteButtonHandling()", Toast.LENGTH_SHORT).show();
    }
}