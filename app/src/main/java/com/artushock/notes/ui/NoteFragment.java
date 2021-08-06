package com.artushock.notes.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.artushock.notes.data.Note;
import com.artushock.notes.R;
import com.artushock.notes.data.NoteSource;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class NoteFragment extends Fragment {

    public static final String ARG_NOTE_SOURCE = "ARG_NOTE_SOURCE";
    private Note note;
    NoteSource noteSource;
    private final int currentPosition;

    private TextView captureNote;
    private TextView descriptionNote;
    private TextView dateNote;
    private TextView contentNote;

    public NoteFragment(int position) {
        this.currentPosition = position;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            noteSource = (NoteSource) getArguments().getSerializable(ARG_NOTE_SOURCE);
            note = noteSource.getNoteData(currentPosition);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_note, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        initNoteViewFields(view);
        if (note != null){
            fillFields();
        }
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
        //Note currentNote = NoteSourceImpl.getInstance().getNoteData(currentPosition);



        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(R.id.fragment_container, new EditCurrentItemFragment(note));
        fragmentTransaction.commit();

    }
}