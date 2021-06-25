package com.example.checkcovid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import com.example.checkcovid.Api.ApiUtilities;
import com.example.checkcovid.Api.CountryData;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CountryActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<CountryData> lists;
    private ProgressDialog progressDialog;
    private EditText search;
    CountryAdapter countryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country);

        recyclerView=findViewById(R.id.countries);
        search=findViewById(R.id.search);
        lists=new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        countryAdapter=new CountryAdapter(lists,this);
        recyclerView.setAdapter(countryAdapter);



        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Loading");
        progressDialog.setCancelable(false);
        progressDialog.show();


        ApiUtilities.getApiInterface().getCountryData().enqueue(new Callback<List<CountryData>>() {
            @Override
            public void onResponse(Call<List<CountryData>> call, Response<List<CountryData>> response) {
                lists.addAll(response.body());

                countryAdapter.notifyDataSetChanged();
                progressDialog.dismiss();


            }

            @Override
            public void onFailure(Call<List<CountryData>> call, Throwable t) {
                progressDialog.dismiss();

                Toast.makeText(CountryActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                fileList(s.toString());

            }
        });
    }

    private void fileList(String text) {

        List<CountryData> filteredList=new ArrayList<>();

        for (CountryData items: lists){
            if (items.getCountry().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(items);

            }

        }

        countryAdapter.filterList(filteredList);

    }
}