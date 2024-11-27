package celv.mobile.projectakhir;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DetailMakanan extends AppCompatActivity {
    public static final String FirebaseURL = "https://projectakhir-9f429-default-rtdb.asia-southeast1.firebasedatabase.app/";
    private TextView tvMenu, tvPrice, tvJumlah;
    private RadioGroup rgSambalLevel;
    private TextInputEditText etCatatan;
    private ImageView btnKurang, btnTambah;
    private Button btnAddToChart;
    private int jumlah = 1;
    private int hargaSatuan = 0;
    private String menuName;
    private DatabaseReference cartRef;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_makanan);

        // Bind views seperti sebelumnya
        tvMenu = findViewById(R.id.tvMenu);
        tvPrice = findViewById(R.id.tvPrice);
        rgSambalLevel = findViewById(R.id.rgSambel);
        etCatatan = findViewById(R.id.etCatatan);
        tvJumlah = findViewById(R.id.tvJumlah);
        btnKurang = findViewById(R.id.btKurang);
        btnTambah = findViewById(R.id.btTambah);
        btnAddToChart = findViewById(R.id.btAdd);

        // Ambil data dari Intent
        Intent intent = getIntent();
        menuName = intent.getStringExtra("menu");
        hargaSatuan = intent.getIntExtra("price", 0);

        // Set data ke tampilan
        tvMenu.setText(menuName);
        tvPrice.setText(String.format("Rp %,d", (long) hargaSatuan));
        tvJumlah.setText(String.valueOf(jumlah));

        // Inisialisasi Firebase
        FirebaseDatabase db = FirebaseDatabase.getInstance(FirebaseURL);
        cartRef = db.getReference("Cart");

        // Event listener untuk tombol
        btnTambah.setOnClickListener(v -> {
            jumlah++;
            updateHarga();
        });

        btnKurang.setOnClickListener(v -> {
            if (jumlah > 1) {
                jumlah--;
                updateHarga();
            }
        });

        btnAddToChart.setOnClickListener(v -> {
            String selectedSambal = getSelectedSambal();
            String catatan = etCatatan.getText().toString();

            // Buat objek CartItem
            CartItem cartItem = new CartItem(menuName, hargaSatuan, jumlah, selectedSambal, catatan);

            // Tambahkan ke Firebase
            String cartId = cartRef.push().getKey();
            if (cartId != null) {
                cartRef.child(cartId).setValue(cartItem)
                        .addOnSuccessListener(unused -> {
                            Toast.makeText(this, "Added to cart", Toast.LENGTH_SHORT).show();
                            finish(); // Tutup aktivitas ini
                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(this, "Failed to add to cart: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        });
            }
        });
    }

    private void updateHarga() {
        double totalHarga = jumlah * hargaSatuan;
        tvPrice.setText(String.format("Rp %,d", (long) totalHarga));
        tvJumlah.setText(String.valueOf(jumlah));
    }

    private String getSelectedSambal() {
        int selectedId = rgSambalLevel.getCheckedRadioButtonId();
        if (selectedId == -1) {
            return "Tidak ada"; // Default jika tidak ada pilihan
        }
        RadioButton radioButton = findViewById(selectedId);
        return radioButton.getText().toString();
    }

}
