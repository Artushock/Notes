package com.artushock.notes.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.artushock.notes.R;
import com.artushock.notes.data.Note;
import com.google.android.material.textfield.TextInputEditText;

public class EditCurrentNoteFragment extends Fragment {
    private Note editedNote;

    private String editedNoteCapture;
    private String editedNoteDescription;
    private String editedNoteContent;
    private String editedCreationDate;
    private long editedLongDate;

    public EditCurrentNoteFragment(Note note) {
        this.editedNote = note;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        editedNoteCapture = editedNote.getNoteCapture();
        editedNoteDescription = editedNote.getNoteDescription();
        editedNoteContent = editedNote.getNoteContent();
        editedCreationDate = editedNote.getCreationDateFormatted();
        editedLongDate = editedNote.getCreationDate();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_current_note, container, false);

        TextInputEditText editNoteCaptureInputText = view.findViewById(R.id.edit_note_capture_input_edit_text);
        TextInputEditText editNoteDescriptionInputText = view.findViewById(R.id.edit_note_description_input_edit_text);
        TextInputEditText editNoteDateInputText = view.findViewById(R.id.edit_note_date_input_edit_text);
        TextInputEditText editNoteContentInputText = view.findViewById(R.id.edit_note_content_input_edit_text);
        Button editNoteButton = view.findViewById(R.id.edit_note_button) ;
        Button cancelEditNoteButton = view.findViewById(R.id.cancel_edit_note_button);

        editNoteCaptureInputText.setText(editedNoteCapture);
        editNoteDescriptionInputText.setText(editedNoteDescription);
        editNoteDateInputText.setText(editedCreationDate);
        editNoteContentInputText.setText(editedNoteContent);

        return view;
    }
}