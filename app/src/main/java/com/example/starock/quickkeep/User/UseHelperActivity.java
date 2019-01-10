package com.example.starock.quickkeep.User;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.starock.quickkeep.R;

import java.util.Properties;

import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class UseHelperActivity extends AppCompatActivity {
    Button button;
    EditText txt_email;
    String address;
    EditText suggestion;
    String name;
    EditText passcode;
    TextView howtogetpasscode;
    ImageView back;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_helper);

        howtogetpasscode=findViewById(R.id.howtogetpasscode);
        back=findViewById(R.id.im_back_finish);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UseHelperActivity.this.finish();
            }
        });
        howtogetpasscode.setClickable(true);
        howtogetpasscode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(UseHelperActivity.this,HelperActivity.class);
                startActivity(intent);
            }
        });
        suggestion=findViewById(R.id.suggestion);
        passcode=findViewById(R.id.txt_passcode);
        button=findViewById(R.id.check);
        SharedPreferences sharedPreferences=getBaseContext().getSharedPreferences("data",Context.MODE_PRIVATE);
        address=sharedPreferences.getString("Email",null);
        txt_email=findViewById(R.id.email);
        if(address!=null){
            char[] email=address.toCharArray();
            txt_email.setText(email,0,email.length);
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txt_email.getText().toString().contains("www.")&&txt_email.getText().toString().contains("@")&&txt_email.getText().toString().contains(".com")){
                    name=splitData(txt_email.getText().toString(),"@",".com");
                }
                else
                    Toast.makeText(UseHelperActivity.this,"不符合邮箱规范哦！",Toast.LENGTH_LONG).show();

                if(!(txt_email.getText().toString().equals(address))){
                    Toast.makeText(UseHelperActivity.this,"请用登陆邮箱哇!",Toast.LENGTH_LONG).show();
                }
                else if(!("qq".equals(name))){
                    Toast.makeText(UseHelperActivity.this,"抱歉，目前只能支持qq邮箱提意见哦！",Toast.LENGTH_LONG).show();
                }
                else{
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try{
                                Session session=getMailSession();
                                MimeMessage m=createSimpleMessage(session,txt_email.getText().toString(),suggestion.getText().toString());
                                sendEmail(m,session,txt_email.getText().toString(),passcode.getText().toString());
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    }).start();
                }
            }
        });

    }

    public static Session getMailSession(){
        Properties properties = new Properties(); //服务器主机名
        properties.put("mail.host","smtp.qq.com"); //协议名称
        properties.put("mail.transport.protocol","smtps"); //发送服务器需要身份验证
        properties.put("mail.smtp.auth","true"); //设置ssl加密（QQ邮箱必须设置）
        properties.put("mail.smtp.ssl.enable","true"); //创建session
        Session session = Session.getDefaultInstance(properties);//获取共享session
        // 开启DEBUG模式，监视发邮件状态
        session.setDebug(true);
        return session;
    }

    public static MimeMessage createSimpleMessage(Session session,String address,String suggestion) throws Exception {
        //创建邮件
        MimeMessage message = new MimeMessage(session);
        //发件人

        message.setFrom(new InternetAddress(address, "个人", "UTF-8"));
        //收件人
        message.setRecipient(MimeMessage.RecipientType.TO,new InternetAddress("1320234924@qq.com", "管理员", "UTF-8"));
        //标题
        message.setSubject("QuickKeep用户的反馈意见");
         /*InternetAddress[] addressArr = new InternetAddress[1];
         addressArr[0] = new InternetAddress("xxx@qq.com", "xxx", "UTF-8");
         //邮件回复接收人
         message.setReplyTo(addressArr);*/
        //封装MIME消息
        MimeBodyPart text = new MimeBodyPart();
        text.setContent(suggestion, "text/html; charset=UTF-8");
        //组合MIME消息
        MimeMultipart multipart = new MimeMultipart();
        multipart.addBodyPart(text);
        //设置邮件内容
        message.setContent(multipart);
        message.saveChanges();
        return message;
    }

    public static void sendEmail(MimeMessage msg, Session session,String emailaddress,String passcode) throws Exception {
        //得到transport对象
        Transport transport = session.getTransport();
        //连接邮件服务器(qq邮箱需要使用授权码)password为qq邮箱授权码 //（***此处邮箱必须和发件人一致***）gvbcaraxmjlbbhhb
        transport.connect("smtp.qq.com", emailaddress, passcode); //发送邮件""
        transport.sendMessage(msg, msg.getAllRecipients());
        transport.close();
    }

    public String splitData(String str, String strStart, String strEnd) {
        String tempStr;
        tempStr = str.substring(str.indexOf(strStart) + 1, str.lastIndexOf(strEnd));
        return tempStr;
    }



}

