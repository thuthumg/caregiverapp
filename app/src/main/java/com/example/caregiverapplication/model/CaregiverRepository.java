package com.example.caregiverapplication.model;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class CaregiverRepository {

    private FirstAidDao firstAidDao;
    private AlarmDao alarmDao;
    private HospitalAidDao hospitalAidDao;
    private LiveData<List<FirstAidData>> allFirstAids;
    private LiveData<List<AlarmData>> allAlarmDatas;
    private LiveData<List<HospitalAidData>> allHospitalDatas;


    public CaregiverRepository(Application application){
        firstAidDao = CaregiverDataRoomDatabase.getInstance(application).firstAidDao();
        alarmDao = CaregiverDataRoomDatabase.getInstance(application).alarmDao();
        hospitalAidDao =  CaregiverDataRoomDatabase.getInstance(application).hospitalAidDao();
        allFirstAids = firstAidDao.getAllFirstAidData();
        allAlarmDatas = alarmDao.getAllAlarmData();
        allHospitalDatas = hospitalAidDao.getAllHospitalAid();
    }

     public void insert(FirstAidData firstAidData){
        new InsertAsyncTask(firstAidDao).execute(firstAidData);
     }
     private static class InsertAsyncTask extends AsyncTask<FirstAidData,Void,Void>{
        private FirstAidDao firstAidDao;

        InsertAsyncTask(FirstAidDao firstAidDao){
            this.firstAidDao = firstAidDao;
        }

         @Override
         protected Void doInBackground(FirstAidData... firstAidData) {
             firstAidDao.insert(firstAidData[0]);
             return null;
         }
     }



    public void update(FirstAidData firstAidData){
        new UpdateAsyncTask(firstAidDao).execute(firstAidData);
    }
    private static class UpdateAsyncTask extends AsyncTask<FirstAidData,Void,Void>{
        private FirstAidDao firstAidDao;

        UpdateAsyncTask(FirstAidDao firstAidDao){
            this.firstAidDao = firstAidDao;
        }

        @Override
        protected Void doInBackground(FirstAidData... firstAidData) {
            firstAidDao.update(firstAidData[0]);
            return null;
        }
    }


    public void delete(FirstAidData firstAidData){
        new DeleteAsyncTask(firstAidDao).execute(firstAidData);
    }
    private static class DeleteAsyncTask extends AsyncTask<FirstAidData,Void,Void>{
        private FirstAidDao firstAidDao;

        DeleteAsyncTask(FirstAidDao firstAidDao){
            this.firstAidDao = firstAidDao;
        }

        @Override
        protected Void doInBackground(FirstAidData... firstAidData) {
            firstAidDao.delete(firstAidData[0]);
            return null;
        }
    }

    public LiveData<List<FirstAidData>> getAllFirstAids(){
        return allFirstAids;
    }




    public void insertAlarmData(AlarmData alarmData){
        new InsertAlarmDataAsyncTask(alarmDao).execute(alarmData);
    }
    private static class InsertAlarmDataAsyncTask extends AsyncTask<AlarmData,Void,Void>{
        private AlarmDao alarmDao;

        InsertAlarmDataAsyncTask(AlarmDao alarmDao){
            this.alarmDao = alarmDao;
        }

        @Override
        protected Void doInBackground(AlarmData... alarmData) {
            alarmDao.insert(alarmData[0]);
            return null;
        }
    }



    public void updateAlarmData(AlarmData alarmData){
        new UpdateAlarmDataAsyncTask(alarmDao).execute(alarmData);
    }
    private static class UpdateAlarmDataAsyncTask extends AsyncTask<AlarmData,Void,Void>{
        private AlarmDao alarmDao;

        UpdateAlarmDataAsyncTask(AlarmDao alarmDao){
            this.alarmDao = alarmDao;
        }

        @Override
        protected Void doInBackground(AlarmData... alarmData) {
            alarmDao.update(alarmData[0]);
            return null;
        }
    }





    public LiveData<List<AlarmData>> getAllAlarmDatas(){
        return allAlarmDatas;
    }

    public void insertHospitalData(HospitalAidData hospitalAidData){
        new InsertHospitalDataAsyncTask(hospitalAidDao).execute(hospitalAidData);
    }
    private static class InsertHospitalDataAsyncTask extends AsyncTask<HospitalAidData,Void,Void>{
        private HospitalAidDao hospitalAidDao;

        InsertHospitalDataAsyncTask(HospitalAidDao hospitalAidDao){
            this.hospitalAidDao = hospitalAidDao;
        }

        @Override
        protected Void doInBackground(HospitalAidData... hospitalAidData) {
            hospitalAidDao.insert(hospitalAidData[0]);
            return null;
        }
    }


    public LiveData<List<HospitalAidData>> getAllHospitalDatas(){
        return allHospitalDatas;
    }




}
