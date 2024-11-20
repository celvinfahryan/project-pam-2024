package celv.mobile.projectakhir;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PilihMakanan extends AppCompatActivity {
    private AdapterPilih adapter;
    private RecyclerView rvFood;
    private RequestQueue queue;
    private MenuDatabase db;
    private final ArrayList<Menu> items = new ArrayList<>();
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private PesananDAO pesananDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pilih_makanan);

        // Initialize RecyclerView
        rvFood = findViewById(R.id.view1);
        adapter = new AdapterPilih(items, this);
        rvFood.setAdapter(adapter);
        rvFood.setLayoutManager(new GridLayoutManager(this, 2));

        // Initialize Room Database
        db = MenuDatabase.getInstance(this);

        // Initialize Volley
        queue = Volley.newRequestQueue(this);

        loadDataFromAPI();
    }

    private void loadDataFromAPI() {
        String url = "http://192.168.43.104/myAPI/PilihMakanan.json";

        StringRequest request = new StringRequest(
                Request.Method.GET,
                url,
                response -> {
                    try {
                        Gson gson = new Gson();
                        Menu[] menuArray = gson.fromJson(response, Menu[].class);

                        executor.execute(() -> {
                            try {
                                // Save to database
                                db.menuDAO().insertAll(menuArray);

                                // Update UI with data from database
                                items.clear();
                                items.addAll(Arrays.asList(menuArray));

                                runOnUiThread(() -> adapter.notifyDataSetChanged());
                            } catch (Exception e) {
                                e.printStackTrace();
                                showError("Database error: " + e.getMessage());
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                        showError("JSON parsing error: " + e.getMessage());
                    }
                },
                error -> showError("Network error: " + error.getMessage())
        );

        queue.add(request);
    }

    private void showError(String message) {
        runOnUiThread(() ->
                Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        executor.shutdown();
    }
}