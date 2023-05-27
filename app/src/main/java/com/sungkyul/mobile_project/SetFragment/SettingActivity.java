package com.sungkyul.mobile_project.SetFragment;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.sungkyul.mobile_project.R;

public class SettingActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);

        setTitle("설정");
    }
}
