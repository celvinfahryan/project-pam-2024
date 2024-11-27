package com.example.filkomfood;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class HistoryFragment extends Fragment {
    private Handler handler;
    private RecyclerView rvHistory;
    private FirebaseFirestore db;
    private List<History> dataset = new ArrayList<>();
    private HistoryAdapter historyAdapter;


    public HistoryFragment() {
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
        this.dataset = new ArrayList<>();


        this.rvHistory = view.findViewById(R.id.rvHistoryFragment);
        this.rvHistory.setLayoutManager(new LinearLayoutManager(getContext()));

        this.historyAdapter = new HistoryAdapter(getContext(), dataset);
        this.rvHistory.setAdapter(historyAdapter);
        this.historyAdapter.notifyDataSetChanged();


        this.db = FirebaseFirestore.getInstance();
        this.db.collection("History").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@androidx.annotation.Nullable QuerySnapshot value, @androidx.annotation.Nullable FirebaseFirestoreException error) {
                for (DocumentChange dc: value.getDocumentChanges()) {
                    if (dc.getType() == DocumentChange.Type.ADDED){
                        dataset.add(dc.getDocument().toObject(History.class));
                    }
                    historyAdapter.notifyDataSetChanged();
                }
            }
        });
    }

}