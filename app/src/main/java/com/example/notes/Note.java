package com.example.notes;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import java.io.Serializable;

@Entity(tableName = "notes")
public class Note implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private String dateTime;
    private String subtitle;
    private String noteText;
    private String color;


    public Note(int id, String title, String dateTime, String subtitle, String noteText, String color) {
        this.id = id;
        this.title = title;
        this.dateTime = dateTime;
        this.subtitle = subtitle;
        this.noteText = noteText;
        this.color = color;
    }

    @Ignore
    public Note(String title, String dateTime, String subtitle, String noteText, String color) {
        this.title = title;
        this.dateTime = dateTime;
        this.subtitle = subtitle;
        this.noteText = noteText;
        this.color = color;
    }

    @Ignore
    public Note(){ }

    public int getId() {return id;}

    public String getTitle() {return title;}

    public String getDateTime() {return dateTime;}

    public String getSubtitle() {return subtitle;}

    public String getNoteText() {return noteText;}

    public String getColor() {return color;}

    public void setId(int id) {this.id = id;}

    public void setTitle(String title) {this.title = title;}

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public void setNoteText(String noteText) {
        this.noteText = noteText;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @NonNull
    @Override
    public String toString() {
        return title + ":" + dateTime;
    }
}
