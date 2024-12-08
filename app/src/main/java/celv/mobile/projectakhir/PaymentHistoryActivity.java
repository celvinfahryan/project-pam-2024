package  celv.mobile.projectakhir;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class PaymentHistoryActivity extends AppCompatActivity {
    RealtimeDatabase db;
    RecyclerView rv;
    FloatingActionButton fab;

    PaymentHistoryAdapter adapter;
    ArrayList<PaymentModel> listData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new RealtimeDatabase();
        setContentView(R.layout.activity_payment_history);
        listData = new ArrayList<>();

        rv = findViewById(R.id.rv_history);
        fab = findViewById(R.id.fab_add);
        adapter = new PaymentHistoryAdapter(listData);
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PaymentHistoryActivity.this, PaymentGateway.class);
                startActivity(intent);
            }
        });

        db.listenData(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listData.clear();
                for (DataSnapshot snp : snapshot.getChildren()){
                    PaymentModel item = snp.getValue(PaymentModel.class);
                    listData.add(item);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(PaymentHistoryActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });


    }
}