package celv.mobile.projectakhir;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;


public class PaymentGateway extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PaymentAdapter paymentAdapter;
    private List<PaymentItem> paymentList;
    private Button btBayar;
    private RealtimeDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_gateway);
        db = new RealtimeDatabase();
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        int spaceInPixels = getResources().getDimensionPixelSize(R.dimen.item_spacing);
        recyclerView.addItemDecoration(new SpaceItemDecoration(spaceInPixels));

        btBayar = findViewById(R.id.btBayar);

        paymentList = new ArrayList<>();
        paymentAdapter = new PaymentAdapter(paymentList);
        recyclerView.setAdapter(paymentAdapter);

        fetchPaymentData();

        btBayar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PaymentItem selectedPayment = paymentAdapter.getSelectedItem();
                if (selectedPayment != null) {
                    String transactionNo = UUID.randomUUID().toString();

                    db.writeItem(
                            new PaymentModel(
                                    transactionNo,
                                    selectedPayment.getPaymentMethod(),
                                    LocalDate.now().toString(),
                                    Long.toString(new Random().nextLong())
                            ),
                            new DatabaseListener() {
                                @Override
                                public void onSuccess(Void a) {
                                    Toast.makeText(PaymentGateway.this,
                                            "Pembayaran diproses dengan " + selectedPayment.getPaymentMethod(),
                                            Toast.LENGTH_SHORT).show();

                                    Intent intent = new Intent(PaymentGateway.this, ActivityPaymentSuccess.class);
                                    intent.putExtra("transactionNo", transactionNo);
                                    startActivity(intent);
                                }

                                @Override
                                public void onError(Exception e) {
                                    Log.e("PaymentGateway", "Error writing item to Firebase", e);
                                }
                            }
                    );

                } else {
                    Toast.makeText(PaymentGateway.this,
                            "Silakan pilih metode pembayaran",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void fetchPaymentData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("https://run.mocky.io/v3/6ece3e7d-7d13-462b-9fe0-01cb3e0303f0");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.connect();

                    if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        InputStream inputStream = connection.getInputStream();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                        StringBuilder response = new StringBuilder();
                        String line;

                        while ((line = reader.readLine()) != null) {
                            response.append(line);
                        }

                        reader.close();
                        connection.disconnect();

                        parseJSONResponse(response.toString());
                    } else {
                        showError("Gagal mengambil data. Kode error: " + connection.getResponseCode());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    showError("Terjadi kesalahan: " + e.getMessage());
                }
            }
        }).start();
    }

    private void parseJSONResponse(String jsonResponse) {
        try {
            JSONObject jsonObject = new JSONObject(jsonResponse);

            JSONArray jsonArray = jsonObject.getJSONArray("paymentOptions");
            List<PaymentItem> items = new ArrayList<>();

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject itemObject = jsonArray.getJSONObject(i);

                String method = itemObject.getString("name");
                int imageId = getResources().getIdentifier(
                        itemObject.getString("icon"), "drawable", getPackageName());

                if (imageId == 0) {
                    Log.e("PaymentGateway", "Drawable resource not found: " + itemObject.getString("icon"));
                }

                items.add(new PaymentItem(method, imageId));
            }

            runOnUiThread(() -> {
                paymentList.clear();
                paymentList.addAll(items);
                paymentAdapter.notifyDataSetChanged();
            });
        } catch (JSONException e) {
            Log.e("PaymentGateway", "Error parsing JSON", e);
            showError("Kesalahan parsing data: " + e.getMessage());
        }
    }

    private void showError(final String message) {
        runOnUiThread(() -> Toast.makeText(PaymentGateway.this, message, Toast.LENGTH_SHORT).show());
    }
}
