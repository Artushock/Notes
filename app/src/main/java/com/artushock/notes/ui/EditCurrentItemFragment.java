package com.artushock.notes.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.artushock.notes.NoteActivity;
import com.artushock.notes.R;
import com.artushock.notes.data.Note;
import com.artushock.notes.data.NoteSource;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class EditCurrentItemFragment extends Fragment {
    NoteSource noteSource;
    private final Note editedNote;

    private String editedNoteCapture;
    private String editedNoteDescription;
    private String editedNoteContent;
    private String editedCreationDate;

    private TextInputEditText editNoteCaptureInputText;
    private TextInputEditText editNoteDescriptionInputText;
    private TextInputEditText editNoteDateInputText;
    private TextInputEditText editNoteContentInputText;

    private long date;

    private NoteActivity activity;

    public EditCurrentItemFragment(Note note) {
        this.editedNote = note;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (NoteActivity) getActivity();

        editedNoteCapture = editedNote.getNoteCapture();
        editedNoteDescription = editedNote.getNoteDescription();
        editedNoteContent = editedNote.getNoteContent();
        editedCreationDate = editedNote.getDateFormatted();

        getParentFragmentManager().setFragmentResultListener(SetDateFragment.REQUEST_KEY_FOR_EDITED_DATE, this, (requestKey, result) -> date = result.getLong(SetDateFragment.KEY_EDITED_DATE));

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
        editNoteDateInputText.setText(simpleDateFormat.format(date));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_current_note, container, false);

        initView(view);
        return view;
    }

    private void initView(View view) {

        initInputEditTextViews(view);
        initButtons(view);
    }

    private void initButtons(View view) {
        Button editNoteButton = view.findViewById(R.id.edit_note_button);
        Button cancelEditNoteButton = view.findViewById(R.id.cancel_edit_note_button);

        editNoteButton.setOnClickListener(v -> {

            readFields();
            Note note = new Note(
                    editedNoteCapture,
                    editedNoteDescription,
                    date,
                    editedNoteContent);

            Bundle result = new Bundle();
            result.putParcelable(ItemsFragment.KEY_EDITED_NOTE_SOURCE, note);
            getParentFragmentManager().setFragmentResult(ItemsFragment.REQUEST_KEY_FOR_EDITED_NOTE_SOURCE, result);
            getParentFragmentManager().popBackStack();
        });

        cancelEditNoteButton.setOnClickListener(v -> requireActivity().getSupportFragmentManager().popBackStack());
    }

    private void readFields() {
        editedNoteCapture = Objects.requireNonNull(editNoteCaptureInputText.getText()).toString();
        editedNoteDescription = Objects.requireNonNull(editNoteDescriptionInputText.getText()).toString();
        editedNoteContent = Objects.requireNonNull(editNoteContentInputText.getText()).toString();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initInputEditTextViews(View view) {
        editNoteCaptureInputText = view.findViewById(R.id.edit_note_capture_input_edit_text);
        editNoteDescriptionInputText = view.findViewById(R.id.edit_note_description_input_edit_text);
        editNoteDateInputText = view.findViewById(R.id.edit_note_date_input_edit_text);
        editNoteDateInputText.setOnTouchListener((v, event) -> {
            activity.addFragment(new SetDateFragment());
            return false;
        });

        editNoteContentInputText = view.findViewById(R.id.edit_note_content_input_edit_text);

        editNoteCaptureInputText.setText(editedNoteCapture);
        editNoteDescriptionInputText.setText(editedNoteDescription);
        editNoteDateInputText.setText(editedCreationDate);
        editNoteContentInputText.setText(editedNoteContent);
    }
}