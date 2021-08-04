package com.artushock.notes.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.artushock.notes.NoteActivity;
import com.artushock.notes.R;
import com.artushock.notes.data.Note;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.UUID;

public class AddNoteFragment extends Fragment {
    public static final String REQUEST_KEY_FOR_ADD_NOTE_DATE = "requestForAddNoteDate";
    public static final String KEY_ADD_NOTE_DATE = "addNoteDate";
    private TextInputEditText noteCaptureInputText;
    private TextInputEditText noteDescriptionInputText;
    private TextInputEditText noteDateInputText;
    private TextInputEditText noteContentInputText;
    long date;

    private NoteActivity activity;


    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activity = (NoteActivity) getActivity();

        getParentFragmentManager().setFragmentResultListener(REQUEST_KEY_FOR_ADD_NOTE_DATE, this, (requestKey, result) -> date = result.getLong(KEY_ADD_NOTE_DATE));

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
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, Locale.getDefault());
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
        initTextInputEditTexts(view);

        initButtons(view);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initTextInputEditTexts(View view) {
        noteCaptureInputText = view.findViewById(R.id.edit_note_capture_input_edit_text);
        noteDescriptionInputText = view.findViewById(R.id.edit_note_description_input_edit_text);
        noteDateInputText = view.findViewById(R.id.edit_note_date_input_edit_text);
        setDateText();

        noteDateInputText.setOnTouchListener((v, event) -> {
            activity.addFragment(new SetDateFragment());
            return true;
        });
        noteContentInputText = view.findViewById(R.id.edit_note_content_input_edit_text);
    }

    private void initButtons(View view) {
        Button addNoteButton = view.findViewById(R.id.edit_note_button);
        addNoteButton.setOnClickListener(this::addNoteButtonHandling);

        Button cancelNoteButton = view.findViewById(R.id.cancel_edit_note_button);
        cancelNoteButton.setOnClickListener(this::cancelNoteButtonHandling);
    }

    private void addNoteButtonHandling(View v) {
        String capture = Objects.requireNonNull(noteCaptureInputText.getText()).toString();
        String description = Objects.requireNonNull(noteDescriptionInputText.getText()).toString();
        String content = Objects.requireNonNull(noteContentInputText.getText()).toString();

        Note newNote = new Note(capture, description, date, content);
        newNote.setId(UUID.randomUUID().toString());

        Bundle result = new Bundle();
        result.putParcelable(ItemsFragment.KEY_ADD_NEW_NOTE, newNote);
        getParentFragmentManager().setFragmentResult(ItemsFragment.REQUEST_KEY_FOR_ADDING_NOTE, result);
        getParentFragmentManager().popBackStack();
    }

    private void cancelNoteButtonHandling(View v) {
        getParentFragmentManager().popBackStack();
    }
}