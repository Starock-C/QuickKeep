package com.example.starock.quickkeep.User;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.starock.quickkeep.AnalysisUtils;
import com.example.starock.quickkeep.R;

public class PersonalActivity extends AppCompatActivity implements View.OnClickListener {
    RelativeLayout user_manager;
    RelativeLayout personal_data;
    RelativeLayout out;
    FloatingActionButton floatingActionButton;
    TextView username,message;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);
        user_manager = findViewById(R.id.re1);
        personal_data = findViewById(R.id.re2);
        out = findViewById(R.id.re3);
        floatingActionButton = findViewById(R.id.fab_personal);
        username=findViewById(R.id.text_username);
        message=findViewById(R.id.text_message);
        username.setText(AnalysisUtils.readPersonalName(this));
        message.setText(AnalysisUtils.readMessagee(this));
        user_manager.setOnClickListener(this);
        personal_data.setOnClickListener(this);
        out.setOnClickListener(this);
        floatingActionButton.setOnClickListener(this);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.re1:
                Intent intent = new Intent(PersonalActivity.this, UserManagerActivity.class);
                startActivity(intent);
                break;
            case R.id.re2:
                Intent intent1 = new Intent(PersonalActivity.this, PersonalDataActivity.class);
                startActivity(intent1);
                break;
            case R.id.re3:
                if (AnalysisUtils.readLoginStatus(this)) {
                    AnalysisUtils.cleanLoginStatus(this);
                    finish();
                }
                break;
            case R.id.fab_personal:
                finish();

        }
    }
}