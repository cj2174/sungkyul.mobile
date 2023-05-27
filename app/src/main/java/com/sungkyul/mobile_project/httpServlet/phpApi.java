package com.sungkyul.mobile_project.httpServlet;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static java.lang.Thread.sleep;

public class phpApi {
    // https://androidexample.com/How_To_Make_HTTP_POST_Request_To_Server_-_Android_Example/index.php?view=article_discription&aid=64&aaid=89 --> EXAMPLE CODE
    static String urlData = "http://34.64.177.188:8080/design_pattern.php";
    static URL url;
    static {
        try {
            url = new URL(urlData);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
    static JSONObject returnJsonData;

    public static JSONObject POSTsend(final String data){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Log.i("Start HTTP POST METHOD", "OK");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setDoOutput(true);
                    conn.connect();
                    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                    wr.write(data);
                    wr.flush();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    returnJsonData = new JSONObject(reader.readLine().toString());
                    // 연결 끊기
                    wr.close();
                    conn.disconnect();
                    Log.i("Start HTTP POST METHOD", "END");
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException err) {
                    err.printStackTrace();
                }
            }
        });
        // 스레드 start
        thread.start();
        try {
            // 스레드 wating
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return returnJsonData;
    }
}
