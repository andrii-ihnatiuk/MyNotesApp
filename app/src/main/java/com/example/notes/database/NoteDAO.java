package com.example.notes.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.notes.Note;

import java.util.List;

@Dao
public interface NoteDAO  {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertNote(Note note);

    @Delete
    void deleteNote(Note note);

    @Update
    void updateNote(Note note);

    @Query("DELETE FROM notes WHERE id= :id")
    void deleteNoteById(int id);

    @Query("DELETE FROM notes")
    void deleteAll();

    @Query("SELECT * FROM notes WHERE id= :id")
    Note getNoteById(int id);

    @Query("SELECT * FROM notes ORDER BY dateTime DESC")
    List<Note> getNotes();

}
