package com.example.caregiverapplication.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
interface AlarmDao {


    @Insert
    void insert(AlarmData alarmData);

    @Update
    void update(AlarmData alarmData);


    @Delete
    void delete(AlarmData alarmData);

    @Query("DELETE FROM tbl_alarm")
    void deleteAll();

    @Query("SELECT * FROM TBL_ALARM ORDER BY alid DESC")
      LiveData<List<AlarmData>> getAllAlarmData();

}
