package com.artushock.notes.ui;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.artushock.notes.NoteActivity;
import com.artushock.notes.R;
import com.artushock.notes.data.Note;
import com.artushock.notes.data.NoteSource;
import com.artushock.notes.data.NoteSourceImpl;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;

public class ItemsFragment extends Fragment {
    public static final String REQUEST_KEY_FOR_ADDING_NOTE = "requestForAddingNote";
    public static final String KEY_ADD_NEW_NOTE = "addNewNote";
    public static final String REQUEST_KEY_FOR_EDITED_NOTE = "requestForEditedNote";
    public static final String KEY_EDIT_CURRENT_NOTE = "editCurrentNote";
    public static final int NO_POSITION_VALUE = -1;


    private NoteSource noteSource;
    private RecyclerView recyclerView;
    private NoteAdapter noteAdapter;
    int currentPosition = NO_POSITION_VALUE;
    private NoteActivity activity;

    public ItemsFragment() {
    }

    public static ItemsFragment newInstance() {
        return new ItemsFragment();
    }

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (NoteActivity)getActivity();

        getParentFragmentManager().setFragmentResultListener(REQUEST_KEY_FOR_ADDING_NOTE, this, (requestKey, result) -> {
            Note newNote = result.getParcelable(KEY_ADD_NEW_NOTE);
            noteSource.addNote(newNote);
        });

        getParentFragmentManager().setFragmentResultListener(REQUEST_KEY_FOR_EDITED_NOTE, this, (requestKey, result) -> {
            Note editedNote = result.getParcelable(KEY_EDIT_CURRENT_NOTE);
            noteSource.setNote(editedNote, currentPosition);
            noteAdapter.notifyItemChanged(currentPosition);
            recyclerView.scrollToPosition(currentPosition);
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_note_items, container, false);
        recyclerView = view.findViewById(R.id.recycler_view_list);
        noteSource = NoteSourceImpl.getInstance();
        initRecyclerView(recyclerView, noteSource);
        return view;
    }

    private void initRecyclerView(RecyclerView recyclerView, NoteSource noteSource) {
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        noteAdapter = new NoteAdapter(noteSource, this);
        recyclerView.setAdapter(noteAdapter);

        noteAdapter.setItemClickListener((view, position) -> {
            Bundle bundle = new Bundle();
            bundle.putParcelable(NoteFragment.ARG_NOTE, noteSource.getNoteData(position));
            Fragment noteFragment = new NoteFragment(position);
            noteFragment.setArguments(bundle);
            activity.addFragment(noteFragment);
        });

        noteAdapter.setEditClickListener((view, position) -> {
            currentPosition = position;
            activity.addFragment(new EditCurrentItemFragment(noteSource.getNoteData(position)));
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
        addFab.setOnClickListener(v -> activity.addFragment(new AddNoteFragment()));
    }

    @Override
    public void onCreateContextMenu(@NonNull @NotNull ContextMenu menu, @NonNull @NotNull View v, @Nullable @org.jetbrains.annotations.Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = requireActivity().getMenuInflater();
        inflater.inflate(R.menu.note_context_menu, menu);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onContextItemSelected(@NonNull @NotNull MenuItem item) {
        int id = item.getItemId();
        currentPosition = noteAdapter.getMenuCurrentPosition();

        switch (id){
            case R.id.edit_context_menu:
                activity.addFragment(new EditCurrentItemFragment(NoteSourceImpl.getInstance().getNoteData(currentPosition)));
                return true;
            case R.id.delete_context_menu:
                noteSource.deleteNote(currentPosition);
                noteAdapter.notifyDataSetChanged();
                return true;
        }

        return super.onContextItemSelected(item);
    }
}