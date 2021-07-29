package com.artushock.notes.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.artushock.notes.R;
import com.artushock.notes.data.Note;
import com.artushock.notes.data.NoteSource;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {

    private final NoteSource dataSource;
    private OnItemClickListener itemClickListener;
    private OnEditClickListener editClickListener;

    private OnCheckedChangeListener checkedChangeListener;

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

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view , int position);
    }

    public void setEditClickListener(OnEditClickListener editClickListener) {
        this.editClickListener = editClickListener;
    }

    public interface OnEditClickListener {
        void onEditClick(View view , int position);
    }

    public void setCheckedChangeListener(OnCheckedChangeListener checkedChangeListener) {
        this.checkedChangeListener = checkedChangeListener;
    }

    public interface OnCheckedChangeListener{
        void OnCheckedChange(View view , int position, boolean isChecked);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView captureItemNote;
        TextView descriptionItemNote;
        TextView dateItemNote;
        AppCompatImageView itemEditImage;
        AppCompatCheckBox itemCheckBox;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            captureItemNote = itemView.findViewById(R.id.item_capture);
            descriptionItemNote = itemView.findViewById(R.id.item_description);
            dateItemNote = itemView.findViewById(R.id.item_date);
            itemEditImage = itemView.findViewById(R.id.item_edit_image);
            itemCheckBox = itemView.findViewById(R.id.item_checkbox);

            itemView.setOnClickListener(v -> {
                if(itemClickListener != null){
                    itemClickListener.onItemClick(v, getAdapterPosition());
                }
            });

            itemEditImage.setOnClickListener(v -> {
                if(editClickListener != null){
                    editClickListener.onEditClick(v, getAdapterPosition());
                }
            });

            itemCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (checkedChangeListener != null){
                    checkedChangeListener.OnCheckedChange(buttonView, getAdapterPosition(), isChecked);
                }
            });
        }

        public void setData(Note note) {
            captureItemNote.setText(note.getNoteCapture());
            descriptionItemNote.setText(note.getNoteDescription());
            dateItemNote.setText(note.getCreationDateFormatted());
        }


    }
}
