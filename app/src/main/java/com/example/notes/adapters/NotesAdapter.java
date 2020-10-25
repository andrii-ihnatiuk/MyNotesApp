package com.example.notes.adapters;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.notes.Note;
import com.example.notes.R;
import com.example.notes.listeners.NotesListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NoteViewHolder> {

    private List<Note> notes;
    private NotesListener notesListener;
    private Timer timer;
    private List<Note> notesSource;

    public NotesAdapter(List<Note> notes, NotesListener notesListener) {
        this.notes = notes;
        this.notesListener = notesListener;
        notesSource = notes;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_container_note, parent, false);
        NoteViewHolder holder = new NoteViewHolder(v);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, final int position) {
        if (notes.get(position).getSubtitle().isEmpty()) {
            holder.textSubtitle.setVisibility(View.GONE);
        } else {
            holder.textSubtitle.setVisibility(View.VISIBLE);
            holder.textSubtitle.setText(notes.get(position).getSubtitle());
        }
        holder.textTitle.setText(notes.get(position).getTitle());
        holder.textDateTime.setText(notes.get(position).getDateTime());

        if (notes.get(position).getColor() != null) { // если в заметке указан ее цвет то применяем его для holder
            holder.layoutNote.getBackground().setColorFilter(Color.parseColor(notes.get(position).getColor()), PorterDuff.Mode.SRC_OVER);
        } else  { // в противном случае ставим цвет по умолчанию
            holder.layoutNote.getBackground().setColorFilter(Color.parseColor("#333333"),  PorterDuff.Mode.SRC_OVER);
        }
        holder.layoutNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // в случае если был произведен поиск, то есть список был изменен
                // тогда берем индекс элемента не из текущего списка а из изначального
                // это нужно чтобы операция изменения элемента работала корректно когда вызывается из окна поиска
                if (notes != notesSource) {
                    int newPos = notesSource.indexOf(notes.get(position));
                    notesListener.onNoteClicked(notes.get(position), newPos);
                } else {
                    notesListener.onNoteClicked(notes.get(position), position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }


    static class NoteViewHolder extends RecyclerView.ViewHolder {

        TextView textTitle, textSubtitle, textDateTime;
        LinearLayout layoutNote;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.textTitle);
            textSubtitle = itemView.findViewById(R.id.textSubtitle);
            textDateTime = itemView.findViewById(R.id.textDateTime);
            layoutNote = itemView.findViewById(R.id.layoutNote);
        }
    }


    public void searchNotes(final String searchKeyword) {
        timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                notes = notesSource;

                if(!searchKeyword.trim().isEmpty()) {
                    ArrayList<Note> temp = new ArrayList<>();
                    for (Note note : notes) {
                        if (note.getTitle().toLowerCase().contains(searchKeyword.toLowerCase())
                                || note.getSubtitle().toLowerCase().contains(searchKeyword.toLowerCase())
                                || note.getNoteText().toLowerCase().contains(searchKeyword.toLowerCase())) {
                            temp.add(note);
                        }
                    }
                    notes = temp;
                }

                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        notifyDataSetChanged();
                    }
                });
            }
        };
        timer.schedule(timerTask, 500);
    }

    public  void cancelTimer() {
        if (timer != null) {
            timer.cancel();
        }
    }
}
