package com.example.starock.quickkeep.User;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.starock.quickkeep.R;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener {
    RelativeLayout aboutuslayout;
    RelativeLayout useHelper;
    RelativeLayout cleardata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_setting);

        aboutuslayout=findViewById(R.id.layout_aboutus);
        useHelper=findViewById(R.id.layout_help);
        cleardata=findViewById(R.id.layout_clear);
        aboutuslayout.setOnClickListener(this);
        useHelper.setOnClickListener(this);
        cleardata.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.layout_aboutus:
                Intent intent=new Intent(SettingActivity.this,AboutusActivity.class);
                startActivity(intent);
                break;
            case R.id.layout_help:
                //
                break;
            case R.id.layout_clear:
                //
                break;
        }
    }
}