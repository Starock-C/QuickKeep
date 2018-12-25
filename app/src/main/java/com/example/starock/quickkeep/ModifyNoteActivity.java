package com.example.starock.quickkeep;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.starock.quickkeep.Database.Note;

import org.litepal.LitePal;

public class ModifyNoteActivity extends AppCompatActivity {
    private EditText title;
    private EditText content;
    private FloatingActionButton save;
    private Note note;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_note);


        title = findViewById(R.id.edittext_modifynote_title);
        content = findViewById(R.id.edittext_modifynote_content);
        save = findViewById(R.id.float_modifyNote_add);

        Intent intent = getIntent();
        int id = 0;
        if ("Action".equals(intent.getAction())) {
            id = intent.getIntExtra("id",0);
        }
        final int ID = id;
        note = LitePal.where("id = ?",Integer.toString(ID)).findFirst(Note.class);

        AsyncTaskThread asyncTask = new AsyncTaskThread();
        asyncTask.doInBackground(Integer.toString(ID));

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title = findViewById(R.id.edittext_modifynote_title);
                content = findViewById(R.id.edittext_modifynote_content);

                note.setTitle(title.getText().toString());
                note.setContent(content.getText().toString());
                if (note.getTitle().equals(""))
                    note.setTitle("无标题");
                note.save();
                finish();
            }
        });
    }

    class AsyncTaskThread extends AsyncTask<String, Integer, Boolean>{
        @Override
        protected Boolean doInBackground(String... strings) {
//            title.setText(note.getTitle());
//            content.setText(note.getContent());
            if (note.getTitle().equals("无标题"))
                title.setText("");
            else title.setText(note.getTitle());
            content.setText(note.getContent());
            return true;
        }
    }
}
