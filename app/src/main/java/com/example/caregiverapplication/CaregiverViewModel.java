package com.example.caregiverapplication;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;


import com.example.caregiverapplication.model.AlarmData;
import com.example.caregiverapplication.model.CaregiverRepository;
import com.example.caregiverapplication.model.FirstAidData;
import com.example.caregiverapplication.model.HospitalAidData;

import java.util.List;

public class CaregiverViewModel extends AndroidViewModel {

    CaregiverRepository caregiverRepository;
    LiveData<List<FirstAidData>> allFirstAids = new MutableLiveData<>();
    LiveData<List<AlarmData>> allAlramDatas = new MutableLiveData<>();
    LiveData<List<HospitalAidData>> allHospitalDatas = new MutableLiveData<>();

    public CaregiverViewModel(@NonNull Application application){
        super(application);
        caregiverRepository = new CaregiverRepository(application);
        allFirstAids = caregiverRepository.getAllFirstAids();
        allAlramDatas = caregiverRepository.getAllAlarmDatas();
        allHospitalDatas = caregiverRepository.getAllHospitalDatas();
    }

    public void insert(FirstAidData firstAidData){
        caregiverRepository.insert(firstAidData);
    }

    public void update(FirstAidData firstAidData){
        caregiverRepository.update(firstAidData);
    }

    public void delete(FirstAidData firstAidData){
        caregiverRepository.delete(firstAidData);
    }

    public void insertAlarmData(AlarmData alarmData){
        caregiverRepository.insertAlarmData(alarmData);
    }
    public void updateAlarmData(AlarmData alarmData){
        caregiverRepository.updateAlarmData(alarmData);
    }

    public void insertHospitalData(HospitalAidData hospitalAidData){
        caregiverRepository.insertHospitalData(hospitalAidData);
    }

}
