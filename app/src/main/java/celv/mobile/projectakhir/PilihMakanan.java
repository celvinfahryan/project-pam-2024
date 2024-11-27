package celv.mobile.projectakhir;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PilihMakanan extends AppCompatActivity {
    public static final String FirebaseURL = "https://projectakhir-9f429-default-rtdb.asia-southeast1.firebasedatabase.app/";
    private RecyclerView rvMenu;
    private AdapterPilih adapter;
    private FirebaseDatabase db;
    private DatabaseReference appdb;
    private ArrayList<Menu> items = new ArrayList<>();
    private Button btCheckout;
    private BottomNavigationView chart;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pilih_makanan);

        String shopName = getIntent().getStringExtra("shopName");
        String idShop = getIntent().getStringExtra("idShop");
        String pic = getIntent().getStringExtra("pic");

        TextView tvShopName = findViewById(R.id.tvShopName); // Pastikan ID sesuai dengan layout Anda
        tvShopName.setText(shopName);

        // Setup Firebase
        db = FirebaseDatabase.getInstance(FirebaseURL);

        // Ubah referensi ke Menu berdasarkan idShop
        appdb = db.getReference("Shop").child(idShop).child("Menu");

        // Initialize RecyclerView
        rvMenu = findViewById(R.id.rvMenu);
        adapter = new AdapterPilih(items, this);
        rvMenu.setAdapter(adapter);
        rvMenu.setLayoutManager(new GridLayoutManager(this, 2));

        btCheckout = findViewById(R.id.btCheckout);
        chart = findViewById(R.id.chart);

        // Load data from Firebase
        loadMenuData();

        onResume();

        btCheckout.setOnClickListener(v -> {
            Intent intent = new Intent(PilihMakanan.this, CartActivity.class);
            startActivity(intent);
        });
    }

    private void loadMenuData() {
        appdb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                items.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Menu menu = dataSnapshot.getValue(Menu.class);
                    if (menu != null) {
                        items.add(menu);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(PilihMakanan.this, "Failed to load data: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Tambahkan logika untuk membuat tombol terlihat
    private void showSummaryButtonIfCartNotEmpty() {
        DatabaseReference cartRef = FirebaseDatabase.getInstance(FirebaseURL).getReference("Cart");
        cartRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    chart.setVisibility(View.VISIBLE);
                    btCheckout.setVisibility(View.VISIBLE);
                } else {
                    chart.setVisibility(View.GONE);
                    btCheckout.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(PilihMakanan.this, "Failed to check cart: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Panggil fungsi ini di akhir loadMenuData()
    @Override
    protected void onResume() {
        super.onResume();
        showSummaryButtonIfCartNotEmpty();
    }
}