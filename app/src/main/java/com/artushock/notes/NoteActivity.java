package com.artushock.notes;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.PersistableBundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.artushock.notes.data.NoteSource;
import com.artushock.notes.data.NoteSourceFirebaseImpl;
import com.artushock.notes.ui.AboutAppFragment;
import com.artushock.notes.ui.AddNoteFragment;
import com.artushock.notes.ui.ItemsFragment;
import com.artushock.notes.ui.SettingsFragment;
import com.google.android.material.navigation.NavigationView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class NoteActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    public static final String NOTE_SOURCE_KEY = "NOTE_SOURCE_KEY";

    private NoteSource noteSource;
    private List<Integer> selectedPositions;

    boolean doubleBackToExitPressedOnce = false;

    public void addSelectedPosition(Integer position) {
        selectedPositions.add(position);
    }

    public void removeSelectedPosition(Integer position) {
        selectedPositions.remove(position);
    }

    private void removeSelectedNotes() {
        for (Integer position : selectedPositions) {
            int i = position;
            noteSource.deleteNoteByObject(noteSource.getNoteData(i));
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putSerializable(NOTE_SOURCE_KEY, (Serializable) noteSource);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        noteSource = (NoteSource) savedInstanceState.getSerializable(NOTE_SOURCE_KEY);
    }

    private void initView() {
        initNoteSourceData();
        initToolbar();
        initNavigationMenu();
        initStartFragment();
    }

    private void initNoteSourceData() {
        noteSource = new NoteSourceFirebaseImpl();
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void initNavigationMenu() {
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    public void initStartFragment() {
        ItemsFragment itemsFragment = ItemsFragment.newInstance(noteSource);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, itemsFragment)
                .commit();

        selectedPositions = new ArrayList<>();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_note_menu:
                addFragment(new AddNoteFragment());
                return true;
            case R.id.clear_all_note_menu:
                alertRemoveAllNotes();
                return true;
            case R.id.clear_selected_note_menu:
                alertRemoveSelectedNotes();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void alertRemoveSelectedNotes() {


        new AlertDialog.Builder(this)
                .setTitle(R.string.Attention)
                .setMessage(R.string.are_u_sure_remove_selected_notes)
                .setIcon(R.drawable.attention)
                .setCancelable(false)
                .setPositiveButton(R.string.Yes, (dialog, which) -> {
                    removeSelectedNotes();
                    initStartFragment();
                })
                .setNegativeButton(R.string.No, (dialog, which) -> {
                    // do nothing
                })
                .show();
    }

    private void alertRemoveAllNotes() {

        new AlertDialog.Builder(this)
                .setTitle(R.string.Attention)
                .setMessage(R.string.are_u_sure_remove_all_notes)
                .setIcon(R.drawable.attention)
                .setCancelable(false)
                .setPositiveButton(R.string.Yes, (dialog, which) -> {
                    noteSource.clearNoteList();
                    initStartFragment();
                })
                .setNegativeButton(R.string.No, (dialog, which) -> {
                    // do nothing
                })
                .show();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        switch (itemId) {
            case R.id.app_settings_nav_menu:
                addFragment(new SettingsFragment());
                break;
            case R.id.about_app_nav_menu:
                addFragment(new AboutAppFragment());
                break;
            case R.id.exit_nav_menu:
                finish();
                System.exit(0);
                break;
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce || getSupportFragmentManager().getBackStackEntryCount() != 0) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, getResources().getText(R.string.press_again), Toast.LENGTH_SHORT).show();

        new Handler(Looper.getMainLooper()).postDelayed(() -> doubleBackToExitPressedOnce = false, 2000);
    }


    public void addFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);

        fragmentTransaction.commit();
    }
}