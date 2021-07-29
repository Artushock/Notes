package com.artushock.notes.data;

public interface NoteSource {
    Note getNoteData(int position);
    void addNote(Note note);
    int size();
}
