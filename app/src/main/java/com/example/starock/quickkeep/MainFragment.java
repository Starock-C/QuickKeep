package com.example.starock.quickkeep;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.starock.quickkeep.Database.Note;
import com.example.starock.quickkeep.Note.ModifyNoteActivity;
import com.example.starock.quickkeep.Note.NoteAdapter;
import com.example.starock.quickkeep.Note.TakeNoteActivity;
import com.example.starock.quickkeep.Search.SearchActivity;
import com.example.starock.quickkeep.User.UserMainActivity;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

public class MainFragment extends Fragment {
    public static List<Note> noteList = new ArrayList<>();
    public static NoteAdapter noteAdapter = new NoteAdapter(noteList);
    private RecyclerView recyclerView;
    public static Button btn_search;
    public static int isSearch;

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main,container,false);

        recyclerView = view.findViewById(R.id.recyclerview_main_notes);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        recyclerView.setAdapter(noteAdapter);

        btn_search = view.findViewById(R.id.button_search_notes);
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),SearchActivity.class);
                startActivity(intent);
            }
        });
        FloatingActionButton takeNote = view.findViewById(R.id.float_main_note);

        //悬浮按钮-进入用户界面
        FloatingActionButton  userInterface = view.findViewById(R.id.float_main_user);

        userInterface.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),UserMainActivity.class);
                startActivity(intent);
            }
        });

        takeNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),TakeNoteActivity.class);
                startActivity(intent);
            }
        });

        noteAdapter.setOnItemClickListener(new NoteAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                Note note = noteList.get(position);
                Toast.makeText(getContext(),"find note "+note.getTitle(),Toast.LENGTH_SHORT).show();

                Log.i("MainFragment","find note"+Integer.toString(note.getId()));
                Intent intent = new Intent();
                intent.setClass(getActivity(),ModifyNoteActivity.class);
                intent.setAction("Action");
                intent.putExtra("id",note.getId());
                getActivity().startActivity(intent);
            }
        });
        Log.i("MainFragment","onCreateView");

        ItemTouchHelper.Callback callback = new ItemTouchHelperCallback(noteAdapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(recyclerView);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isSearch == 0)
            initNotes();
        noteAdapter.notifyDataSetChanged();
        isSearch = 0;
    }

    public void initNotes(){
        noteList.clear();
        noteList.addAll(LitePal.findAll(Note.class));
        Log.i("MainFragment","success");
    }

}


