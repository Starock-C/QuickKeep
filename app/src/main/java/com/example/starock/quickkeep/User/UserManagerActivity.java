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

public class UserManagerActivity extends AppCompatActivity /*implements View.OnClickListener*/{
    RelativeLayout user;
    RelativeLayout modify_psd;
    TextView user_name;
    FloatingActionButton floatingActionButton;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_manager);
        user=(RelativeLayout)findViewById(R.id.rea_user);
        modify_psd=(RelativeLayout)findViewById(R.id.rea_modify);
        user_name=(TextView)findViewById(R.id.text_email);
        floatingActionButton=(FloatingActionButton) findViewById(R.id.fab_user_manager);
        user_name.setText(AnalysisUtils.readLoginUserName(this));
        modify_psd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(UserManagerActivity.this,ModifyPwdActivity.class);
                startActivity(intent);
            }
        });
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
