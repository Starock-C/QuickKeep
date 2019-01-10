package com.example.starock.quickkeep.User;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.starock.quickkeep.R;

public class PersonalDataActivity extends AppCompatActivity {
    EditText username,message;
    ImageView check;
    FloatingActionButton fab_data;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_data);
        username=(EditText)findViewById(R.id.edit_person_name);
        message=(EditText)findViewById(R.id.edit_message);
        check=(ImageView)findViewById(R.id.img_check_data);
        fab_data=(FloatingActionButton)findViewById(R.id.fab_personal_data);
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor=getSharedPreferences("loginInfo",MODE_PRIVATE).edit();
                editor.putString("username",username.getText().toString());
                editor.putString("message",message.getText().toString());
                editor.commit();
            }
        });
        fab_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
