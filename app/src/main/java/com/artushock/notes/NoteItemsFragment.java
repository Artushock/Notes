package com.artushock.notes;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.artushock.notes.data.NoteSource;
import com.artushock.notes.data.NoteSourceImpl;
import com.artushock.notes.ui.EditNewFragment;
import com.artushock.notes.ui.NoteAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class NoteItemsFragment extends Fragment {

    public static NoteItemsFragment newInstance() {
        return new NoteItemsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_note_items, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_list);
        NoteSource noteSource = new NoteSourceImpl().startInit();
        initRecyclerView(recyclerView, noteSource);
        return view;
    }

    private void initRecyclerView(RecyclerView recyclerView, NoteSource noteSource) {
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        NoteAdapter noteAdapter = new NoteAdapter(noteSource);
        recyclerView.setAdapter(noteAdapter);

        noteAdapter.setItemClickListener((view, position) -> addFragment(new EditNewFragment()));

        noteAdapter.setEditClickListener((view, position) -> Toast.makeText(getContext(), "Редактировать заметку", Toast.LENGTH_SHORT).show());

        noteAdapter.setCheckedChangeListener((view, position, isChecked) -> Toast.makeText(getContext(), "checkBox is " + isChecked, Toast.LENGTH_SHORT).show());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    private void initView(View view) {
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
}