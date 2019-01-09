package com.example.starock.quickkeep.User;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.example.starock.quickkeep.R;

public class HelperActivity extends AppCompatActivity {
    ImageView imageView1;
    ImageView imageView2;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_helper);

        imageView1=findViewById(R.id.im1);
        imageView2=findViewById(R.id.im2);

    }
}
