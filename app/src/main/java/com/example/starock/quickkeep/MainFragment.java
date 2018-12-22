package com.example.starock.quickkeep;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.starock.quickkeep.Database.Note;
import com.example.starock.quickkeep.Database.NoteType;
import com.example.starock.quickkeep.Drawer.NoteTypeAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainFragment extends Fragment {
    private static List<Note> noteList = new ArrayList<>();
    private static NoteAdapter noteAdapter = new NoteAdapter(noteList);

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_main,container,false);
        initNotes();
        RecyclerView recyclerView = view.findViewById(R.id.recyclerview_main_notes);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        recyclerView.setAdapter(noteAdapter);

        FloatingActionButton takeNote = view.findViewById(R.id.float_main_note);
        //悬浮按钮-进入用户界面
        FloatingActionButton  userInterface = view.findViewById(R.id.float_main_user);


        takeNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view.getContext(),TakeNoteActivity.class);
                startActivity(intent);
            }
        });


        return view;
    }

    public void initNotes(){
        noteList.clear();
        for (int i = 0; i <= 5; i++){
            Note note = new Note();
            note.setTitle("1");
            note.setContent("111\n222\n333\n444");
            Note note1 = new Note();
            note1.setTitle("2");
            note1.setContent("222\n333");
            Note note2 = new Note();
            note2.setTitle("3");
            note2.setContent("333");
            noteList.add(note);
            noteList.add(note1);
            noteList.add(note2);
        }

    }
}


