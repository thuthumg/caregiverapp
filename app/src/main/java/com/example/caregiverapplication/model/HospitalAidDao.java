package com.example.caregiverapplication.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface HospitalAidDao {


    @Insert
    void insert(HospitalAidData hospitalAidData);

    @Update
    void update(HospitalAidData hospitalAidData);

    @Delete
    void delete(HospitalAidData hospitalAidData);

    @Query("DELETE FROM tbl_hospital_aid")
    void deleteAll();

    @Query("SELECT * FROM tbl_hospital_aid LIMIT 3")//ORDER BY hospital_name DESC
     LiveData<List<HospitalAidData>> getAllHospitalAid();
}
