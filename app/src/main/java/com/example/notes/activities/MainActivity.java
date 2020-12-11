package com.example.notes.activities;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import com.example.notes.Note;
import com.example.notes.R;
import com.example.notes.adapters.NotesAdapter;
import com.example.notes.listeners.NotesListener;
import com.example.notes.viewModels.MainViewModel;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NotesListener {

    public static final int REQUEST_CODE_SHOW_NOTES = 0;
    public static final int REQUEST_CODE_ADD_NOTE = 1;
    public static final int REQUEST_CODE_UPDATE_NOTE = 2;
    public static final int REQUEST_CODE_DELETE_NOTE = 3;

    private RecyclerView notesRecyclerView;
    private List<Note> noteList;
    private NotesAdapter notesAdapter;
    private int noteClickedPosition = -1;

    private MainViewModel mViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView imageAddNoteMain = findViewById(R.id.imageAddNoteMain);
        imageAddNoteMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearInputSearchOrClearFocus();
                Intent intent = new Intent(getApplicationContext(), CreateNoteActivity.class);
                startActivityForResult(intent, REQUEST_CODE_ADD_NOTE);
            }
        });

        notesRecyclerView = findViewById(R.id.notesRecyclerView);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        notesRecyclerView.setLayoutManager(layoutManager);

        initViewModel();

        noteList = new ArrayList<>();
        notesAdapter = new NotesAdapter(noteList, MainActivity.this);
        notesRecyclerView.setAdapter(notesAdapter);

        getNotes(REQUEST_CODE_SHOW_NOTES);

        final EditText inputSearch = findViewById(R.id.inputSearch);

        inputSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                notesAdapter.cancelTimer(); // отменяем таймер, очищается поток
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (noteList.size() != 0) {
                    notesAdapter.searchNotes(s.toString()); // создается новый поток поиска
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data == null && resultCode == REQUEST_CODE_DELETE_NOTE) { // Если не было получено данных то значит было запрошено удаление заметки
            getNotes(REQUEST_CODE_DELETE_NOTE);
        }
        // если был послан запрос на обновление и получен код об успешном завершении
        // тогда обновляем существующую заметку
        if (requestCode == REQUEST_CODE_UPDATE_NOTE && resultCode == RESULT_OK) {
            getNotes(REQUEST_CODE_UPDATE_NOTE);
        }
        // если был послан запрос на добавление и получено код об успешном завершении
        // тогда добавляем полученную заметку
        if (requestCode == REQUEST_CODE_ADD_NOTE && resultCode == RESULT_OK) {
            getNotes(REQUEST_CODE_ADD_NOTE);
        }
    }

    private void getNotes(final int REQUEST_CODE) {

        final List<Note> data = new ArrayList<>(mViewModel.getAllNotes());

        if (REQUEST_CODE == REQUEST_CODE_SHOW_NOTES) {
            noteList.addAll(data);
            notesAdapter.notifyDataSetChanged();
        } else if (REQUEST_CODE == REQUEST_CODE_ADD_NOTE) {
            noteList.add(0, data.get(0));
            notesAdapter.notifyItemInserted(0);
            notesRecyclerView.smoothScrollToPosition(0);
        } else if (REQUEST_CODE == REQUEST_CODE_UPDATE_NOTE) {
            noteList.remove(noteClickedPosition);
            noteList.add(noteClickedPosition, data.get(noteClickedPosition));
            notesAdapter.notifyItemChanged(noteClickedPosition);
        } else if (REQUEST_CODE == REQUEST_CODE_DELETE_NOTE) {
            noteList.remove(noteClickedPosition);
            notesAdapter.notifyItemRemoved(noteClickedPosition);
        }

    }

    @Override
    public void onNoteClicked(Note note, int position) {
        // при нажатии на заметку запускаем Activity для просмотра заметки
        noteClickedPosition = position;
        Intent intent = new Intent(getApplicationContext(), CreateNoteActivity.class);
        intent.putExtra("isViewOrUpdate", true);
        intent.putExtra("note_id", note.getId());
        startActivityForResult(intent, REQUEST_CODE_UPDATE_NOTE);
        clearInputSearchOrClearFocus();
    }

    /* Функция для очистки поля поиска и удаления с неё фокуса */
    public void clearInputSearchOrClearFocus() {
        EditText inputSearch = findViewById(R.id.inputSearch);
        inputSearch.setText("");
        inputSearch.clearFocus();
    }


    private void initViewModel() {
        mViewModel = new ViewModelProvider(this, ViewModelProvider
                .AndroidViewModelFactory
                .getInstance(this.getApplication()))
                .get(MainViewModel.class);
    }

}