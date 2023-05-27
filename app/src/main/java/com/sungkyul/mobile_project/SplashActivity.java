package com.sungkyul.mobile_project;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

public class SplashActivity extends Activity {
// 처음 등장하는 이미지 시간 지정 및 엑티비티 넘기는 클래스
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            Thread.sleep(3000);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

/*        startActivity(new Intent(this, LoginActivity.class));*/
        startActivity(new Intent(this, MainActivity.class));
        finish();

    }
}
