package com.artushock.notes;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.Date;

public class NoteItemsFragment extends Fragment {
    private final ArrayList<Note> noteItems = new ArrayList<>();
    private ListView noteItemList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_note_items, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        noteItemList = view.findViewById(R.id.list_view_fragment);
        setTestNoteData();

        NoteAdapter noteAdapter = new NoteAdapter(view.getContext(), R.layout.note_item_layout, noteItems);
        noteItemList.setAdapter(noteAdapter);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        noteItemList.setOnItemClickListener((parent, view, position, id) -> showNoteFragment(noteItems.get(position)));
    }

    private void showNoteFragment(Note note) {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            showNoteLandFragment(note);
        } else {
            showNotePortraitFragment(note);
        }
    }

    private void showNoteLandFragment(Note note) {
        NoteFragment noteFragment = NoteFragment.newInstance(note);

        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.note_fragment_container, noteFragment);

        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commit();
    }

    private void showNotePortraitFragment(Note note) {
        Intent intent = new Intent();
        intent.setClass(getContext(), NoteActivity.class);
        intent.putExtra(NoteFragment.ARG_CITY, note);
        startActivity(intent);
    }


    private void setTestNoteData() {
        noteItems.add(new Note("Стрижка", "Постричься", new Date(), "Постричься"));
        noteItems.add(new Note("Химчистка", "Заехать в химчистку", new Date(), "Завзти в химчистку шубу жены"));
        noteItems.add(new Note("Заправка", "Заправить машину", new Date(), "Заправить машину на все деньги"));
        noteItems.add(new Note("Собака", "Выгулять собаку", new Date(), "Выгулять собаку"));
        noteItems.add(new Note("Подарки", "Купить подарки", new Date(), "Купить подарки детям"));
    }
}