package com.example.starock.quickkeep;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.starock.quickkeep.Database.Note;
import com.example.starock.quickkeep.Database.NoteType;
import com.example.starock.quickkeep.Drawer.NoteTypeAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainFragment extends Fragment {
    private static List<NoteType> noteTypeList = new ArrayList<>();
    private static NoteTypeAdapter noteTypeAdapter = new NoteTypeAdapter(noteTypeList);
    private static List<Note> noteList = new ArrayList<>();
    private static NoteAdapter noteAdapter = new NoteAdapter(noteList);

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main,container,false);
        initTypes();
        initNotes();
        RecyclerView recyclerView = view.findViewById(R.id.recyclerview_main_notes);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(noteAdapter);

        return view;
    }

    public void initTypes(){
        noteTypeList.clear();
        NoteType noteType = new NoteType();
        noteType.setName("未分类");
        noteType.setCount(0);
        NoteType noteType1 = new NoteType();
        noteType1.setName("全部");
        noteType1.setCount(0);
        noteTypeList.add(noteType);
        noteTypeList.add(noteType1);
    }

    public void initNotes(){
        noteList.clear();
        Note note = new Note();
        note.setTitle("1");
        note.setContent("111");
        Note note1 = new Note();
        note1.setTitle("2");
        note1.setContent("222");
        noteList.add(note);
        noteList.add(note1);
    }
}


