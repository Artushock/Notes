package com.artushock.notes.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.artushock.notes.Note;
import com.artushock.notes.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class NoteFragment extends Fragment {

    public static final String ARG_CITY = "ARG_CITY";
    private Note note;

    private TextView captureNote;
    private TextView descriptionNote;
    private TextView dateNote;
    private TextView contentNote;
    private FloatingActionButton editFab;

    public NoteFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static NoteFragment newInstance(Note note) {
        NoteFragment noteFragment = new NoteFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_CITY, note);
        noteFragment.setArguments(args);
        return noteFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

        if (getArguments() != null) {
            note = getArguments().getParcelable(ARG_CITY);
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.note_fragment_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(ARG_CITY, note);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_note, container, false);
        return initNoteViewFields(view);
    }

    private View initNoteViewFields(View view) {
        captureNote = view.findViewById(R.id.note_capture);
        descriptionNote = view.findViewById(R.id.note_description);
        dateNote = view.findViewById(R.id.note_date);
        contentNote = view.findViewById(R.id.note_content);
        editFab = view.findViewById(R.id.edit_fab);
        return view;
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

        if (savedInstanceState != null){
            note = savedInstanceState.getParcelable(ARG_CITY);
        }

        captureNote.setText(note.getNoteCapture());
        descriptionNote.setText(note.getNoteDescription());
        // TODO Solve problem with the field in portrait landscape
        dateNote.setText(note.getCreationDateFormatted());
        contentNote.setText(note.getNoteContent());

        editFab.setOnClickListener(v -> {
            editNoteFabHandling(v);
        });
    }

    private void editNoteFabHandling(View v) {
        //todo
        Toast.makeText(getContext(), "From editNoteFabHandling()", Toast.LENGTH_SHORT).show();
    }
}