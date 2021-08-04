package com.artushock.notes.data;

public interface NoteSource {
    Note getNoteData(int position);
    void addNote(Note note);
    void deleteNoteByPosition(int position);
    void deleteNoteByObject(Note note);
    void updateNote(Note note, int position);
    void clearNoteList();
    void setNote(Note note, int position);
    int size();
}
