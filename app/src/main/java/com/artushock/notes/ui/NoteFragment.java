package com.artushock.notes.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.artushock.notes.NoteActivity;
import com.artushock.notes.R;
import com.artushock.notes.data.Note;
import com.artushock.notes.data.NoteSource;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.Serializable;

public class NoteFragment extends Fragment {

    public static final String TAG = "[Arts_NoteFragment]";

    private static final String NOTE_FRAGMENT_NOTE_SOURCE_KEY = "NOTE_FRAGMENT_NOTE_SOURCE_KEY";
    private static final String NOTE_FRAGMENT_POSITION_KEY = "NOTE_FRAGMENT_POSITION_KEY";

    private Note note;
    private NoteSource noteSource;
    private int position;
    NoteActivity activity;

    private TextView captureNote;
    private TextView descriptionNote;
    private TextView dateNote;
    private TextView contentNote;

    public NoteFragment() {
    }

    public static NoteFragment newInstance(NoteSource noteSource, int position) {
        NoteFragment noteFragment = new NoteFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(NOTE_FRAGMENT_NOTE_SOURCE_KEY, (Serializable) noteSource);
        bundle.putInt(NOTE_FRAGMENT_POSITION_KEY, position);
        noteFragment.setArguments(bundle);
        return noteFragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getParentFragmentManager().setFragmentResultListener(ItemsFragment.REQUEST_KEY_FOR_EDITED_NOTE_SOURCE, this, (requestKey, result) -> {
            noteSource = (NoteSource) result.getSerializable(ItemsFragment.KEY_EDITED_NOTE_SOURCE);
            note = noteSource.getNoteData(position);
            Log.d(TAG, "Got new note");
        });

        activity = (NoteActivity) requireActivity();

        if (getArguments() != null) {
            noteSource = (NoteSource) getArguments().getSerializable(NOTE_FRAGMENT_NOTE_SOURCE_KEY);
            position = getArguments().getInt(NOTE_FRAGMENT_POSITION_KEY);
            if (note == null) {
                note = noteSource.getNoteData(position);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_note, container, false);
        initView(view);
        Log.d(TAG, "onCreateView");
        return view;
    }

    private void initView(View view) {
        initNoteViewFields(view);
        if (note != null) {
            fillFields();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        fillFields();
    }

    private void fillFields() {
        captureNote.setText(note.getNoteCapture());
        descriptionNote.setText(note.getNoteDescription());
        dateNote.setText(note.getDateFormatted());
        contentNote.setText(note.getNoteContent());
    }

    private void initNoteViewFields(View view) {
        captureNote = view.findViewById(R.id.note_capture);
        descriptionNote = view.findViewById(R.id.note_description);
        dateNote = view.findViewById(R.id.note_date);
        contentNote = view.findViewById(R.id.note_content);

        FloatingActionButton editFab = view.findViewById(R.id.edit_fab);
        editFab.setOnClickListener(v -> editNoteFabHandling());
    }

    private void editNoteFabHandling() {
        activity.addFragment(EditNoteFragment.newInstance(noteSource, position));
    }
}