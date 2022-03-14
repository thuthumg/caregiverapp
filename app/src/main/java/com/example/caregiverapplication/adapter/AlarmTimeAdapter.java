package com.example.caregiverapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.caregiverapplication.R;
import com.example.caregiverapplication.model.AlarmData;

import java.util.ArrayList;
import java.util.List;

public class AlarmTimeAdapter extends RecyclerView.Adapter<AlarmTimeAdapter.AlarmTimeViewHolder> {

    private Context context;

    private List<AlarmData> alarmDataList = new ArrayList<>();

    private AlarmTimeClickListener alarmTimeClickListener;

    public  interface AlarmTimeClickListener{
        void toggleOnOffListener(AlarmData alarmData);
    }

    public void setAlarmDataList(List<AlarmData> alarmDataList) {
        this.alarmDataList.clear();
        this.alarmDataList.addAll(alarmDataList);
        notifyDataSetChanged();
    }

    public AlarmTimeAdapter(Context context, AlarmTimeClickListener alarmTimeClickListener) {
        this.context = context;
        this.alarmTimeClickListener = alarmTimeClickListener;
    }

    @NonNull
    @Override
    public AlarmTimeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.toggle_alarm_list_layout, parent, false);

        return new AlarmTimeViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull final AlarmTimeViewHolder holder, int position) {

        final AlarmData alarmData = alarmDataList.get(position);
        holder.tvTimeData.setText(alarmData.getAlarm_time());
        if(alarmData.getFlag() == 1)
        {
            holder.toggleOnOff.setChecked(true);
        }else{
            holder.toggleOnOff.setChecked(false);
        }
        holder.toggleOnOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.toggleOnOff.isChecked())
                {
                    alarmData.setFlag(1);
                }else{
                    alarmData.setFlag(0);
                }

                alarmTimeClickListener.toggleOnOffListener(alarmData);
            }
        });



    }

    @Override
    public int getItemCount() {
        return alarmDataList.size();
    }


    public static class  AlarmTimeViewHolder extends RecyclerView.ViewHolder{

        private TextView tvTimeData;
        private SwitchCompat toggleOnOff;

        public AlarmTimeViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTimeData = itemView.findViewById(R.id.tv_time_data);
            toggleOnOff = itemView.findViewById(R.id.toggle_on_off);

        }
    }
}
