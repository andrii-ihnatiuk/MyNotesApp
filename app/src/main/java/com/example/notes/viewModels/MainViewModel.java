package com.example.notes.viewModels;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import com.example.notes.Note;
import com.example.notes.database.NotesRepository;
import java.util.List;

public class MainViewModel extends AndroidViewModel {
    private NotesRepository repository;

    public MainViewModel(@NonNull Application application) {
        super(application);
        repository = NotesRepository.getInstance(application);
    }

    public List<Note> getAllNotes() {
        return repository.getAllNotes();
    }
}
