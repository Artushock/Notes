package com.artushock.notes;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.artushock.notes.fragments.AboutAppFragment;
import com.artushock.notes.fragments.EditNewFragment;
import com.artushock.notes.fragments.NoteFragment;
import com.artushock.notes.fragments.SettingsFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Date;

public class NoteItemsFragment extends Fragment {
    private ListView noteItemList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_note_items, container, false);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        if (!menu.hasVisibleItems()){
            inflater.inflate(R.menu.main_menu, menu);
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.add_note_menu:
                addFragment(new EditNewFragment());
                return true;
            case R.id.app_settings_menu:
                addFragment(new SettingsFragment());
                return true;
            case R.id.about_app_menu:
                addFragment(new AboutAppFragment());
                return true;
            default: return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        noteItemList = view.findViewById(R.id.list_view_fragment);

        if (MainActivity.noteItems.size() == 0) setTestNoteData();

        initView(view);
    }

    private void initView(View view) {
        initAdapter(view);
        initAddButton(view);
    }

    private void initAddButton(View view) {
        FloatingActionButton addFab = view.findViewById(R.id.add_fab);
        addFab.setOnClickListener(v -> addFragment(new EditNewFragment()));
    }

    private void addFragment(Fragment fragment) {
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);

        fragmentTransaction.commit();
    }

    private void initAdapter(View view) {
        NoteAdapter noteAdapter = new NoteAdapter(view.getContext(), R.layout.note_item_layout, MainActivity.noteItems);
        noteItemList.setAdapter(noteAdapter);
        noteItemList.setOnItemClickListener((parent, view1, position, id) -> showNoteFragment(MainActivity.noteItems.get(position)));
    }

    private void showNoteFragment(Note note) {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            showNoteLandFragment(note);
        } else {
            showNoteLandFragment(note);
        }
    }

    private void showNoteLandFragment(Note note) {
        NoteFragment noteFragment = NoteFragment.newInstance(note);
        addFragment(noteFragment);
    }


    private void setTestNoteData() {
        ArrayList<Note> testList = new ArrayList<>();
        testList.add(new Note("Стрижка", "Постричься", new Date().getTime(), "Постричься"));
        testList.add(new Note("Химчистка", "Заехать в химчистку", new Date().getTime(), "Завзти в химчистку шубу жены"));
        testList.add(new Note("Заправка", "Заправить машину", new Date().getTime(), "Заправить машину на все деньги"));
        testList.add(new Note("Собака", "Выгулять собаку", new Date().getTime(), "Выгулять собаку"));
        testList.add(new Note("Подарки", "Купить подарки", new Date().getTime(), "Купить подарки детям"));
        MainActivity.noteItems = testList;
    }
}