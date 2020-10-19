package com.example.notes.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.notes.Note;
import com.example.notes.R;
import com.example.notes.adapters.NotesAdapter;
import com.example.notes.listeners.NotesListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NotesListener {

    public static final int REQUEST_CODE_ADD_NOTE = 1;
    public static final int REQUEST_CODE_UPDATE_NOTE = 2;
    public static final int REQUEST_CODE_DELETE_NOTE = 3;

    private RecyclerView notesRecyclerView;
    private List<Note> noteList;
    private NotesAdapter notesAdapter;

    private int noteClickedPosition = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView imageAddNoteMain = findViewById(R.id.imageAddNoteMain);
        imageAddNoteMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CreateNoteActivity.class);
                startActivityForResult(intent, REQUEST_CODE_ADD_NOTE);
            }
        });

        notesRecyclerView = findViewById(R.id.notesRecyclerView);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        notesRecyclerView.setLayoutManager(layoutManager);

        noteList = new ArrayList<>();
        notesAdapter = new NotesAdapter(noteList, this);
        notesRecyclerView.setAdapter(notesAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data == null) {
            if (resultCode == REQUEST_CODE_DELETE_NOTE) {
                noteList.remove(noteClickedPosition);
                //List<Note> newList = new ArrayList<>(noteList);
                /*notesAdapter = new NotesAdapter(noteList, this);
                notesRecyclerView.setAdapter(notesAdapter);*/
                //notesAdapter.notifyItemRemoved(noteClickedPosition);


                notesAdapter.notifyItemRangeChanged(noteClickedPosition, noteList.size());
                notesAdapter.notifyDataSetChanged();

            }
            return;
        }
        Note note = (Note) data.getSerializableExtra("note");
        if (requestCode == REQUEST_CODE_UPDATE_NOTE) {
            noteList.set(noteClickedPosition, note);
            notesAdapter.notifyItemChanged(noteClickedPosition);
        }
        if (requestCode == REQUEST_CODE_ADD_NOTE) {
            noteList.add(0, note);
            notesAdapter.notifyItemInserted(0);
        }

    }

    @Override
    public void onNoteClicked(Note note, int position) {
        noteClickedPosition = position;
        Intent intent = new Intent(getApplicationContext(), CreateNoteActivity.class);
        intent.putExtra("isViewOrUpdate", true);
        intent.putExtra("note", note);
        startActivityForResult(intent, REQUEST_CODE_UPDATE_NOTE);

    }
}