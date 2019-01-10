package com.example.starock.quickkeep.User;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.starock.quickkeep.R;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener {
    RelativeLayout aboutuslayout;
    RelativeLayout useHelper;
    RelativeLayout cleardata;
    TextView cachesum;
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_setting);

        aboutuslayout=findViewById(R.id.layout_aboutus);
        useHelper=findViewById(R.id.layout_help);
        cleardata=findViewById(R.id.layout_clear);
        cachesum=findViewById(R.id.cachesum);
        back=findViewById(R.id.im_back_ac);

        aboutuslayout.setOnClickListener(this);
        useHelper.setOnClickListener(this);
        cleardata.setOnClickListener(this);
        back.setOnClickListener(this);
        try{
            cachesum.setText(DataCleanManagerUtil.getTotalCacheSize(SettingActivity.this));
            cachesum.setTextColor(getResources().getColor(R.color.colorBlack));
        }catch (Exception e){
            e.printStackTrace();
        }

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
                DataCleanManagerUtil.clearAllCache(SettingActivity.this);
                Toast.makeText(SettingActivity.this,"清楚缓存成功！",Toast.LENGTH_LONG).show();
                cachesum.setText("0B");
                cachesum.setTextColor(getResources().getColor(R.color.gray));
                break;
            case R.id.im_back_ac:
                SettingActivity.this.finish();
                break;
        }
    }
}