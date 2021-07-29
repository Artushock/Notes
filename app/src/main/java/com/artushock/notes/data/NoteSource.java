package com.artushock.notes.data;

public interface NoteSource {
    Note getNoteData(int position);
    void addNote(Note note);
    void setNote(Note note, int position);
    int size();
}
