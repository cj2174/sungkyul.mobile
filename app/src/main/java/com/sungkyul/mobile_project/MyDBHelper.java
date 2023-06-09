package com.sungkyul.mobile_project;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MyDBHelper extends SQLiteOpenHelper {

    Integer[] posterID;
    String[] posterText;
    String text ="수면 이동 식사 운동 일 쇼핑 여가활동 집안일 영화 및 드라마 감상 산책 공부 웹서핑";

    public MyDBHelper(Context context){
        super(context, "Time", null,3 );
    }

    @Override
    public void onCreate(SQLiteDatabase db) {


        Integer[] posterID = { R.drawable.icon01, R.drawable.icon02, R.drawable.icon03, R.drawable.icon04, R.drawable.icon05, R.drawable.icon06,
                R.drawable.icon07, R.drawable.icon08, R.drawable.icon09, R.drawable.icon10, R.drawable.icon11, R.drawable.icon12  };

        String[] posterText = text.split(" ");

        // user_plan 생성
        db.execSQL("CREATE TABLE user_plan ( " +
                "pk INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                "planname TEXT NOT NULL, " +
                "activityname TEXT NOT NULL, " +
                "img_src INTEGER, " +
                "currenttime TEXT, " +
                "timegoal TEXT );");

        // user_activity 생성
        db.execSQL("CREATE TABLE user_activity ( " +
                "pk INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                "activityname TEXT NOT NULL, " +
                "img_src INTEGER );");

        // time_db 생성
        db.execSQL("CREATE TABLE time_db ( " +
                "pk INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                "activityname TEXT NOT NULL, " +
                "timestart TEXT, " +
                "timeend TEXT, " +
                "timedata TEXT );");


        for(int i=0; i<posterID.length; i++){
            db.execSQL("INSERT INTO user_activity(activityname, img_src) VALUES ('" + posterText[i] + "', '" + posterID[i] + "');");
        }

    }

    public void plan_insert(SQLiteDatabase db, String planname , String activityName, Integer img_src, String timegoal, String currentTime){
        Log.i(this.getClass().getName(),planname + " - " + activityName + " - " + img_src + " timegoal- " + timegoal + " - " + currentTime);
        db.execSQL("INSERT INTO user_plan(planname ,activityname, img_src, currenttime , timegoal) " +
                "VALUES ('" + planname + "' , '" + activityName + "', '" + img_src + "' , '" + currentTime + "' , '" +  timegoal + "');");
    }


    public void plan_delete(SQLiteDatabase db, String planname){
        db.execSQL("DELETE FROM user_plan WHERE planname = '" +  planname + "';");
    }


    public void activity_insert(SQLiteDatabase db, String activityName, Integer img_src){
        Log.i(this.getClass().getName(),activityName + img_src);
        db.execSQL("INSERT INTO user_activity(activityname, img_src) VALUES ('" + activityName + "', '" + img_src + "');");
    }

    public void activity_delete(SQLiteDatabase db, String activityName){
        db.execSQL("DELETE FROM user_activity WHERE activityname = '" +  activityName + "';");
        db.execSQL("DELETE FROM time_db WHERE activityname = '" +  activityName + "';");
    }

    public void insert(SQLiteDatabase db, String activityName, String timeStart, String timeEnd, String timeGap){
        Log.i(this.getClass().getName(),activityName + timeEnd +timeStart + " => " + timeGap);
        db.execSQL("INSERT INTO time_db(activityname, timestart, timeend, timedata) VALUES ('" + activityName + "', '" + timeStart
                + "', '" + timeEnd + "', '" + timeGap + "');");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // 버전이 업그레이드되면 기존에 있던걸 삭제하고, db를 새로 생성
        db.execSQL("DROP TABLE IF EXISTS time_db");
        db.execSQL("DROP TABLE IF EXISTS user_activity");
        db.execSQL("DROP TABLE IF EXISTS user_plan");
        onCreate(db);
    }
}
