package com.example.notes.database;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.example.notes.Note;

@Database(entities = {Note.class}, version = 1)
public abstract class NotesDatabase extends RoomDatabase {

    public static final String DATABASE_NAME = "notes_db.db";
    private static volatile NotesDatabase instance;

    private static final Object LOCK = new Object();

    public abstract NoteDAO noteDAO();

    public static NotesDatabase getInstance(Context context) {
        if (instance == null) {
            synchronized (LOCK) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context, NotesDatabase.class, DATABASE_NAME).build();
                }
            }
        }
        return instance;
    }
}
