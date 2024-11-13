package com.example.filkomfood;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.filkomfood.databinding.ActivityCekHistoryBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class CekHistory extends AppCompatActivity  {
    private Button btdetail;

    interface Request{
        @GET("data.json")
        Call<List<History>> getHistory();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cek_history);

        this.btdetail = this.findViewById(R.id.btdetail);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.framLayout, new HistoryFragment());
        fragmentTransaction.commit();}}

//    @Override
//    public void onClick(View view) {
//        Intent i = new Intent(this, RangkumanHistory.class);
//        startActivity(i);
//    }
//}
//        List<History> data = new ArrayList<>();
//        data.add(new History("Geprek Kantin 2", "31 Agustus 2024, 16.57", "Selesai", "Ayam bakar Cakalang", "14.000"));
//        data.add(new History("Geprek Kantin 1", "11 September 2024, 10.57", "Selesai", "Mie Ayam", "15.000"));
//        data.add(new History("Geprek Kantin 3", "22 Agustus 2024, 14.57", "Selesai", "Mie Yamin", "11.000"));
//        data.add(new History("Geprek Kantin 5", "5 Agustus 2024, 12.00", "Selesai", "Ayam Madu Bakar", "15.000"));
//        this.data = data;