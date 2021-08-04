package com.artushock.notes.ui;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.artushock.notes.NoteActivity;
import com.artushock.notes.R;
import com.artushock.notes.data.Note;
import com.artushock.notes.data.NoteSource;
import com.artushock.notes.data.NoteSourceResponse;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;

public class ItemsFragment extends Fragment {
    public static final String TAG = "[ItemsFragment] : ";

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
        activity = (NoteActivity) getActivity();

        getParentFragmentManager().setFragmentResultListener(REQUEST_KEY_FOR_ADDING_NOTE, this, (requestKey, result) -> {
            Note note = result.getParcelable(KEY_ADD_NEW_NOTE);
            noteSource.addNote(note);
        });

        getParentFragmentManager().setFragmentResultListener(REQUEST_KEY_FOR_EDITED_NOTE, this, (requestKey, result) -> {
            Note editedNote = result.getParcelable(KEY_EDIT_CURRENT_NOTE);
            noteSource.setNote(editedNote, currentPosition);
            noteAdapter.notifyItemChanged(currentPosition);
            recyclerView.scrollToPosition(currentPosition);
        });

        this.noteSource = (NoteSource) getArguments().getSerializable(NoteActivity.NOTE_SOURCE_KEY);
        initAdapter(noteSource);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_note_items, container, false);


        noteSource = noteSource.init(new NoteSourceResponse() {
            @Override
            public void initialized(NoteSource noteSource) {
                noteAdapter.notifyDataSetChanged();
                Log.d(TAG, "notifyDataSetChanged()");
            }
        });

        noteAdapter.setDataSource(noteSource);

        initView(view);
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }



    private void initView(View view) {
        initRecyclerView(view);
        initAddButton(view);
    }

    private void initRecyclerView(View view) {
        recyclerView = view.findViewById(R.id.recycler_view_list);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(noteAdapter);

        Log.d(TAG, "recyclerView initialized ");
    }

    private void initAddButton(View view) {
        FloatingActionButton addFab = view.findViewById(R.id.add_fab);
        addFab.setOnClickListener(v -> activity.addFragment(new AddNoteFragment()));
    }


    private void initAdapter(NoteSource noteSource) {
        noteAdapter = new NoteAdapter(this);

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

        noteAdapter.setCheckedChangeListener((view, position, isChecked) -> {
            if (isChecked) {
                activity.addSelectedPosition(position);
            } else {
                activity.removeSelectedPosition(position);
            }
        });

        Log.d(TAG, "noteAdapter initialized ");
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

        switch (id) {
            case R.id.edit_context_menu:
                activity.addFragment(new EditCurrentItemFragment(noteSource.getNoteData(currentPosition)));
                return true;
            case R.id.delete_context_menu:
                noteSource.deleteNoteByPosition(currentPosition);
                noteAdapter.notifyDataSetChanged();
                return true;
        }

        return super.onContextItemSelected(item);
    }
}