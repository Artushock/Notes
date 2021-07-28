package com.artushock.notes.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.artushock.notes.R;
import com.artushock.notes.data.Note;
import com.artushock.notes.data.NoteSource;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {

    private final NoteSource dataSource;

    public NoteAdapter(NoteSource dataSource) {
        this.dataSource = dataSource;
    }

    @NonNull
    @Override
    public NoteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteAdapter.ViewHolder holder, int position) {
        holder.setData(dataSource.getNoteData(position));

    }

    @Override
    public int getItemCount() {
        return dataSource.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView captureItemNote;
        TextView descriptionItemNote;
        TextView dateItemNote;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            captureItemNote = itemView.findViewById(R.id.note_item_capture);
            descriptionItemNote = itemView.findViewById(R.id.note_item_description);
            dateItemNote = itemView.findViewById(R.id.note_item_date);
        }

        public void setData(Note note) {
            captureItemNote.setText(note.getNoteCapture());
            descriptionItemNote.setText(note.getNoteDescription());
            dateItemNote.setText(note.getCreationDateFormatted());
        }
    }
}
