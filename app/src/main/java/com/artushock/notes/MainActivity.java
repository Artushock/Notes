package com.artushock.notes;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.artushock.notes.ui.AboutAppFragment;
import com.artushock.notes.ui.EditNewFragment;
import com.artushock.notes.ui.SettingsFragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static ArrayList<Note> noteItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NoteItemsFragment noteItemsFragment = new NoteItemsFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, noteItemsFragment).commit();

        initView();
    }

    private void initView() {
        initToolbar();

    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
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


    private void addFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);

        fragmentTransaction.commit();
    }


}