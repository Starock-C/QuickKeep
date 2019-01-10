package com.example.starock.quickkeep;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.example.starock.quickkeep.User.LockPasswordActivity;

import org.litepal.LitePal;

public class BaseApplication extends Application {
    private static Context mContext;
    private static ScreenActionReceiver mScreenActionReceiver;
    public void onCreate(){
        super.onCreate();
        mContext=getApplicationContext();
        LitePal.initialize(this);
        if(mScreenActionReceiver == null) {
            mScreenActionReceiver = new ScreenActionReceiver();
            IntentFilter screenfilter = new IntentFilter();
            screenfilter.addAction(Intent.ACTION_SCREEN_OFF);
            screenfilter.addAction(Intent.ACTION_SCREEN_ON);
            registerReceiver(mScreenActionReceiver, screenfilter);
        }

    }
    public static Context getContext(){
        return mContext;
    }


    public static boolean IS_FOREGROUND = false;
    public static boolean LOCKSTATION = false;

    public class ScreenActionReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (LOCKSTATION==true && action.equals(Intent.ACTION_SCREEN_ON)) {
                //用户点亮屏幕
                //判断是否需要弹出密码锁来
                if (IS_FOREGROUND) {
                    //如果应用程序恰好处在前台，则弹出密码锁界面来
                    Intent intent1=new Intent(BaseApplication.getContext(),LockPasswordActivity.class);
                    startActivity(intent1);
                }
            } else if (LOCKSTATION==true && action.equals(Intent.ACTION_SCREEN_OFF)) {
                //用户关闭屏幕
            }
        }
    }



}
