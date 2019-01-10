package com.example.starock.quickkeep;

import android.content.Context;
import android.content.SharedPreferences;

public class AnalysisUtils {
    //读取用户名
    public static String readLoginUserName(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("loginInfo",Context.MODE_PRIVATE);
        String userName=sharedPreferences.getString("Email","");
        return userName;
    }
    public static String readPersonalName(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("loginInfo",Context.MODE_PRIVATE);
        String personalName=sharedPreferences.getString("username","");
        return personalName;
    }
    public static String readMessagee(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("loginInfo",Context.MODE_PRIVATE);
        String message=sharedPreferences.getString("message","");
        return message;
    }
    //读取登录状态
    public static boolean readLoginStatus(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("loginInfo",Context.MODE_PRIVATE);
        boolean isLogin=sharedPreferences.getBoolean("isLogin",false);
        return isLogin;
    }

    //清除登录状态
    public static void cleanLoginStatus(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("loginInfo",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isLogin",false);
        editor.putString("Email","");
        editor.commit();
    }


}
