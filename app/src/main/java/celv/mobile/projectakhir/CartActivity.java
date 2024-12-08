package celv.mobile.projectakhir;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {
    public static final String FirebaseURL = "https://projectakhir-9f429-default-rtdb.asia-southeast1.firebasedatabase.app/";
    private RecyclerView rvCart;
    private CartAdapter adapter;
    private ArrayList<CartItem> cartList = new ArrayList<>();
    private DatabaseReference cartRef;
    private Button btnPayment;
    private TextView tvTotalPayment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        rvCart = findViewById(R.id.rvCart);
        tvTotalPayment = findViewById(R.id.tvTotalPayment);
        btnPayment = findViewById(R.id.btnPayment);

        adapter = new CartAdapter(cartList, this, this);
        rvCart.setAdapter(adapter);
        rvCart.setLayoutManager(new LinearLayoutManager(this));

        FirebaseDatabase db = FirebaseDatabase.getInstance(FirebaseURL);
        cartRef = db.getReference("Cart");

        loadCartData();

        btnPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CartActivity.this, PaymentGateway.class);
                startActivity(intent);
            }
        });
    }

    protected void loadCartData() {
        cartRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Membersihkan daftar untuk menghindari duplikasi
                cartList.clear();
                int totalPayment = 0;

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    // Mengambil data dari Firebase
                    CartItem item = dataSnapshot.getValue(CartItem.class);

                    // Validasi untuk memastikan item tidak null
                    if (item != null && item.getMenuName() != null && item.getJumlah() > 0 && item.getHargaSatuan() > 0) {
                        totalPayment += item.getJumlah() * item.getHargaSatuan(); // Menghitung total pembayaran
                        cartList.add(item); // Menambahkan item ke daftar
                    }
                }

                // Memperbarui adapter dan total pembayaran
                adapter.notifyDataSetChanged();
                tvTotalPayment.setText(String.format("Total Payment: Rp %,d", totalPayment));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Menangani kesalahan dari Firebase
                Toast.makeText(CartActivity.this, "Failed to load cart: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
