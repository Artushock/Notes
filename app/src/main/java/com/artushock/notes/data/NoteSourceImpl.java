package com.artushock.notes.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NoteSourceImpl implements NoteSource, Serializable {

    private static NoteSourceImpl instance;
    private List<Note> notes;

    private NoteSourceImpl() {
        this.notes = new ArrayList<>();
        startInit();
    }

    private void startInit() {
        notes.add(new Note("Стрижка", "Постричься", new Date().getTime(), "Постричься"));
        notes.add(new Note("Химчистка", "Заехать в химчистку", new Date().getTime(), "Завзти в химчистку шубу жены"));
        notes.add(new Note("Заправка", "Заправить машину", new Date().getTime(), "Заправить машину на все деньги"));
        notes.add(new Note("Собака", "Выгулять собаку", new Date().getTime(), "Выгулять собаку"));
        notes.add(new Note("Подарки", "Купить подарки", new Date().getTime(), "Купить подарки детям"));
    }

    public static NoteSourceImpl getInstance() {
        if (instance == null) {
            instance = new NoteSourceImpl();
        }
        return instance;
    }

    @Override
    public Note getNoteData(int position) {
        return notes.get(position);
    }

    @Override
    public void addNote(Note note) {
        notes.add(note);
    }

    @Override
    public void deleteNote(int position) {
        notes.remove(position);
    }

    @Override
    public void updateNote(Note note, int position) {
        notes.set(position, note);
    }

    @Override
    public void clearNoteList() {
        notes.clear();
    }

    @Override
    public int size() {
        return notes.size();
    }

    public void setNote(Note note, int position){
        notes.set(position, note);
    }


}

