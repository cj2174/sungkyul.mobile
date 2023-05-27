package com.sungkyul.mobile_project.Result;

public class ResultActivity {
    //항목별로 시간을 넣기 위한 객체
    String name = "";
    int time = 0;

    public ResultActivity(String name, int time) {
        this.name = name;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public void PlusTime(int time){
        this.time += time;
    }


}
