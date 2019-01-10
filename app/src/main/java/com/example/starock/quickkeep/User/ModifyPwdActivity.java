package com.example.starock.quickkeep.User;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.starock.quickkeep.AnalysisUtils;
import com.example.starock.quickkeep.LoginActivity;
import com.example.starock.quickkeep.R;

public class ModifyPwdActivity extends AppCompatActivity {
    private EditText et_original_psw;
    private EditText et_new_psw;
    private EditText et_new_psw_again;
    private ImageView img_save;
    private FloatingActionButton btn_back;
    private String userName;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifypwd);
       /* initView();*/
        et_original_psw=(EditText)findViewById(R.id.old_password);
        et_new_psw=(EditText)findViewById(R.id.new_password);
        et_new_psw_again=(EditText)findViewById(R.id.new_password1);
        img_save=(ImageView)findViewById(R.id.img_check);
        btn_back=(FloatingActionButton)findViewById(R.id.fab_modify);
        userName = AnalysisUtils.readLoginUserName(this);
        img_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit();
            }
        });
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void submit() {
        String psw=et_original_psw.getText().toString().trim();
        String newPsw=et_new_psw.getText().toString().trim();
        String again=et_new_psw_again.getText().toString().trim();
         if (!MD5Utils.md5(psw).equals(readPsw())){
            Log.i("MD5Utils.md5(psw)",""+MD5Utils.md5(psw));
            Log.i("readPsw",""+readPsw());
            Toast.makeText(this,"输入的密码与原始密码不一致",Toast.LENGTH_SHORT).show();
            return;
        }else if (MD5Utils.md5(newPsw).equals(readPsw())){
            Toast.makeText(this,"输入的新密码与原始密码不能一致",Toast.LENGTH_SHORT).show();
            return;
        }else if (!newPsw.equals(again)){
            Toast.makeText(this,"再次输入的新密码不一致",Toast.LENGTH_SHORT).show();
            return;
        }else {
            Toast.makeText(this,"新密码设置成功", Toast.LENGTH_SHORT).show();
            modifyPsw(newPsw);//
            Intent intent=new Intent(ModifyPwdActivity.this,LoginActivity.class);
            startActivity(intent);
            //关闭设置页面

            //关闭修改密码页面
            ModifyPwdActivity.this.finish();
        }
    }

    private void modifyPsw(String newPsw) {
        String md5psw= MD5Utils.md5(newPsw);
        SharedPreferences sharedPreferences=getSharedPreferences("loginInfo",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(userName,md5psw);
        editor.commit();
    }

    private String readPsw() {
        SharedPreferences sharedPreferences=getSharedPreferences("loginInfo",MODE_PRIVATE);
        String spPsw=sharedPreferences.getString(userName,"");
        Log.i("Email",userName);
        Log.i("passwork",spPsw);
        return spPsw;
    }

}