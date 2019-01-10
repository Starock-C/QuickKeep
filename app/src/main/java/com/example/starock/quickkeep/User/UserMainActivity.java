package com.example.starock.quickkeep.User;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.starock.quickkeep.AnalysisUtils;
import com.example.starock.quickkeep.LoginActivity;
import com.example.starock.quickkeep.R;

public class UserMainActivity extends AppCompatActivity implements View.OnClickListener {
    RelativeLayout howtosearch;
    RelativeLayout center;
    RelativeLayout setting;
    RelativeLayout suggestion;
    RelativeLayout upload;
    ImageView close;
    TextView email,username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main);

        howtosearch=findViewById(R.id.layout_way);
        center=findViewById(R.id.layout_center);
        setting=findViewById(R.id.layout_setting);
        suggestion=findViewById(R.id.layout_sugg);
        upload=findViewById(R.id.layout_upload);
        close=findViewById(R.id.im_close_ac);
        email=(TextView)findViewById(R.id.email);
        username=(TextView)findViewById(R.id.username);

        if (AnalysisUtils.readLoginStatus(this)){
            email.setText(AnalysisUtils.readLoginUserName(this));
            username.setText(AnalysisUtils.readPersonalName(this));
        }

        howtosearch.setOnClickListener(this);
        center.setOnClickListener(this);
        setting.setOnClickListener(this);
        suggestion.setOnClickListener(this);
        upload.setOnClickListener(this);
        close.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.layout_way:
                //如何收集
                Intent intent=new Intent(UserMainActivity.this,Viewpage_collectActivity.class);
                startActivity(intent);
                break;
            case R.id.layout_center:
                //个人中心
                if(AnalysisUtils.readLoginStatus(this)){
                    Intent intent1=new Intent(UserMainActivity.this,PersonalActivity.class);
                    startActivity(intent1);
                }
                else {
                    Intent intent1=new Intent(UserMainActivity.this,LoginActivity.class);
                    startActivity(intent1);
                }
                break;
            case R.id.layout_setting:
                Intent intent3=new Intent(UserMainActivity.this,SettingActivity.class);
                startActivity(intent3);
                break;
            case R.id.layout_sugg:
                Intent intent4=new Intent(UserMainActivity.this,UseHelperActivity.class);
                startActivity(intent4);
                break;
            case R.id.layout_upload:
                break;
            case R.id.im_close_ac:
                UserMainActivity.this.finish();
                break;
        }
    }

    /*public void gotoaboutus(View view){

    }*/
}
