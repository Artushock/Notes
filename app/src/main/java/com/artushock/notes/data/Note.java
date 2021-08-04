package com.artushock.notes.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class Note implements Parcelable {
    private final String noteCapture;
    private final String noteDescription;
    private final String noteContent;
    private final long creationDate;

    public Note(Parcel in) {
        noteCapture = in.readString();
        noteDescription = in.readString();
        noteContent = in.readString();
        creationDate = in.readLong();
    }

    public String getNoteCapture() {
        return noteCapture;
    }

    public String getNoteDescription() {
        return noteDescription;
    }

    public String getNoteContent() {
        return noteContent;
    }

    public String getCreationDateFormatted() {
        String pattern = "dd.MM.yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, Locale.getDefault());
        return simpleDateFormat.format(creationDate);
    }

    public Note(String noteCapture, String noteDescription, long date, String noteContent) {
        this.noteCapture = noteCapture;
        this.noteDescription = noteDescription;
        this.noteContent = noteContent;
        this.creationDate = date;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(noteCapture);
        dest.writeString(noteDescription);
        dest.writeString(noteContent);
        dest.writeLong(creationDate);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };
}
