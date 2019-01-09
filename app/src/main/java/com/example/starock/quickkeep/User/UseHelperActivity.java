package com.example.starock.quickkeep.User;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_helper);

        button=findViewById(R.id.check);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            Session session=getMailSession();
                            MimeMessage m=createSimpleMessage(session);
                            sendEmail(m,session);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }).start();

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

    public static MimeMessage createSimpleMessage(Session session) throws Exception {
        //创建邮件
        MimeMessage message = new MimeMessage(session);
        //发件人
        message.setFrom(new InternetAddress("284545631@qq.com", "管理员", "UTF-8"));
        //收件人
        message.setRecipient(MimeMessage.RecipientType.TO,new InternetAddress("1320234924@qq.com", "USER_BB", "UTF-8"));
        //标题
        message.setSubject("简单邮件");
         /*InternetAddress[] addressArr = new InternetAddress[1];
         addressArr[0] = new InternetAddress("xxx@qq.com", "xxx", "UTF-8");
         //邮件回复接收人
         message.setReplyTo(addressArr);*/
        //封装MIME消息
        MimeBodyPart text = new MimeBodyPart();
        text.setContent("你好啊！", "text/html; charset=UTF-8");
        //组合MIME消息
        MimeMultipart multipart = new MimeMultipart();
        multipart.addBodyPart(text);
        //设置邮件内容
        message.setContent(multipart);
        message.saveChanges();
        return message;
    }

    public static void sendEmail(MimeMessage msg, Session session) throws Exception {
        //得到transport对象
        Transport transport = session.getTransport();
        //连接邮件服务器(qq邮箱需要使用授权码)password为qq邮箱授权码 //（***此处邮箱必须和发件人一致***）
        transport.connect("smtp.qq.com", "284545631@qq.com", "gvbcaraxmjlbbhhb"); //发送邮件
        transport.sendMessage(msg, msg.getAllRecipients());
        transport.close();
    }


}

