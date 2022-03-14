package com.example.caregiverapplication.adapter;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.caregiverapplication.R;
import com.example.caregiverapplication.model.FirstAidData;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FirstAidAdapter extends RecyclerView.Adapter<FirstAidAdapter.MyViewHolder>
implements Filterable {

    private Context context;
    private List<FirstAidData> firstAidData = new ArrayList<>();
    private List<FirstAidData> firstAidDataFiltered = new ArrayList<>();
    private FirstAidListener listener;



    public interface FirstAidListener{
        void onFirstAidSelected(FirstAidData firstAidData);
    }


    public void setFirstAidData(List<FirstAidData> firstAidData) {
        Log.d("adapter","list adapter"+firstAidData.size());
        this.firstAidData = firstAidData;
        this.firstAidDataFiltered = firstAidData;
        notifyDataSetChanged();
    }

    public FirstAidAdapter(Context context, FirstAidListener listener) {
        this.context = context;

        this.listener = listener;
    }

    public FirstAidAdapter(Context context, List<FirstAidData> firstAidData, FirstAidListener listener) {
        this.context = context;
        this.firstAidData = firstAidData;
        this.firstAidDataFiltered = firstAidData;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rv_list_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        FirstAidData firstAidDataObj = firstAidDataFiltered.get(position);
        Log.d("Adapter","FirstAdapter = "+firstAidDataObj.getPhoto());
        holder.name.setText(firstAidDataObj.getName());
        holder.instruction.setText(firstAidDataObj.getInstruction());
        holder.cauption.setText(firstAidDataObj.getCaution());
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.transforms(new CenterCrop(), new RoundedCorners(15));
        requestOptions.placeholder(R.drawable.ic_image_black_24dp);
        requestOptions.error(R.drawable.ic_image_black_24dp);
       // File file = new File(firstAidDataObj.getPhoto());
        Uri uri =  Uri.fromFile(new File(firstAidDataObj.getPhoto()));
       // Uri uri = Uri.fromFile(firstAidDataObj.getPhoto());
        //RequestOptions.circleCropTransform()
        Glide.with(context)
                .load(uri)
                .apply(requestOptions)
                .into(holder.imgPhoto);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onFirstAidSelected(firstAidDataFiltered.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return firstAidDataFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                Log.d("adapter","filter = "+charString);

                if (charString.isEmpty()) {
                    firstAidDataFiltered = firstAidData;
                } else {
                    List<FirstAidData> filteredList = new ArrayList<>();
                    for (FirstAidData row : firstAidData) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                       // Log.d("adapter","adapter search data = "+row.getInstruction().toLowerCase());
                       // Log.d("adapter","adapter search data 2= "+charString.toLowerCase());
                        if (row.getName().toLowerCase().contains(charString.toLowerCase())) {
                            Log.d("adapter","true adapter");
                            // || row.getPhone().contains(charSequence)
                            filteredList.add(row);
                        }else{
                            Log.d("adapter","false adapter");
                        }
                    }

                    firstAidDataFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = firstAidDataFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                firstAidDataFiltered = (ArrayList<FirstAidData>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView instruction,cauption,name;
        public ImageView imgPhoto;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tv_name);
            instruction = itemView.findViewById(R.id.tv_instruction);
            cauption = itemView.findViewById(R.id.tv_caution);
            imgPhoto = itemView.findViewById(R.id.img_data);

           /* itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onFirstAidSelected(firstAidDataFiltered.get(getAdapterPosition()));
                }
            });*/
        }
    }
}
