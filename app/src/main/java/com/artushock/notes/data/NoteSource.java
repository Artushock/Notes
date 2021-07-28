package com.artushock.notes.data;

public interface NoteSource {
    Note getNoteData(int position);
    int size();
}
