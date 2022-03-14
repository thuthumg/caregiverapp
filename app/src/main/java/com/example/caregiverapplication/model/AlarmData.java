package com.example.caregiverapplication.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tbl_alarm")
public class AlarmData {
    @PrimaryKey(autoGenerate =  true)
    private int alid;

    private String alarm_time;

    private int flag;

    public AlarmData( String alarm_time, int flag) {

        this.alarm_time = alarm_time;
        this.flag = flag;
    }

    public int getAlid() {
        return alid;
    }

    public void setAlid(int alid) {
        this.alid = alid;
    }

    public String getAlarm_time() {
        return alarm_time;
    }

    public void setAlarm_time(String alarm_time) {
        this.alarm_time = alarm_time;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }
}
