package com.example.starock.quickkeep;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.starock.quickkeep.Database.Note;
import com.example.starock.quickkeep.Database.NoteType;

import org.litepal.LitePal;
import org.litepal.crud.LitePalSupport;

import java.util.Date;
import java.util.List;

public class TakeNoteActivity extends AppCompatActivity {

    private EditText title;
    private EditText content;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_note);

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
                newNote.setType("未分类");
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
    }
}
