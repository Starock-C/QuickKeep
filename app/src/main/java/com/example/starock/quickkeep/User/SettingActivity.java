package com.example.starock.quickkeep.User;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.starock.quickkeep.BaseApplication;
import com.example.starock.quickkeep.R;

import static com.example.starock.quickkeep.BaseApplication.IS_FOREGROUND;
import static com.example.starock.quickkeep.BaseApplication.LOCKSTATION;
import static com.example.starock.quickkeep.MainActivity.isAppOnForeground;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener {
    RelativeLayout aboutuslayout;
    RelativeLayout useHelper;
    RelativeLayout cleardata;
    TextView cachesum;
    ImageView back;
    Switch lockswitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_setting);

        aboutuslayout=findViewById(R.id.layout_aboutus);
        useHelper=findViewById(R.id.layout_help);
        cleardata=findViewById(R.id.layout_clear);
        cachesum=findViewById(R.id.cachesum);
        back=findViewById(R.id.im_back_ac);
        lockswitch=findViewById(R.id.lockswitch);

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

        lockswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    LOCKSTATION=true;
                    SharedPreferences.Editor editor=BaseApplication.getContext().getSharedPreferences("data",MODE_PRIVATE).edit();
                    editor.putBoolean("Lockstation",LOCKSTATION);
                    editor.apply();
                } else {
                    LOCKSTATION=false;
                    SharedPreferences.Editor editor=BaseApplication.getContext().getSharedPreferences("data",MODE_PRIVATE).edit();
                    editor.putBoolean("Lockstation",LOCKSTATION);
                    editor.apply();
                }
            }
        });

    }

    protected void onStop() {
        super.onStop();
        if(LOCKSTATION==true && !isAppOnForeground(this)) {
            IS_FOREGROUND=false;
        }
    }

    protected void onRestart() {
        super.onRestart();
        if(LOCKSTATION==true && !IS_FOREGROUND) {
            IS_FOREGROUND = true;
            Intent intent=new Intent(SettingActivity.this,PasswordActivity.class);
            startActivity(intent);

        }
    }
    protected void onResume(){
        super.onResume();
        SharedPreferences sharedPreferences=SettingActivity.this.getSharedPreferences("data",Context.MODE_PRIVATE);
        LOCKSTATION = sharedPreferences.getBoolean("Lockstation",false);
        lockswitch.setChecked(LOCKSTATION);
    }

    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.layout_aboutus:
                Intent intent=new Intent(SettingActivity.this,AboutusActivity.class);
                startActivity(intent);
                break;
            case R.id.layout_help:
                PermissionPageUtil.GoToSetting(SettingActivity.this);
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