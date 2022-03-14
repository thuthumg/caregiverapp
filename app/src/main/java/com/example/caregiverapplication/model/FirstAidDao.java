package com.example.caregiverapplication.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface FirstAidDao {


    @Insert
    void insert(FirstAidData firstAidData);

    @Update
    void update(FirstAidData firstAidData);

    @Delete
    void delete(FirstAidData firstAidData);

    @Query("DELETE FROM tbl_first_aid")
    void deleteAll();

    @Query("SELECT * FROM TBL_FIRST_AID ORDER BY name ASC")//DESC
     LiveData<List<FirstAidData>> getAllFirstAidData();
}
