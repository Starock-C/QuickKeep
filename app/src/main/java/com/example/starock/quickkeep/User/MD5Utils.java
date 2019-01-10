package com.example.starock.quickkeep.User;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Utils {
    public static String md5(String text){
        MessageDigest digest = null;

        try {
            //获取数据指纹对象
            digest = MessageDigest.getInstance("md5");
            //字节数组
            byte[] result = digest.digest(text.getBytes());
            //16进制转换
            StringBuffer sb = new StringBuffer();
            //获取所有字节进行转换
            for (byte b: result){
                //使用『与算法』，java使用unicode字符，所以每个字符占位两个
                // 需要与两位16进制最大值进行与运算，获取number值
                int number = b & 0xff;
                //number值转换字符串
                String hex = Integer.toHexString(number);
                if (hex.length() == 1){
                    //若转换后的字符长度等于1则进行字符串拼接
                    sb.append("0"+hex);
                }else {
                    sb.append(hex);
                }
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            //发送异常return空字符串
            return "";
        }
    }
}
