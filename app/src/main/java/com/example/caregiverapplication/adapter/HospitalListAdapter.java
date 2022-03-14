package com.example.caregiverapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.caregiverapplication.R;
import com.example.caregiverapplication.model.HospitalAidData;

import java.util.ArrayList;
import java.util.List;

public class HospitalListAdapter extends RecyclerView.Adapter<HospitalListAdapter.HospitalListViewHolder> {

    private Context context;

    private List<HospitalAidData> hospitalAidDataList = new ArrayList<>();



    public void setHospitalAidDataList(List<HospitalAidData> hospitalAidDataList) {
        this.hospitalAidDataList.clear();
        this.hospitalAidDataList.addAll(hospitalAidDataList);
        notifyDataSetChanged();
    }

    public HospitalListAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public HospitalListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.nearest_hospital_layout, parent, false);

        return new HospitalListViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull final HospitalListViewHolder holder, int position) {

         HospitalAidData hospitalAidData = hospitalAidDataList.get(position);
           holder.tvHospital.setText(hospitalAidData.getHospital_name());
           holder.tvPhone.setText(hospitalAidData.getPhone());
           holder.tvAddress.setText(hospitalAidData.getAddress());


    }

    @Override
    public int getItemCount() {
        return hospitalAidDataList.size();
    }


    public static class  HospitalListViewHolder extends RecyclerView.ViewHolder{

        private TextView tvHospital,tvPhone,tvAddress;


        public HospitalListViewHolder(@NonNull View itemView) {
            super(itemView);

            tvHospital = itemView.findViewById(R.id.tv_hospital);
            tvPhone = itemView.findViewById(R.id.tv_phone);
            tvAddress = itemView.findViewById(R.id.tv_address);

        }
    }
}
