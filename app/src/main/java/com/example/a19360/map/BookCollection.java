package com.example.a19360.map;

import android.os.Handler;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by 19360 on 2018/11/30.
 */

public class BookCollection {
    Book book;

    public Book getBook(){return  book;}

    public void download(final Handler handler,final String url_s){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //你的URL
                    URL url = new URL(url_s);
                    HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                    //设置连接属性。不喜欢的话直接默认也阔以
                    conn.setConnectTimeout(5000);//设置超时
                    conn.setUseCaches(false);//数据不多不用缓存了

                    //这里连接了
                    conn.connect();
                    //这里才真正获取到了数据
                    InputStream inputStream = conn.getInputStream();
                    InputStreamReader input = new InputStreamReader(inputStream);
                    BufferedReader buffer = new BufferedReader(input);
                    if(conn.getResponseCode() == 200){//200意味着返回的是"OK"
                        String inputLine;
                        StringBuffer resultData  = new StringBuffer();//StringBuffer字符串拼接很快
                        while((inputLine = buffer.readLine())!= null){
                            resultData.append(inputLine);
                        }
                        String text = resultData.toString();
                        parseJson(text);
                        handler.sendEmptyMessage(1);
                        Log.v("out---------------->",text);
                    }
                } catch(Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }
    private void parseJson(String text) {
        try {
            //这里的text就是上边获取到的数据，一个String.
            JSONObject jsonObject = new JSONObject(text);
            book = new Book();
            book.setName(jsonObject.getString("title"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}