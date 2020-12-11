package com.example.notes.viewModels;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import com.example.notes.Note;
import com.example.notes.database.NotesRepository;

public class NoteViewModel extends AndroidViewModel {
    private NotesRepository repository;

    public NoteViewModel(@NonNull Application application) {
        super(application);
        repository = NotesRepository.getInstance(application);
    }

    public void deleteNoteById(int note_id) {
        repository.deleteNoteById(note_id);
    }

    public void insertNote(Note note) {
        repository.insertNote(note);
    }

    public Note getNoteById(int note_id) {
        return repository.getNoteById(note_id);
    }
}
