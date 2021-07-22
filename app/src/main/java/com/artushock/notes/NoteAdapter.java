package com.artushock.notes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

public class NoteAdapter extends ArrayAdapter<Note> {

    private LayoutInflater inflater;
    private int noteItemLayout;
    private List<Note> notes;


    public NoteAdapter(@NonNull Context context, int resource, @NonNull List<Note> notes) {
        super(context, resource, notes);
        this.notes = notes;
        this.noteItemLayout = resource;
        this.inflater = LayoutInflater.from(context);
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View view = inflater.inflate(this.noteItemLayout, parent, false);

        TextView noteItemCapture = view.findViewById(R.id.note_item_capture);
        TextView noteItemDescription = view.findViewById(R.id.note_item_description);
        TextView noteItemCreationDate = view.findViewById(R.id.note_item_date);

        Note note = notes.get(position);

        noteItemCapture.setText(note.getNoteCapture());
        noteItemDescription.setText(note.getNoteDescription());
        noteItemCreationDate.setText(note.getCreationDateFormatted());

        return view;
    }
}
