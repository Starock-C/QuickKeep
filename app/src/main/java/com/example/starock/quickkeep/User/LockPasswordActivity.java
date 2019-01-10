package com.example.starock.quickkeep.User;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.starock.quickkeep.R;

import static com.example.starock.quickkeep.BaseApplication.LOCKSTATION;

public class LockPasswordActivity extends AppCompatActivity {
    private Button button;
    EditText password;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_check_pwd);

        button=findViewById(R.id.check);
        password=findViewById(R.id.pwd1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences=LockPasswordActivity.this.getSharedPreferences("loginInfo",Context.MODE_PRIVATE);
                String pwd = sharedPreferences.getString("lockpwd",null);
                if(password.getText().toString().equals(pwd)){
                    LockPasswordActivity.this.finish();
                }
                else
                    Toast.makeText(LockPasswordActivity.this,"密码错误哦！请重新输入！",Toast.LENGTH_LONG).show();
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
