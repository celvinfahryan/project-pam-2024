package celv.mobile.projectakhir;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    public static final String FirebaseURL = "https://projectakhir-9f429-default-rtdb.asia-southeast1.firebasedatabase.app/";
    private RecyclerView rvShop;
    private HomeAdapter adapter;
    private FirebaseDatabase db;
    private DatabaseReference shopRef;
    private ArrayList<Shop> shopList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Setup Firebase
        db = FirebaseDatabase.getInstance(FirebaseURL);
        shopRef = db.getReference("Shop");

        // Initialize RecyclerView
        rvShop = view.findViewById(R.id.rvShop);
        adapter = new HomeAdapter(shopList, getActivity());
        rvShop.setAdapter(adapter);
        rvShop.setLayoutManager(new GridLayoutManager(getContext(), 2));

        // Load data from Firebase
        loadShopData();

        return view;
    }

    private void loadShopData() {
        shopRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                shopList.clear();
                for (DataSnapshot shopSnapshot : snapshot.getChildren()) {
                    Shop shop = shopSnapshot.getValue(Shop.class);
                    if (shop != null) {
                        shop.setId(shopSnapshot.getKey()); // Set key sebagai ID
                        shopList.add(shop);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Failed to load data: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}