package com.artushock.notes;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.artushock.notes.data.NoteSource;
import com.artushock.notes.data.NoteSourceImpl;
import com.artushock.notes.ui.AboutAppFragment;
import com.artushock.notes.ui.AddNoteFragment;
import com.artushock.notes.ui.ItemsFragment;
import com.artushock.notes.ui.SettingsFragment;
import com.google.android.material.navigation.NavigationView;

import java.io.Serializable;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private NoteSource noteSource;
    public static final String NOTE_SOURCE_KEY = "NOTE_SOURCE_KEY";

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
        initStartFragment(noteSource);
    }

    private void initNoteSourceData() {
        noteSource = NoteSourceImpl.getInstance();
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void initNavigationMenu() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void initStartFragment(NoteSource noteSource) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(NOTE_SOURCE_KEY, (Serializable) noteSource);
        ItemsFragment itemsFragment = ItemsFragment.newInstance();
        itemsFragment.setArguments(bundle);
        addFragment(itemsFragment);
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
            case R.id.app_settings_menu:
                addFragment(new SettingsFragment());
                return true;
            case R.id.about_app_menu:
                addFragment(new AboutAppFragment());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        switch (itemId) {
            case R.id.app_settings_menu:
                addFragment(new SettingsFragment());
                break;
            case R.id.about_app_menu:
                addFragment(new AboutAppFragment());
                break;
            case R.id.add_note_menu:
                addFragment(new AddNoteFragment());
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void addFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);

        fragmentTransaction.commit();
    }
}