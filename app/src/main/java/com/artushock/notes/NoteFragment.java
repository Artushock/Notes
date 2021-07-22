package com.artushock.notes;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


public class NoteFragment extends Fragment {

    public static final String ARG_CITY = "ARG_CITY";
    private Note note;

    private TextView captureNote;
    private TextView descriptionNote;
    private TextView dateNote;
    private TextView contentNote;
    private Button backButton;

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
        if (getArguments() != null) {
            note = getArguments().getParcelable(ARG_CITY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_note, container, false);
        return initEditNoteFields(view);
    }

    private View initEditNoteFields(View view) {
        captureNote = view.findViewById(R.id.note_capture);
        descriptionNote = view.findViewById(R.id.note_description);
        dateNote = view.findViewById(R.id.note_date);
        contentNote = view.findViewById(R.id.note_content);
        backButton = view.findViewById(R.id.back_btn);
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
        //dateNote.setText(note.getCreationDateFormatted());
        contentNote.setText(note.getNoteContent());

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            backButton.setVisibility(Button.INVISIBLE);
        }

        backButton.setOnClickListener(v -> getActivity().finish());
    }
}