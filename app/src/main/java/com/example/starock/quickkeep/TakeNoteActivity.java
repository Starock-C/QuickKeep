package com.example.starock.quickkeep;

import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.starock.quickkeep.Database.Note;
import com.example.starock.quickkeep.Database.NoteType;

import org.litepal.LitePal;
import org.litepal.crud.LitePalSupport;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TakeNoteActivity extends AppCompatActivity {
    private static List<NoteType> noteTypeList = new ArrayList<>();
    private static SelectTypeAdapter selectTypeAdapter = new SelectTypeAdapter(noteTypeList);
    private EditText title;
    private EditText content;
    private EditText newType;
    private Button addType;
    private NoteType noteType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_note);

        final RecyclerView recyclerView = findViewById(R.id.recyclerview_add_type);
//        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,5);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(5,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        recyclerView.setAdapter(selectTypeAdapter);

        FloatingActionButton addNote = findViewById(R.id.float_addNote_add);
        addNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title = findViewById(R.id.edittext_takenote_title);
                content = findViewById(R.id.edittext_takenote_content);
                Note newNote = new Note();
                newNote.setTitle("无标题");
                if (!title.getText().toString().equals(""))
                    newNote.setTitle(title.getText().toString());
                newNote.setContent(content.getText().toString());
                newNote.setDatetime(new Date(System.currentTimeMillis()));
                newNote.setSource("无来源");
                if (noteType != null)
                    newNote.setType(noteType.getName());
                else newNote.setType("未分类");
                NoteType noteType = LitePal.where("name = ?",newNote.getType()).findFirst(NoteType.class);
                if (noteType == null){
                    NoteType newNoteType = new NoteType();
                    newNoteType.setName(newNote.getType());
                    newNoteType.setCount(1);
                    newNoteType.save();
                }
                else {
                    noteType.setCount(noteType.getCount()+1);
                    noteType.save();
                }
                newNote.save();
                finish();
            }
        });

        selectTypeAdapter.setOnItemClickListener(new SelectTypeAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                SelectTypeAdapter.ViewHolder viewHolder = (SelectTypeAdapter.ViewHolder) recyclerView.getChildViewHolder(recyclerView.getChildAt(position));
                noteType = noteTypeList.get(position);
                Toast.makeText(TakeNoteActivity.this,"find type "+noteType.getName(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        initTypes();
        addType = findViewById(R.id.btn_takenote_addType);
        newType = findViewById(R.id.edittext_takenote_newType);
        addType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!newType.getText().toString().equals("")){
                    if (LitePal.where("name = ?",newType.getText().toString()).find(NoteType.class).isEmpty()){
                        NoteType newNoteType = new NoteType();
                        newNoteType.setCount(0);
                        newNoteType.setName(newType.getText().toString());
                        newNoteType.save();
                        AsyncTaskThread asyncTaskThread = new AsyncTaskThread();
                        asyncTaskThread.doInBackground();
                    }
                }


            }
        });
    }

    private void initTypes() {
        noteTypeList.clear();
        noteTypeList.addAll(LitePal.where("name <> '未分类'").find(NoteType.class));

    }


    class AsyncTaskThread extends AsyncTask<String, Integer, Boolean> {
        @Override
        protected Boolean doInBackground(String... strings) {
            initTypes();
            return true;
        }
    }
}
