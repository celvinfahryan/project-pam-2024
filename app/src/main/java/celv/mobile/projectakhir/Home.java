//package celv.mobile.projectakhir;
//
//import android.os.Bundle;
//import android.widget.Toast;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.GridLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.android.volley.Request;
//import com.android.volley.RequestQueue;
//import com.android.volley.toolbox.StringRequest;
//import com.android.volley.toolbox.Volley;
//import com.google.gson.Gson;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class Home extends AppCompatActivity {
//    private RecyclerView.Adapter adapterFoodList;
//    private RecyclerView recyclerViewFood;
//    private RequestQueue queue;
//    ArrayList<Menu> items = new ArrayList<>();
//
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.home);
//
//        recyclerViewFood = findViewById(R.id.view1);
//        items = new ArrayList<>();
//
//        adapterFoodList = new HomeAdapter(items, this);
//        recyclerViewFood.setAdapter(adapterFoodList);
//        recyclerViewFood.setLayoutManager(new GridLayoutManager(this, 2));
//
//        this.queue = Volley.newRequestQueue(this);
//
//        String url = "http://192.168.1.19/myAPI/Home.json";
//        StringRequest req = new StringRequest(
//                Request.Method.GET, url,
//                response -> {
//                    Gson gson = new Gson();
//                    Menu[] array = gson.fromJson(response, Menu[].class);
//                    for (int i = 0; i < array.length; i++) {
//                        items.add(array[i]);
//                    }
//                    adapterFoodList.notifyDataSetChanged();
//                }, error -> {
//            Toast.makeText(this, error.getMessage(), Toast.LENGTH_SHORT).show();
//
//        }
//        );
//        queue.add(req);
//    }
//}

package celv.mobile.projectakhir;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.ArrayList;

public class Home extends AppCompatActivity {
    private RecyclerView.Adapter adapterFoodList;
    private RecyclerView recyclerViewFood;
    private RequestQueue queue;
    ArrayList<Menu> items = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        recyclerViewFood = findViewById(R.id.view1);
        items = new ArrayList<>();

        adapterFoodList = new HomeAdapter(items, this);
        recyclerViewFood.setAdapter(adapterFoodList);
        recyclerViewFood.setLayoutManager(new GridLayoutManager(this, 2));

        this.queue = Volley.newRequestQueue(this);

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
                    Toast.makeText(this, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
        );
        queue.add(req);
    }
}