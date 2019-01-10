package com.example.starock.quickkeep.User;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.starock.quickkeep.AnalysisUtils;
import com.example.starock.quickkeep.LoginActivity;
import com.example.starock.quickkeep.R;
import com.tencent.cos.xml.CosXmlService;
import com.tencent.cos.xml.CosXmlServiceConfig;
import com.tencent.cos.xml.exception.CosXmlClientException;
import com.tencent.cos.xml.exception.CosXmlServiceException;
import com.tencent.cos.xml.listener.CosXmlProgressListener;
import com.tencent.cos.xml.listener.CosXmlResultListener;
import com.tencent.cos.xml.model.CosXmlRequest;
import com.tencent.cos.xml.model.CosXmlResult;
import com.tencent.cos.xml.transfer.COSXMLUploadTask;
import com.tencent.cos.xml.transfer.TransferConfig;
import com.tencent.cos.xml.transfer.TransferManager;
import com.tencent.cos.xml.transfer.TransferState;
import com.tencent.cos.xml.transfer.TransferStateListener;
import com.tencent.qcloud.core.auth.QCloudCredentialProvider;
import com.tencent.qcloud.core.auth.ShortTimeCredentialProvider;

public class UserMainActivity extends AppCompatActivity implements View.OnClickListener {
    RelativeLayout howtosearch;
    RelativeLayout center;
    RelativeLayout setting;
    RelativeLayout suggestion;
    RelativeLayout upload;
    ImageView close;
    TextView email,username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main);

        howtosearch=findViewById(R.id.layout_way);
        center=findViewById(R.id.layout_center);
        setting=findViewById(R.id.layout_setting);
        suggestion=findViewById(R.id.layout_sugg);
        upload=findViewById(R.id.layout_upload);
        close=findViewById(R.id.im_close_ac);
        email=(TextView)findViewById(R.id.email);
        username=(TextView)findViewById(R.id.username);

        if(AnalysisUtils.readLoginStatus(this)){
            email.setText(AnalysisUtils.readLoginUserName(this));
            username.setText(AnalysisUtils.readPersonalName(this));
        }

        howtosearch.setOnClickListener(this);
        center.setOnClickListener(this);
        setting.setOnClickListener(this);
        suggestion.setOnClickListener(this);
        upload.setOnClickListener(this);
        close.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.layout_way:
                //如何收集
                Intent intent3=new Intent(UserMainActivity.this,Viewpage_collectActivity.class);
                startActivity(intent3);
                break;
            case R.id.layout_center:
                //个人中心
                if(AnalysisUtils.readLoginStatus(this)){
                    Intent intent2=new Intent(UserMainActivity.this,PersonalActivity.class);
                    startActivity(intent2);
                }
                else {
                    Intent intent4=new Intent(UserMainActivity.this,LoginActivity.class);
                    startActivity(intent4);
                }
                break;
            case R.id.layout_setting:
                Intent intent=new Intent(UserMainActivity.this,SettingActivity.class);
                startActivity(intent);
                break;
            case R.id.layout_sugg:
                Intent intent1=new Intent(UserMainActivity.this,UseHelperActivity.class);
                startActivity(intent1);
                break;
            case R.id.layout_upload:
                upLoadData loadData=new upLoadData();
                new Thread(loadData).start();
                Toast.makeText(UserMainActivity.this,"备份成功",Toast.LENGTH_LONG).show();
                break;
            case R.id.im_close_ac:
                UserMainActivity.this.finish();
                break;
        }
    }
    class upLoadData extends Thread{
        @Override
        public void run(){
            String appid="1258397749";
            String region="ap-chengdu";
            String secretId = "AKIDOcb6MQApW1HI7jqOPBoDmAJF0WnpKCZi";
            String secretKey ="IJ5dw42dz85nIYbP1QdfRzIOFuKXXLum";

            CosXmlServiceConfig serviceConfig = new CosXmlServiceConfig.Builder()
                    .setAppidAndRegion(appid, region)
                    .setDebuggable(true)
                    .builder();


            QCloudCredentialProvider credentialProvider = new ShortTimeCredentialProvider(secretId,
                    secretKey, 300);

            CosXmlService cosXmlService = new CosXmlService(UserMainActivity.this, serviceConfig, credentialProvider);


            // 初始化 TransferConfig
            TransferConfig transferConfig = new TransferConfig.Builder().build();

            //初始化 TransferManager
            TransferManager transferManager = new TransferManager(cosXmlService, transferConfig);

            SharedPreferences sharedPreferences=getBaseContext().getSharedPreferences("data",Context.MODE_PRIVATE);
            String name=sharedPreferences.getString("username",null);

            String bucket = "yanl-1258397749";
            String cosPath = name+"InfoFromQK.xml";//[对象键](https://cloud.tencent.com/document/product/436/13324)，即存储到 COS 上的绝对路径; //格式如 cosPath = "test.txt";
            String srcPath = "data/data/com.example.starock.quickkeep/shared_prefs/data.xml";//"本地文件的绝对路径"; // 如 srcPath=Environment.getExternalStorageDirectory().getPath() + "/test.txt";
            String uploadId = "1"; //若存在初始化分片上传的 UploadId，则赋值对应uploadId值用于续传，否则，赋值null。
            //上传文件
            COSXMLUploadTask cosxmlUploadTask = transferManager.upload(bucket, cosPath, srcPath, uploadId);

            cosPath = name+"QuickKeep.db";
            srcPath = "data/data/com.example.starock.quickkeep/database/QuickKeep.db";
            uploadId = "2";
            cosxmlUploadTask = transferManager.upload(bucket, cosPath, srcPath, uploadId);


            //设置上传进度回调

            cosxmlUploadTask.setCosXmlProgressListener(new CosXmlProgressListener() {
                @Override
                public void onProgress(long complete, long target) {
                    float progress = 1.0f * complete / target * 100;
                    Log.d("TEST",  String.format("progress = %d%%", (int)progress));
                }
            });
            //设置返回结果回调
            cosxmlUploadTask.setCosXmlResultListener(new CosXmlResultListener() {
                @Override
                public void onSuccess(CosXmlRequest request, CosXmlResult result) {
                    Log.d("TEST",  "Success: " + result.printResult());
                }

                @Override
                public void onFail(CosXmlRequest request, CosXmlClientException exception, CosXmlServiceException serviceException) {
                    Log.d("TEST",  "Failed: " + (exception == null ? serviceException.getMessage() : exception.toString()));
                }
            });

            //设置任务状态回调, 可以查看任务过程
            cosxmlUploadTask.setTransferStateListener(new TransferStateListener() {
                @Override
                public void onStateChanged(TransferState state) {
                    Log.d("TEST", "Task state:" + state.name());
                }
            });
        }
    }
}
