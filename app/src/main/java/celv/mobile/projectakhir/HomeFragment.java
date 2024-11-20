package celv.mobile.projectakhir;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HomeFragment extends Fragment {
    private HomeAdapter adapter;
    private RecyclerView rvFood;
    private RequestQueue queue;
    private MenuDatabase db;
    private final ArrayList<Menu> items = new ArrayList<>();
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Initialize RecyclerView
        rvFood = view.findViewById(R.id.view1);
        adapter = new HomeAdapter(items, getContext());
        rvFood.setAdapter(adapter);
        rvFood.setLayoutManager(new GridLayoutManager(getContext(), 2));

        // Initialize Room Database
        db = MenuDatabase.getInstance(requireContext());

        // Initialize Volley
        queue = Volley.newRequestQueue(requireContext());

        // Load data
        loadData();

        return view;
    }

    private void loadData() {
        String url = "http://192.168.43.104/myAPI/Home.json";

        StringRequest request = new StringRequest(
                Request.Method.GET,
                url,
                response -> {
                    try {
                        Gson gson = new Gson();
                        Menu[] menuArray = gson.fromJson(response, Menu[].class);

                        // Save to database in background
                        executor.execute(() -> {
                            try {
                                db.menuDAO().deleteAll(); // Clear old data
                                db.menuDAO().insertAll(menuArray);

                                // Load from database and update UI
                                items.clear();
                                items.addAll(db.menuDAO().getAllMenu());

                                // Update UI on main thread
                                if (getActivity() != null) {
                                    getActivity().runOnUiThread(() -> adapter.notifyDataSetChanged());
                                }
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
        if (getActivity() != null) {
            getActivity().runOnUiThread(() ->
                    Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show()
            );
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        executor.shutdown();
    }
}