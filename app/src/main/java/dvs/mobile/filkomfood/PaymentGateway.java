package dvs.mobile.filkomfood;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class PaymentGateway extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PaymentAdapter paymentAdapter;
    private List<PaymentItem> paymentList = new ArrayList<>();
    private Button btBayar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_gateway);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        int spaceInPixels = getResources().getDimensionPixelSize(R.dimen.item_spacing);
        recyclerView.addItemDecoration(new SpaceItemDecoration(spaceInPixels));
        paymentAdapter = new PaymentAdapter(paymentList);

        paymentAdapter = new PaymentAdapter(paymentList);
        recyclerView.setAdapter(paymentAdapter);

        btBayar = findViewById(R.id.btBayar);
        btBayar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast
                Toast.makeText(PaymentGateway.this, "Pembayaran diproses", Toast.LENGTH_SHORT).show();

                //Intent ke halaman pembayaran berhasil
                Intent intent = new Intent(PaymentGateway.this, ActivityPaymentSuccess.class);
                startActivity(intent);
            }
        });

        fetchPaymentOptionsFromMocky();
    }

    private void fetchPaymentOptionsFromMocky() {
        new Thread(() -> {
            try {
                URL url = new URL("https://run.mocky.io/v3/76fae726-997a-4e28-8aa0-31e805364569");
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");

                BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder jsonBuilder = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    jsonBuilder.append(line);
                }

                reader.close();
                String jsonResponse = jsonBuilder.toString();

                // Parse JSON response utk daftar metode pembayaran
                Gson gson = new Gson();
                JsonObject jsonObject = gson.fromJson(jsonResponse, JsonObject.class);
                List<PaymentItem> fetchedPaymentOptions = gson.fromJson(
                        jsonObject.getAsJsonArray("paymentOptions"),
                        new TypeToken<List<PaymentItem>>() {}.getType()
                );

                // Update UI on the main thread
                runOnUiThread(() -> {
                    paymentList.clear();
                    paymentList.addAll(fetchedPaymentOptions);
                    paymentAdapter.notifyDataSetChanged();
                });

            } catch (Exception e) {
                runOnUiThread(() -> Toast.makeText(PaymentGateway.this, "Failed to load payment options", Toast.LENGTH_SHORT).show());
                e.printStackTrace();
            }
        }).start();
    }
}
