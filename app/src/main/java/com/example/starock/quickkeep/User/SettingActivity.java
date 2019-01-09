package com.example.starock.quickkeep.User;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.starock.quickkeep.R;

public class SettingActivity extends AppCompatActivity {
    RelativeLayout aboutuslayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_setting);

        aboutuslayout=findViewById(R.id.layout_aboutus);
        aboutuslayout.setClickable(true);
        aboutuslayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SettingActivity.this,AboutusActivity.class);
                startActivity(intent);
            }
        });
    }
}