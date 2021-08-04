package com.artushock.notes.data;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class NoteSourceFirebaseImpl implements NoteSource, Serializable {

    public static final String COLLECTION = "NOTES";
    public static final String TAG = "NoteSourceFirebaseImpl";

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference collection = db.collection(COLLECTION);
    List<Note> notes = new ArrayList<>();


    @Override
    public NoteSource init(NoteSourceResponse response) {

        collection.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    notes = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()){
                        Note note = (Note) document.toObject(Note.class);
                        note.setId(document.getId());

                        notes.add(note);
                    }
                    Log.d(TAG, "Succesful " + notes.size() + " got elements");
                } else {
                    Log.e(TAG,"Failed " + task.getException());
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Log.e(TAG,"Failed " + e);
            }
        });

        if (response != null) {
            response.initialized(this);
        }

        return this;
    }

    @Override
    public Note getNoteData(int position) {
        return notes.get(position);
    }

    @Override
    public void addNote(Note note) {
        notes.add(note);
        collection.document(note.getId()).set(note);
    }

    @Override
    public void deleteNoteByPosition(int position) {
        collection.document(notes.get(position).getId()).delete();
        notes.remove(position);
    }

    @Override
    public void deleteNoteByObject(Note note) {
        collection.document(note.getId()).delete();
        notes.remove(note);
    }

    @Override
    public void clearNoteList() {
        for (Note note : notes){
            collection.document(note.getId()).delete();
        }
        notes.clear();
    }

    @Override
    public void setNote(Note note, int position) {
        collection.document(note.getId()).set(note);
        notes.set(position, note);
    }

    @Override
    public int size() {
        return notes.size();
    }
}
