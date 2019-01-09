package com.example.starock.quickkeep.User;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.starock.quickkeep.R;

public class UserMainActivity extends AppCompatActivity implements View.OnClickListener {
    RelativeLayout howtosearch;
    RelativeLayout center;
    RelativeLayout setting;
    RelativeLayout suggestion;
    RelativeLayout upload;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main);

        howtosearch=findViewById(R.id.layout_way);
        center=findViewById(R.id.layout_center);
        setting=findViewById(R.id.layout_setting);
        suggestion=findViewById(R.id.layout_sugg);
        upload=findViewById(R.id.layout_upload);

        howtosearch.setOnClickListener(this);
        center.setOnClickListener(this);
        setting.setOnClickListener(this);
        suggestion.setOnClickListener(this);
        upload.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.layout_way:
                //如何收集
                break;
            case R.id.layout_center:
                //个人中心
                break;
            case R.id.layout_setting:
                Intent intent=new Intent(UserMainActivity.this,SettingActivity.class);
                startActivity(intent);
                break;
            case R.id.layout_sugg:
                Intent intent1=new Intent(UserMainActivity.this,UseHelperActivity.class);
                startActivity(intent1);
                break;
            case R.id.layout_upload:
                break;
        }
    }

    /*public void gotoaboutus(View view){

    }*/
}
