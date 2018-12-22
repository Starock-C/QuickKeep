package com.example.starock.quickkeep;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.starock.quickkeep.Database.Note;

import java.text.SimpleDateFormat;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {
    private List<Note> noteList;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView noteTitle;
        TextView noteTime;
        TextView noteContent;
        public ViewHolder(View view){
            super(view);
            noteTitle = view.findViewById(R.id.textview_main_title);
            noteTime = view.findViewById(R.id.textview_main_time);
            noteContent = view.findViewById(R.id.textview_main_content);
        }
    }
    public NoteAdapter(List<Note> noteList){
        this.noteList = noteList;
    }
    @NonNull
    @Override
    public NoteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.main_note_item,viewGroup,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NoteAdapter.ViewHolder viewHolder, int i) {
        Note note = noteList.get(i);
        viewHolder.noteTitle.setText(note.getTitle());
        viewHolder.noteTime.setText(sdf.format(note.getDatetime()));
        viewHolder.noteContent.setText(note.getContent());
    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }
}
