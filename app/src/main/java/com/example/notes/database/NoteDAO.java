package com.example.notes.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.example.notes.Note;
import java.util.List;

@Dao
public interface NoteDAO  {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertNote(Note note);

    @Delete
    void deleteNote(Note note);

    @Query("DELETE FROM notes WHERE id= :note_id")
    void deleteNoteById(int note_id);

    @Query("DELETE FROM notes")
    void deleteAll();

    @Query("SELECT * FROM notes WHERE id= :note_id")
    Note getNoteById(int note_id);

    @Query("SELECT * FROM notes ORDER BY dateTime DESC")
    List<Note> getNotes();

}
