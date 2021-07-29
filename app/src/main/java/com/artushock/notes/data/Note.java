package com.artushock.notes.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Note implements Parcelable {
    private String noteCapture;
    private String noteDescription;
    private String noteContent;
    private long creationDate;

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

    public long getCreationDate() {
        return creationDate;
    }

    public String getCreationDateFormatted() {
        String pattern = "dd.MM.yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(creationDate);
    }

    public Note(String noteCapture, String noteDescription, long date, String noteContent) {
        this.noteCapture = noteCapture;
        this.noteDescription = noteDescription;
        this.noteContent = noteContent;
        this.creationDate = date;
    }

    public Note(String noteCapture, String noteDescription, String noteContent) {
        new Note(noteCapture, noteDescription, new Date().getTime(), noteContent);
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
