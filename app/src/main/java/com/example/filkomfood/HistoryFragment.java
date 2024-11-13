package com.example.filkomfood;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;


interface Request{
    @GET("data.json")
    Call<List<History>> getHistory();
}

public class HistoryFragment extends Fragment {

    private List<History> data;
    private HistoryAdapter historyAdapter;
    private RecyclerView rvHistory;


    public HistoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_history, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.data = new ArrayList<>();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.216/myAPI/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        CekHistory.Request request = retrofit.create(CekHistory.Request.class);
        Call<List<History>> call = request.getHistory();

        call.enqueue(new Callback<List<History>>() {
            @Override
            public void onResponse(Call<List<History>> call, Response<List<History>> response) {
                if(response.isSuccessful()){
                    data = response.body();
                    historyAdapter = new HistoryAdapter(getContext(),data);
                    rvHistory.setAdapter(historyAdapter);
                    return;
                }
            }

            @Override
            public void onFailure(Call<List<History>> call, Throwable throwable) {
                Log.e("API Error", "Request failed", throwable);
                Toast.makeText(getContext(), "Failed to load data: " + throwable.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        this.rvHistory = view.findViewById(R.id.rvHistoryFragment);
        this.rvHistory.setLayoutManager(new LinearLayoutManager(getContext()));

        this.historyAdapter = new HistoryAdapter(getContext(), data);
        this.rvHistory.setAdapter(historyAdapter);
        this.historyAdapter.notifyDataSetChanged();
    }
}