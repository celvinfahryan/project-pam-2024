package celv.mobile.projectakhir;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.ArrayList;

//public class PilihMakanan extends AppCompatActivity {
//    private RecyclerView.Adapter adapterFoodList;
//    private RecyclerView recyclerViewFood;
//    private Button addBtn;
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.pilih_makanan);
//
//        String title = getIntent().getStringExtra("title");
//        String time = getIntent().getStringExtra("time");
//        int price = getIntent().getIntExtra("price", 0);
//        int picAddress = getIntent().getIntExtra("picAddress", 0);
//
//        ArrayList<Menu> items = new ArrayList<>();
////        items.add(new Menu(picAddress, time, title, 15000));
////        items.add(new Menu(picAddress, time, title, 15000));
////        items.add(new Menu(picAddress, time, title, 15000));
////        items.add(new Menu(picAddress, time, title, 15000));
////        items.add(new Menu(picAddress, time, title, 15000));
////        items.add(new Menu(picAddress, time, title, 15000));
//
//
//        recyclerViewFood = findViewById(R.id.view1);
//        adapterFoodList = new AdapterPilih(items, this);
//        recyclerViewFood.setAdapter(adapterFoodList);
//        recyclerViewFood.setLayoutManager(new GridLayoutManager(this, 2));
//    }
//}

public class PilihMakanan extends AppCompatActivity {
    private RecyclerView.Adapter adapterFoodList;
    private RecyclerView recyclerViewFood;
    private RequestQueue queue;
    ArrayList<Menu> items = new ArrayList<>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pilih_makanan);

        recyclerViewFood = findViewById(R.id.view1);
        items = new ArrayList<>();

        adapterFoodList = new AdapterPilih(items, this);
        recyclerViewFood.setAdapter(adapterFoodList);
        recyclerViewFood.setLayoutManager(new GridLayoutManager(this, 2));

        this.queue = Volley.newRequestQueue(this);

        String url = "http://192.168.1.19/myAPI/PilihMakanan.json";
        StringRequest req = new StringRequest(
                Request.Method.GET, url,
                response -> {
                    Gson gson = new Gson();
//                        JSONArray array = gson.fromJson(response, JSONArray.class);
                    Menu[] array = gson.fromJson(response, Menu[].class);
                    for (int i = 0; i < array.length; i++) {
                        items.add(array[i]);
                    }
                    adapterFoodList.notifyDataSetChanged();
                }, error -> {
            Toast.makeText(this, error.getMessage(), Toast.LENGTH_SHORT).show();

        }
        );
        queue.add(req);
    }
}