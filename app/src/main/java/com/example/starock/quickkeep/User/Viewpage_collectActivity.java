package com.example.starock.quickkeep.User;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.VideoView;
import android.net.Uri;
import com.example.starock.quickkeep.R;
import java.util.ArrayList;
import java.util.List;

public class Viewpage_collectActivity extends Activity {
  /*  private View view1,view2;
    private List<View> viewList;
    private ViewPager viewPager;
   *//* private SharedPreferences mPreferences;*/
  private VideoView video;
    private ImageView img;
    int i=0;
    private List list=new ArrayList();
    Handler handler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch ((msg.what)){
                case 1:
                    video.setVisibility(View.VISIBLE);
                    img.setVisibility(View.GONE);
                    video.setVideoURI(Uri.parse(list.get(i).toString()));
                    video.requestFocus();
                    video.start();
                    break;
                case 2:
                    video.setVisibility(View.GONE);
                    img.setVisibility(View.VISIBLE);
                    ImageLoader.getInstance().displayImage(list.get(i).toString(),img);
                    i++;
                    waitTtime();
                    break;
            }
            return false;
        }
    });
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_howtocollect);
        initView();
        setDate();
        startPhoto();
        video.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                i++;
                startPhoto();
                return true;
            }
        });
        video.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                i++;
                startPhoto();
            }
        });
        FloatingActionButton fab=(FloatingActionButton)findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void setDate(){
        list.add(Uri.parse("android.resource://"+getPackageName()+"/raw/yanshi"));
        list.add("drawable://"+R.drawable.ic_yanshi1);
        list.add(("drawable://"+R.drawable.ic_yanshi2));
        list.add("drawable://"+R.drawable.ic_yanshi3);
    }
    private void initView(){
        video=findViewById(R.id.video);
        img=findViewById(R.id.img);
    }
    private void startPhoto(){
        if(i<list.size()){
            if (list.get(i).toString().contains("resource")){
                handler.sendEmptyMessage(1);
            }else {
                handler.sendEmptyMessage(2);
            }
        }else {
            i=0;
            if (list.get(i).toString().contains("resource")){
                handler.sendEmptyMessage(1);
            }else {
                handler.sendEmptyMessage(2);
            }
        }

    }
    private void waitTtime(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                startPhoto();
            }
        }).start();
    }
    /*protected void onCreate(Bundle savedInstanceState){
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
                finish();
            }
        }.start();
        FloatingActionButton fab=(FloatingActionButton)findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
*/
}
