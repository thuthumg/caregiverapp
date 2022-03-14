package com.example.caregiverapplication.model;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;


@Database(entities = {AlarmData.class,FirstAidData.class,HospitalAidData.class},version = 1)
abstract class CaregiverDataRoomDatabase extends RoomDatabase {

  private static CaregiverDataRoomDatabase instance;

  abstract AlarmDao alarmDao();
  abstract FirstAidDao firstAidDao();
  abstract HospitalAidDao hospitalAidDao();

    static synchronized CaregiverDataRoomDatabase getInstance(Context context){
        if(instance == null)
        {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    CaregiverDataRoomDatabase.class,"db_caregiver")
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    static Callback roomCallback = new Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateData(instance).execute();
        }
    };

    private static class PopulateData extends AsyncTask<Void,Void,Void> {

        CaregiverDataRoomDatabase database;
        PopulateData(CaregiverDataRoomDatabase database){
            this.database = database;
        }
        @Override
        protected Void doInBackground(Void... voids) {


            // use add() method to add elements in the list

            FirstAidData firstAidData =  new FirstAidData("SNAKE BITE ",
                    "Help the casualty lie down, with head and shoulders raised. Reassure the casualty and \n" +
                            "advise her not to move the bitten limb to prevent venom from spreading. Call 911 for emergency help. \n" +
                            "If you have been properly trained, consider wrapping a pressure bandage around the entire length of \n" +
                            "the limb that was bitten. The bandage should be comfortably snug but loose enough to allow a finger \n" +
                            "to be slipped under it. Whether or not it is wrapped, the bitten limb should be immobilized with \n" +
                            "a splint to prevent the casualty from bending it. Keep the limb below the level of the heart. \n" +
                            "Monitor and record vital signs while waiting for emergency help. The casualty must remain still, \n" +
                            "and should be taken to the hospital as soon as possible.",
                    "Do not apply a tourniquet, slash the wound with a knife, or try to suck out the venom. \n" +
                            "If the casualty loses consciousness and is not breathing normally, begin CPR with chest compressions.", "");

            database.firstAidDao().insert(firstAidData);
            FirstAidData firstAidData1 =  new FirstAidData("HEART ATTACK",
                    "Call 911 for emergency help. Tell the dispatcher that you suspect a heart attack. \n" +
                            "If the casualty asks you to do so, call his own doctor too. Make the casualty as comfortable \n" +
                            "as possible to ease the strain on his heart. A half-sitting position, with his head and shoulders \n" +
                            "supported and his knees bent, is often best. Place cushions behind him and under his knees. \n" +
                            "Assist the casualty to take up to one full-dose adult aspirin tablet (325 mg) or four baby aspirin \n" +
                            "(81 mg each). Advise him to chew it slowly. If the casualty has angina medication, such as tablets or a pump-action or \n" +
                            "aerosol spray, let him administer it; help him if necessary. Encourage him to rest. \n" +
                            "Monitor and record vital signs—level of response, breathing, and pulse—while waiting for help. \n" +
                            "Stay calm to avoid undue stress.",
                   "If the casualty becomes unconscious and is not breathing normally, begin CPR with chest compressions.Ask the casualty, if conscious, about possible aspirin allergy."," ");
            database.firstAidDao().insert(firstAidData1);
            FirstAidData firstAidData2 =  new FirstAidData("STROKE","Look at the casualty’s face. \n" +
                    "Ask him to smile; if he has had a stroke he may only be able to smile on \n" +
                    "one side—the other side of his mouth may droop. Ask the casualty to raise both his arms; \n" +
                    "if he has had a stroke, he may be able to lift only one arm. \n" +
                    "Find out whether the person can speak clearly and understand what you say. \n" +
                    "When you ask a question, does he respond appropriately to you?  \n" +
                    "Call 911 for emergency help. Tell the dispatcher that you have used the FAST guide and you \n" +
                    "suspect a stroke. Keep the casualty comfortable and supported. \n" +
                    "If the casualty is conscious, you can help him lie down. \n" +
                    "Reassure him that help is on the way.  Regularly monitor and record vital signs—level of response, \n" +
                    "breathing, and pulse (— while waiting for help to arrive. \n" +
                    "Do not give the casualty anything to eat or drink because \n" +
                    "it may be difficult for him to swallow.","If the person loses consciousness and is not breathing (or just gasping), \n" +
                    "begin CPR with chest compressions.","");
            database.firstAidDao().insert(firstAidData2);

          //  (1, 'Asia Royal', '(+951) 2304999, (+951) 538055', 'No. 14, Baho Road, Sanchanung Tsp, Yangon, Myanmar.', 16.7982, 96.1312);

            HospitalAidData hospitalAidData =  new HospitalAidData("Asia Royal","(+951) 2304999, (+951) 538055","No. 14, Baho Road, Sanchanung Tsp, Yangon, Myanmar.",16.7982, 96.1312);
            database.hospitalAidDao().insert(hospitalAidData);


            return null;
        }
    }

}
