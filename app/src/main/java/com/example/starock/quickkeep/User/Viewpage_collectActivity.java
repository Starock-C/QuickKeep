package com.example.starock.quickkeep.User;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.example.starock.quickkeep.R;
import java.util.ArrayList;
import java.util.List;

public class Viewpage_collectActivity extends Activity {
    private View view1,view2;
    private List<View> viewList;
    private ViewPager viewPager;
   /* private SharedPreferences mPreferences;*/
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_howtocollect);
        viewPager=(ViewPager)findViewById(R.id.viewpage);
        LayoutInflater inflater=getLayoutInflater();
        view1=inflater.inflate(R.layout.activity_viewpage1,null);
        view2=inflater.inflate(R.layout.activity_viewpage2,null);
        viewList=new ArrayList<View>();
        viewList.add(view1);
        viewList.add(view2);
        PagerAdapter pagerAdapter=new PagerAdapter() {
            @Override
            public int getCount() {
                return viewList.size();
            }

            @Override
            public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
                return o==view;
            }

            @Override
            public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
                container.removeView(viewList.get(position));
            }

            public Object instantiateItem(ViewGroup container,int position){
                container.addView(viewList.get(position));
                return viewList.get(position);
            }
        };

        viewPager.setAdapter(pagerAdapter);
        new CountDownTimer(6000,1000){
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                Intent intent=new Intent();
                intent.setClass(Viewpage_collectActivity.this,UserMainActivity.class);
                startActivity(intent);
                finish();
            }
        }.start();
        FloatingActionButton fab=(FloatingActionButton)findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(Viewpage_collectActivity.this,UserMainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }


}
