package com.example.checkcovid;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.checkcovid.Api.CountryData;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.CountryHolder> {

    List<CountryData> list;
    Context context;

    public CountryAdapter(List<CountryData> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public CountryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(context).inflate(R.layout.country_item,parent,false);

        return new CountryHolder(view);
    }

    // for search country

    public void filterList(List<CountryData> filterList){
        list=filterList;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull CountryHolder holder, int position) {

        final CountryData data=list.get(position);

        holder.countryCases.setText(NumberFormat.getInstance().format(Integer.parseInt(data.getCases())));
        holder.countryName.setText(data.getCountry());
        holder.sno.setText(String.valueOf(position+1));

        Map<String, String> img=data.getCountryInfo();
        Glide.with(context).load(img.get("flag")).into(holder.imageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,MainActivity.class);
                intent.putExtra("country",data.getCountry());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class CountryHolder extends RecyclerView.ViewHolder {

        private TextView sno,countryName,countryCases;
        private ImageView imageView;

        public CountryHolder(@NonNull View itemView) {
            super(itemView);

            sno=itemView.findViewById(R.id.sno);
            countryName=itemView.findViewById(R.id.countryName);
            countryCases=itemView.findViewById(R.id.countryCases);
            imageView=itemView.findViewById(R.id.countryFlag);
        }
    }
}
