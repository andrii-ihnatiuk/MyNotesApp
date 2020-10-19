package com.example.notes.listeners;

import com.example.notes.Note;

public interface NotesListener {
    void onNoteClicked(Note note, int position);
}
