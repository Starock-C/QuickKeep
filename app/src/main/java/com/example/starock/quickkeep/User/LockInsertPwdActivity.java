package com.example.starock.quickkeep.User;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.starock.quickkeep.BaseApplication;
import com.example.starock.quickkeep.R;

import static com.example.starock.quickkeep.BaseApplication.LOCKSTATION;

public class LockInsertPwdActivity extends AppCompatActivity {
    EditText pwd;
    Button check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_insertpwd);

        pwd=findViewById(R.id.pwd1);
        check=findViewById(R.id.check);
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor=BaseApplication.getContext().getSharedPreferences("loginInfo",MODE_PRIVATE).edit();
                editor.putString("lockpwd",pwd.getText().toString());
                editor.apply();
                if(pwd.getText().toString().length()==4)
                    LockInsertPwdActivity.this.finish();
                else
                    Toast.makeText(LockInsertPwdActivity.this,"密码不能少于四位哦！",Toast.LENGTH_LONG).show();
            }
        });


    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK) { //监控/拦截/屏蔽返回键
            return true;
        } else if(keyCode == KeyEvent.KEYCODE_MENU) {//MENU键
            //监控/拦截菜单键
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
