package com.artushock.notes;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static ArrayList<Note> noteItems = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NoteItemsFragment noteItemsFragment = new NoteItemsFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, noteItemsFragment).commit();

    }
}