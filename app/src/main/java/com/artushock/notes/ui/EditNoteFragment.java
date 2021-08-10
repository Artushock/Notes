package com.artushock.notes.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.artushock.notes.NoteActivity;
import com.artushock.notes.R;
import com.artushock.notes.data.Note;
import com.artushock.notes.data.NoteSource;
import com.google.android.material.textfield.TextInputEditText;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Objects;

public class EditNoteFragment extends Fragment {

    public static final String TAG = "[Arts_EditNoteFragment]";

    private static final String EDITED_NOTE_SOURCE_KEY = "EDITED_NOTE_KEY";
    private static final String EDITED_NOTE_POSITION_KEY = "EDITED_NOTE_POSITION_KEY";

    private NoteSource noteSource;
    private Note note;
    private int position;
    private String noteId;
    private long date;

    private String editedNoteCapture;
    private String editedNoteDescription;
    private String editedNoteContent;
    private String editedCreationDate;

    private TextInputEditText editNoteCaptureInputText;
    private TextInputEditText editNoteDescriptionInputText;
    private TextView dateTextView;
    private TextInputEditText editNoteContentInputText;


    public EditNoteFragment() {
        // Required empty public constructor
    }

    public static EditNoteFragment newInstance(NoteSource noteSource, int position) {
        EditNoteFragment fragment = new EditNoteFragment();
        Bundle args = new Bundle();
        args.putSerializable(EDITED_NOTE_SOURCE_KEY, (Serializable) noteSource);
        args.putInt(EDITED_NOTE_POSITION_KEY, position);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getParentFragmentManager().setFragmentResultListener(SetDateFragment.REQUEST_KEY_FOR_EDITED_DATE, this, (requestKey, result) -> {
            date = result.getLong(SetDateFragment.KEY_EDITED_DATE);
            Log.d(TAG, "date = " + date);
        });

        if (getArguments() != null) {
            noteSource = (NoteSource) getArguments().getSerializable(EDITED_NOTE_SOURCE_KEY);
            position = getArguments().getInt(EDITED_NOTE_POSITION_KEY);
        }

        note = noteSource.getNoteData(position);
        noteId = note.getId();
        date = note.getDate();


        initNoteFields();
        Log.d(TAG, "onCreate");
    }

    @Override
    public void onResume() {
        super.onResume();
        setDateText();
        Log.d(TAG, "onResume");
    }

    private void setDateText() {
        String pattern = "dd.MM.yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, Locale.getDefault());
        dateTextView.setText(simpleDateFormat.format(date));
    }

    private void initNoteFields() {
        editedNoteCapture = note.getNoteCapture();
        editedNoteDescription = note.getNoteDescription();
        editedNoteContent = note.getNoteContent();
        editedCreationDate = note.getDateFormatted();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_note, container, false);
        initView(view);
        return view;
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initView(View view) {
        editNoteCaptureInputText = view.findViewById(R.id.edit_note_capture_input_edit_text);
        editNoteDescriptionInputText = view.findViewById(R.id.edit_note_description_input_edit_text);
        dateTextView = view.findViewById(R.id.edit_date_text_view);

        dateTextView.setOnClickListener(v -> {
            NoteActivity noteActivity = (NoteActivity) requireActivity();
            Log.d(TAG, "date = " + date);
            noteActivity.addFragment(SetDateFragment.newInstance(date));
        });

        editNoteContentInputText = view.findViewById(R.id.edit_note_content_input_edit_text);

        editNoteCaptureInputText.setText(editedNoteCapture);
        editNoteDescriptionInputText.setText(editedNoteDescription);
        dateTextView.setText(editedCreationDate);
        editNoteContentInputText.setText(editedNoteContent);


        Button editNoteButton = view.findViewById(R.id.edit_note_button);
        Button cancelEditNoteButton = view.findViewById(R.id.cancel_edit_note_button);

        editNoteButton.setOnClickListener(v -> {

            readFields();
            Note note = new Note(
                    editedNoteCapture,
                    editedNoteDescription,
                    date,
                    editedNoteContent);
            note.setId(noteId);
            noteSource.setNote(note, position);

            Log.d(TAG, "date = " + date);

            Bundle result = new Bundle();
            result.putSerializable(ItemsFragment.KEY_EDITED_NOTE_SOURCE, (Serializable) noteSource);
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
}