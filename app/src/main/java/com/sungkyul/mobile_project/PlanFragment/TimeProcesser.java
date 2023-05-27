package com.sungkyul.mobile_project.PlanFragment;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sungkyul.mobile_project.MyDBHelper;


public class TimeProcesser {

    // // 생성 이후 시간 데이터만 더함
    // 년/ 월/ 일  시:분:초
    int[] Date = new int[6];

    public static MyDBHelper myDBHelper;
    public static SQLiteDatabase db;

    public String Timeprcess(Context context, String activityName, String date){

        int minuteTime = 0;

        myDBHelper = new MyDBHelper(context);
        db = myDBHelper.getReadableDatabase();
        Cursor cursor;
        cursor = db.rawQuery("SELECT timestart,timedata FROM time_db WHERE activityname = + '" + activityName + "' ;", null);

        while(cursor.moveToNext()){

            String timestart = (cursor.getString(0));

            // 생성 이후 시간 데이터만 더함
            int compare = date.compareTo( timestart );
            if(compare < 0) {
                String timeStr[] = (cursor.getString(1)).split(":");
                minuteTime += (Integer.parseInt(timeStr[0]) * 60) + Integer.parseInt(timeStr[1]);
            }
        }
        return minuteTime+"";
        // minute 형태로 Return 해야함
    }
}