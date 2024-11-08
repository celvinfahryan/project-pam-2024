package celv.mobile.projectakhir;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    private RecyclerView.Adapter adapterFoodList;
    private RecyclerView recyclerViewFood;
    private RequestQueue queue;
    ArrayList<Menu> items = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerViewFood = view.findViewById(R.id.view1);
        items = new ArrayList<>();

        adapterFoodList = new HomeAdapter(items, getContext());
        recyclerViewFood.setAdapter(adapterFoodList);
        recyclerViewFood.setLayoutManager(new GridLayoutManager(getContext(), 2));

        this.queue = Volley.newRequestQueue(getContext());

        String url = "http://192.168.1.9/myAPI/Home.json";
        StringRequest req = new StringRequest(
                Request.Method.GET, url,
                response -> {
                    Gson gson = new Gson();
                    Menu[] array = gson.fromJson(response, Menu[].class);
                    for (int i = 0; i < array.length; i++) {
                        items.add(array[i]);
                    }
                    adapterFoodList.notifyDataSetChanged();
                },
                error -> {
                    Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                }
        );
        queue.add(req);

        return view;

    }
}