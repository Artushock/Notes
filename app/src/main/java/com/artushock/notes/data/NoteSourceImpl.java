package com.artushock.notes.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NoteSourceImpl implements NoteSource{
    private List<Note> notes;

    public NoteSourceImpl() {
        this.notes = new ArrayList<>();
    }

    public NoteSourceImpl startInit() {
        notes.add(new Note("Стрижка", "Постричься", new Date().getTime(), "Постричься"));
        notes.add(new Note("Химчистка", "Заехать в химчистку", new Date().getTime(), "Завзти в химчистку шубу жены"));
        notes.add(new Note("Заправка", "Заправить машину", new Date().getTime(), "Заправить машину на все деньги"));
        notes.add(new Note("Собака", "Выгулять собаку", new Date().getTime(), "Выгулять собаку"));
        notes.add(new Note("Подарки", "Купить подарки", new Date().getTime(), "Купить подарки детям"));
        return this;
    }

    @Override
    public Note getNoteData(int position) {
        return notes.get(position);
    }

    @Override
    public int size() {
        return notes.size();
    }
}

