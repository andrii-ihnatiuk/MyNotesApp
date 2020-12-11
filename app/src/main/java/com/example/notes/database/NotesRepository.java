package com.example.notes.database;

import android.content.Context;
import com.example.notes.Note;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class NotesRepository {

    private Executor executor = Executors.newSingleThreadExecutor();
    private static NotesRepository instance;
    private NotesDatabase db;

    public static NotesRepository getInstance(Context context) {
        if (instance == null) {
            instance = new NotesRepository(context);
        }
        return instance;
    }

    private NotesRepository(Context context) {
        db = NotesDatabase.getInstance(context);
    }

    public List<Note> getAllNotes() {
        List<Note> data = new ArrayList<>();

        ExecutorService es = Executors.newSingleThreadExecutor();
        Future<List<Note>> result = es.submit(new Callable<List<Note>>() {
            @Override
            public List<Note> call() throws Exception {
                return db.noteDAO().getNotes();
            }
        });

        try {
            data = result.get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        es.shutdown();

        return data;
    }

    public void deleteNoteById(final int note_id) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                db.noteDAO().deleteNoteById(note_id);
            }
        });
    }

    public void deleteAll() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                db.noteDAO().deleteAll();
            }
        });
    }

    public void insertNote(final Note note) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                db.noteDAO().insertNote(note);
            }
        });
    }

    public Note getNoteById(final int note_id) {
        ExecutorService es = Executors.newSingleThreadExecutor();
        Future<Note> result = es.submit(new Callable<Note>() {
            @Override
            public Note call() throws Exception {
                return db.noteDAO().getNoteById(note_id);
            }
        });

        try {
            return result.get();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            es.shutdown();
        }
    }
}

