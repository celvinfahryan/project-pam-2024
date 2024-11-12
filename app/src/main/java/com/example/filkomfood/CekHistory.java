package com.example.filkomfood;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class CekHistory extends AppCompatActivity  {
    private RecyclerView rvHistory;
    private HistoryAdapter historyAdapter;
    private Button btdetail;
    private List<History> data;

    interface Request{
        @GET("data.json")
        Call<List<History>> getHistory();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cek_history);


        this.rvHistory = this.findViewById(R.id.rvHistory);
        this.rvHistory.setLayoutManager(
                new LinearLayoutManager(this)
        );

        this.btdetail = this.findViewById(R.id.btdetail);
//        this.btdetail.setOnClickListener(this);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.216/myAPI/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Request request = retrofit.create(Request.class);
        Call<List<History>> call = request.getHistory();

        call.enqueue(new Callback<List<History>>() {
            @Override
            public void onResponse(Call<List<History>> call, Response<List<History>> response) {
                if(response.isSuccessful()){

                    data = response.body();
                    historyAdapter = new HistoryAdapter(CekHistory.this,data);
                    rvHistory.setAdapter(historyAdapter);
                    return;
                }
            }

            @Override
            public void onFailure(Call<List<History>> call, Throwable throwable) {
                Log.e("API Error", "Request failed", throwable);
                Toast.makeText(CekHistory.this, "Failed to load data: " + throwable.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

//        List<History> data = new ArrayList<>();
//        data.add(new History("Geprek Kantin 2", "31 Agustus 2024, 16.57", "Selesai", "Ayam bakar Cakalang", "14.000"));
//        data.add(new History("Geprek Kantin 1", "11 September 2024, 10.57", "Selesai", "Mie Ayam", "15.000"));
//        data.add(new History("Geprek Kantin 3", "22 Agustus 2024, 14.57", "Selesai", "Mie Yamin", "11.000"));
//        data.add(new History("Geprek Kantin 5", "5 Agustus 2024, 12.00", "Selesai", "Ayam Madu Bakar", "15.000"));
//        this.data = data;


        this.rvHistory.setAdapter(this.historyAdapter);


    }}

//    @Override
//    public void onClick(View view) {
//        Intent i = new Intent(this, RangkumanHistory.class);
//        startActivity(i);
//    }
//}