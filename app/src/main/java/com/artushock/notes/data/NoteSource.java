package com.artushock.notes.data;

public interface NoteSource {
    Note getNoteData(int position);
    void addNote(Note note);
    void deleteNote(int position);
    void updateNote(Note note, int position);
    void clearNoteList();
    void setNote(Note note, int position);
    int size();
}
