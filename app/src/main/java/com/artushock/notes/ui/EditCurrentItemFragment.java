package com.artushock.notes.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import com.artushock.notes.R;
import com.artushock.notes.data.Note;
import com.google.android.material.textfield.TextInputEditText;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Date;

public class EditCurrentItemFragment extends Fragment {
    private Note editedNote;

    private String editedNoteCapture;
    private String editedNoteDescription;
    private String editedNoteContent;
    private String editedCreationDate;
    private long editedLongDate;

    private TextInputEditText editNoteCaptureInputText;
    private TextInputEditText editNoteDescriptionInputText;
    private TextInputEditText editNoteDateInputText;
    private TextInputEditText editNoteContentInputText;

    long date;

    public EditCurrentItemFragment(Note note) {
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
        editNoteDateInputText.setText(simpleDateFormat.format(date));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
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
            result.putParcelable("editCurrentNote", note);
            getParentFragmentManager().setFragmentResult("requestForEditedNote", result);
            getParentFragmentManager().popBackStack();
        });

        cancelEditNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().getSupportFragmentManager().popBackStack();
            }
        });
    }

    private void readFields() {
        editedNoteCapture = editNoteCaptureInputText.getText().toString();
        editedNoteDescription = editNoteDescriptionInputText.getText().toString();
        editedNoteContent = editNoteContentInputText.getText().toString();
    }

    private void initInputEditTextViews(View view) {
        editNoteCaptureInputText = view.findViewById(R.id.edit_note_capture_input_edit_text);
        editNoteDescriptionInputText = view.findViewById(R.id.edit_note_description_input_edit_text);
        editNoteDateInputText = view.findViewById(R.id.edit_note_date_input_edit_text);
        editNoteDateInputText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                noteDateInputTextHandling(v);
                return false;
            }
        });

        editNoteContentInputText = view.findViewById(R.id.edit_note_content_input_edit_text);

        editNoteCaptureInputText.setText(editedNoteCapture);
        editNoteDescriptionInputText.setText(editedNoteDescription);
        editNoteDateInputText.setText(editedCreationDate);
        editNoteContentInputText.setText(editedNoteContent);
    }

    private void noteDateInputTextHandling(View v) {
        getParentFragmentManager()
                .beginTransaction()
                .addToBackStack(null)
                .replace(R.id.fragment_container, new SetDateFragment())
                .commit();
    }
}