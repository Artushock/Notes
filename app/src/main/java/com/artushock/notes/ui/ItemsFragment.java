package com.artushock.notes.ui;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.artushock.notes.MainActivity;
import com.artushock.notes.R;
import com.artushock.notes.data.Note;
import com.artushock.notes.data.NoteSource;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;

public class ItemsFragment extends Fragment {
    private NoteSource noteSource;

    public ItemsFragment() {
    }

    public static ItemsFragment newInstance() {
        return new ItemsFragment();
    }

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getParentFragmentManager().setFragmentResultListener("requestForAddingNote", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull @NotNull String requestKey, @NonNull @NotNull Bundle result) {
                Note newNote = result.getParcelable("addNewNote");
                noteSource.addNote(newNote);
            }
        });

        getParentFragmentManager().setFragmentResultListener("requestForEditingCurrentNote", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull @NotNull String requestKey, @NonNull @NotNull Bundle result) {

            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_note_items, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_list);
        noteSource = (NoteSource) getArguments().getSerializable(MainActivity.NOTE_SOURCE_KEY);
        initRecyclerView(recyclerView, noteSource);
        return view;
    }

    private void initRecyclerView(RecyclerView recyclerView, NoteSource noteSource) {
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        NoteAdapter noteAdapter = new NoteAdapter(noteSource);
        recyclerView.setAdapter(noteAdapter);

        noteAdapter.setItemClickListener((view, position) -> addFragment(new AddNoteFragment()));

        noteAdapter.setEditClickListener(new NoteAdapter.OnEditClickListener() {
            @Override
            public void onEditClick(View view, int position) {
                getParentFragmentManager().setFragmentResultListener("editRequest", getViewLifecycleOwner(), new FragmentResultListener() {
                    @Override
                    public void onFragmentResult(@NonNull @NotNull String requestKey, @NonNull @NotNull Bundle result) {
                        Note note = result.getParcelable("bundleKey");
                        noteSource.setNote(note, position);
                    }
                });
                addFragment(new EditCurrentItemFragment(noteSource.getNoteData(position)));
            }
        });

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
        addFab.setOnClickListener(v -> addFragment(new AddNoteFragment()));
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