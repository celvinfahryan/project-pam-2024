package celv.mobile.projectakhir;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DetailMakanan extends AppCompatActivity {
    private TextView tvMenu, tvPrice, tvJumlah;
    private RadioGroup rgSambalLevel;
    private TextInputEditText etCatatan;
    private ImageView btnKurang, btnTambah;
    private Button btnAddToChart;
    private int jumlah = 1;
    private double hargaSatuan = 0;

    private PesananDatabase db;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private PesananDAO pesananDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_makanan);

        // Initialize views
        initViews();

        // Initialize database
        db = PesananDatabase.getInstance(this);

        // Get data from intent
        String menu = getIntent().getStringExtra("menu");
        double price = getIntent().getDoubleExtra("price", 0);

        // Set initial data
        tvMenu.setText(menu);
        hargaSatuan = price;
        tvPrice.setText(String.format("Rp %,d", (long) price));

        updateTotalHarga();

        // Handle quantity buttons
        btnKurang.setOnClickListener(v -> {
            if (jumlah > 1) {
                jumlah--;
                updateTotalHarga();
            }
        });

        btnTambah.setOnClickListener(v -> {
            jumlah++;
            updateTotalHarga();
        });

        // Handle order button
        btnAddToChart.setOnClickListener(v -> {
            // Get selected sambal level
            int selectedId = rgSambalLevel.getCheckedRadioButtonId();
            String sambalLevel = "Level 0";

            if (selectedId != -1) {
                RadioButton selectedRadioButton = findViewById(selectedId);
                sambalLevel = selectedRadioButton.getText().toString();
            }

            // Get catatan
            String catatan = etCatatan.getText().toString().trim();

            // Calculate total price
            double totalHarga = hargaSatuan * jumlah;

            // Create and save Pesanan to database
            Pesanan pesanan = new Pesanan();
            pesanan.menu = menu;
            pesanan.sambalLevel = sambalLevel;
            pesanan.jumlah = jumlah;
            pesanan.totalHarga = totalHarga;
            pesanan.catatan = catatan.isEmpty() ? "-" : catatan;

            executor.execute(() -> {
                try {
                    db.pesananDAO().insert(pesanan);
                    runOnUiThread(() -> {
                        String successMessage = String.format("Pesanan berhasil ditambahkan!\nTotal: Rp %,d",
                                (long)(hargaSatuan * jumlah));
                        Toast.makeText(DetailMakanan.this, successMessage, Toast.LENGTH_SHORT).show();
                        finish();
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    runOnUiThread(() ->
                            Toast.makeText(this, "Gagal menyimpan pesanan: " + e.getMessage(), Toast.LENGTH_SHORT).show()
                    );
                }
            });
        });
    }

    private void initViews() {
        tvMenu = findViewById(R.id.tvMenu);
        tvPrice = findViewById(R.id.tvPrice);
        rgSambalLevel = findViewById(R.id.rgSambel);
        etCatatan = findViewById(R.id.etCatatan);
        tvJumlah = findViewById(R.id.tvJumlah);
        btnKurang = findViewById(R.id.btKurang);
        btnTambah = findViewById(R.id.btTambah);
        btnAddToChart = findViewById(R.id.btAdd);
    }

    private void updateTotalHarga() {
        tvJumlah.setText(String.format("%d", jumlah));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        executor.shutdown();
    }
}