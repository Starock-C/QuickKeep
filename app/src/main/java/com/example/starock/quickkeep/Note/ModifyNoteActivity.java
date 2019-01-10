package com.example.starock.quickkeep.Note;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.starock.quickkeep.Database.Note;
import com.example.starock.quickkeep.Database.NoteType;
import com.example.starock.quickkeep.R;
import com.example.starock.quickkeep.Drawer.SelectTypeAdapter;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

public class ModifyNoteActivity extends AppCompatActivity {
    private static List<NoteType> noteTypeList = new ArrayList<>();
    private static SelectTypeAdapter selectTypeAdapter = new SelectTypeAdapter(noteTypeList);
    private EditText title;
    private EditText content;
    private EditText newType;
    private Button addType;
    private NoteType noteType;
    private NoteType oldType;
    private Note note;
    private int oldPosition;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_note);
        LitePal.initialize(this);

        final RecyclerView recyclerView = findViewById(R.id.recyclerview_modify_add_type);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(5,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        recyclerView.setAdapter(selectTypeAdapter);

        ImageView save = findViewById(R.id.float_modifyNote_add);

        title = findViewById(R.id.edittext_modifynote_title);
        content = findViewById(R.id.edittext_modifynote_content);

        Intent intent = getIntent();
        int id = 0;
        if ("Action".equals(intent.getAction())) {
            id = intent.getIntExtra("id",0);
        }
        final int ID = id;
        note = LitePal.where("id = ?",Integer.toString(ID)).findFirst(Note.class);
        oldType = LitePal.where("name = ?",note.getType()).findFirst(NoteType.class);
//        Toast.makeText(ModifyNoteActivity.this,"find type "+oldType.getName(),Toast.LENGTH_SHORT).show();
        title.setText(note.getTitle());
        content.setText(note.getContent());

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                note.setTitle(title.getText().toString());
                note.setContent(content.getText().toString());
                if (note.getTitle().equals(""))
                    note.setTitle("无标题");
                if (noteType != null && !noteType.getName().equals(oldType.getName())){
                    note.setType(noteType.getName());
                    noteType.setCount(noteType.getCount()+1);
                    oldType.setCount(oldType.getCount()-1);
                    noteType.save();
                    oldType.save();
                }
                note.save();
                finish();
            }
        });

        selectTypeAdapter.setOnItemClickListener(new SelectTypeAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                recyclerView.getChildAt(oldPosition).setBackgroundResource(R.color.colorLightGray);
                noteType = noteTypeList.get(position);
                recyclerView.getChildAt(position).setBackgroundResource(R.color.colorGray);
                selectTypeAdapter.notifyDataSetChanged();
                oldPosition = position;
//                Toast.makeText(ModifyNoteActivity.this,"find type "+noteType.getName(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        initTypes();
        addType = findViewById(R.id.btn_modifynote_addType);
        newType = findViewById(R.id.edittext_modifynote_newType);
        addType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!newType.getText().toString().equals("")){
                    if (LitePal.where("name = ?",newType.getText().toString()).find(NoteType.class).isEmpty()){
                        NoteType newNoteType = new NoteType();
                        newNoteType.setCount(0);
                        newNoteType.setName(newType.getText().toString());
                        newNoteType.save();
                    }
                }
            }
        });
    }

    private void initTypes() {
        noteTypeList.clear();
        noteTypeList.addAll(LitePal.where("name <> '未分类' and name <> '剪贴板'").find(NoteType.class));
    }
}
