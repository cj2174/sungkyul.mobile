package com.sungkyul.mobile_project.Result;

import androidx.annotation.NonNull;

public class ResultItem {
    //결과에 들어가는 객체를 생성하기위해 만든 클래스
    String name;
    String time;
    String starttime;
    String endtime;
    int resid;

    public ResultItem(String name,String starttime, String endtime, String time, int resid){
        this.name = name;
        this.starttime = starttime;
        this.endtime = endtime;
        this.time = time;
        this.resid = resid;
    }

    //이름 가져오기
    public String getName() {
        return name;
    }
    //이름 설정하기
    public void setName(String name) {
        this.name = name;
    }

    //총 시간 가져오기
    public String getTime() {
        return time;
    }

    //총 시간 설정하기
    public void setTime(String time) {
        this.time = time;
    }

    public int getResid() {
        return resid;
    }

    public void setResid(int resid) {
        this.resid = resid;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public int getSecond(){
        String time = this.time;
        String hour = time.split(":")[0];
        String minute = time.split(":")[1];
        String second = time.split(":")[2];

        int total = Integer.parseInt(hour)*3600 +
                    Integer.parseInt(minute)*60 +
                    Integer.parseInt(second);


        return total;
    }

    @NonNull
    @Override
    public String toString() {
        return "ResultItem{" + "name = " + name + "time = " + time + "resid = " + resid;
    }
}