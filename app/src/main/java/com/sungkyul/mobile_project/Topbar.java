package com.sungkyul.mobile_project;

import android.app.Activity;
import android.os.Build;

import androidx.core.content.ContextCompat;

public class Topbar {
// 상단바 색깔을 지정
    public enum StatusBarColorType {

        ACCENT_STATUS_BAR( R.color.colorAccent ),
        YELLOW_STATUS_BAR( R.color.colorYellow );


        private int backgroundColorId;

        StatusBarColorType(int backgroundColorId){
            this.backgroundColorId = backgroundColorId;
        }

        public int getBackgroundColorId() {
            return backgroundColorId;
        }
    }

    public static void setStatusBarColor(Activity activity, StatusBarColorType colorType) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().setStatusBarColor(ContextCompat.getColor(activity, colorType.getBackgroundColorId()));
        }

   }
}