package com.artushock.notes.data;

public interface NoteSource {
    NoteSource init(NoteSourceResponse response);
    Note getNoteData(int position);
    void addNote(Note note);
    void deleteNoteByPosition(int position);
    void deleteNoteByObject(Note note);
    void clearNoteList();
    void setNote(Note note, int position);
    int size();
}
