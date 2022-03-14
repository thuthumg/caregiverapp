package com.example.caregiverapplication;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.caregiverapplication.adapter.AlarmTimeAdapter;
import com.example.caregiverapplication.model.AlarmData;
import com.example.caregiverapplication.notialarm.MyNotificationPublisher;
import com.google.android.material.button.MaterialButton;

import java.util.Calendar;
import java.util.List;

public class FrmSetAlarm extends AppCompatActivity {
    AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    private TimePicker alarmTimePicker;
    private static FrmSetAlarm inst;
    private TextView alarmTextView;
    private RecyclerView rvAlarmTimeAddData;
    private AlarmTimeAdapter alarmTimeAdapter;
    private MaterialButton btnAdd;
    private CaregiverViewModel caregiverViewModel;
    Calendar cal;
    PendingIntent pintent;
    private PendingIntent alarmIntent;
    public static final String  NOTIFICATION_CHANNEL_ID = "10001";
    private final static String default_notification_channel_id = "default" ;

    public static FrmSetAlarm instance() {
        return inst;
    }

    private boolean toggleFlag;
    @Override
    public void onStart() {
        super.onStart();
        inst = this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_set_alarm);
        alarmTimePicker = findViewById(R.id.alarmTimePicker);
       // alarmTimePicker.setIs24HourView(true);
        rvAlarmTimeAddData = findViewById(R.id.rv_alarm_time_add_data);

         alarmManager = (AlarmManager) getSystemService(Context. ALARM_SERVICE ) ;


        btnAdd = findViewById(R.id.btn_add);
        caregiverViewModel = ViewModelProviders.of(this).get(CaregiverViewModel.class);

        alarmTimeAdapter = new AlarmTimeAdapter(this, new AlarmTimeAdapter.AlarmTimeClickListener() {
            @Override
            public void toggleOnOffListener(AlarmData alarmData) {
              //  alarmManager.cancel(pintent);
               // callAlarmFunction(false);
                caregiverViewModel.updateAlarmData(alarmData);

                toggleFlag = false;
                scheduleNotification(getNotification( "5 minutes delay" ) , 300000,toggleFlag ) ;

             //   cancelAlarm();
            }
        });
        rvAlarmTimeAddData.setNestedScrollingEnabled(false);
        rvAlarmTimeAddData.setLayoutManager(new LinearLayoutManager(this));
        rvAlarmTimeAddData.setAdapter(alarmTimeAdapter);
        caregiverViewModel.allAlramDatas.observe(this, new Observer<List<AlarmData>>() {
            @Override
            public void onChanged(List<AlarmData> alarmData) {
                alarmTimeAdapter.setAlarmDataList(alarmData);
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int hour, minute;
                String am_pm;
                if (Build.VERSION.SDK_INT >= 23 ){
                    hour = alarmTimePicker.getHour();
                    minute = alarmTimePicker.getMinute();
                }
                else{
                    hour = alarmTimePicker.getCurrentHour();
                    minute = alarmTimePicker.getCurrentMinute();
                }
                if(hour > 12) {
                    am_pm = "PM";
                    hour = hour - 12;
                }
                else
                {
                    am_pm="AM";
                }
                String hours = "";
                String minutes = "";

                if(hour>9)
                {
                    hours = String.valueOf(hour);
                }else{
                    hours = '0'+String.valueOf(hour);
                }
                if(minute>9)
                {
                    minutes = String.valueOf(minute);
                }else{
                    minutes = '0'+String.valueOf(minute);
                }
              //  Toast.makeText(FrmSetAlarm.this,"alaram time = "+hours +":"+ minutes+" "+am_pm,Toast.LENGTH_LONG).show();

                AlarmData alarmData = new AlarmData(hours +":"+ minutes+" "+am_pm,1);
                caregiverViewModel.insertAlarmData(alarmData);
                toggleFlag = true;
               // scheduleNotification(getNotification( "10 second delay" ) , 1000,toggleFlag ) ;
                scheduleNotification(getNotification( "5 minutes delay" ) , 300000,toggleFlag ) ;

            }
        });
    }

    private void scheduleNotification (Notification notification , int delay,boolean toggleFlag) {
        Intent notificationIntent = new Intent( this, MyNotificationPublisher. class ) ;
        notificationIntent.putExtra(MyNotificationPublisher. NOTIFICATION_ID , 1 ) ;
        notificationIntent.putExtra(MyNotificationPublisher. NOTIFICATION , notification) ;
        PendingIntent pendingIntent = PendingIntent. getBroadcast ( this, 0 , notificationIntent , PendingIntent. FLAG_UPDATE_CURRENT ) ;
        long futureInMillis = SystemClock. elapsedRealtime () + delay ;

        assert alarmManager != null;
        if(toggleFlag) {
            alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, AlarmManager.INTERVAL_DAY,pendingIntent);
            // alarmManager.set(AlarmManager. ELAPSED_REALTIME_WAKEUP , futureInMillis , pendingIntent) ;
        }
        else
        {    alarmManager.cancel(pendingIntent);}
    }
    private Notification getNotification (String content) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder( this, default_notification_channel_id ) ;
        builder.setContentTitle( "Scheduled Notification" ) ;
        builder.setContentText(content) ;
        builder.setSmallIcon(R.drawable. app_icon ) ;
        builder.setAutoCancel( true ) ;
        builder.setChannelId( NOTIFICATION_CHANNEL_ID ) ;
        return builder.build() ;
    }


    @Override
    public void onBackPressed() {
       // super.onBackPressed();
        setResult(RESULT_OK);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 0 && resultCode == RESULT_OK){
            alarmManager.cancel(pendingIntent);
        }
    }
}

