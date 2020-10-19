package com.example.notes;

import java.io.Serializable;

public class Note implements Serializable {
    private String title;

    private String dateTime;

    private String subtitle;

    private String noteText;

    private String color;


    public String getTitle() {
        return title;
    }

    public String getDateTime() {
        return dateTime;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public String getNoteText() {
        return noteText;
    }

    public String getColor() {
        return color;
    }

    public void setTitle(String title) {
        this.title = title;
    }

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
}
