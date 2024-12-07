package  celv.mobile.projectakhir;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ActivityPaymentSuccess extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_success);

//        TextView paymentSN = findViewById(R.id.paymentSN);
        Button btnKembali = findViewById(R.id.btKembali);

        String transactionNo = getIntent().getStringExtra("transactionNo");
        Log.d("TransactionCheck", "Transaction ID: " + transactionNo);

        DatabaseReference databaseRef = FirebaseDatabase.getInstance("https://filkomfood-default-rtdb.firebaseio.com/").getReference("payments");

        if (transactionNo != null) {
            Log.d("TransactionCheck", "Transaction ID is not null: " + transactionNo);
//
//            databaseRef.orderByChild("transactionNo").equalTo(transactionNo)
//                    .addChildEventListener(new ChildEventListener() {
//
//                        @Override
//                        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//                            Log.d("FirebaseData", "DataSnapshot: " + dataSnapshot.toString());
//                            if (dataSnapshot.exists()) {
//                                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
//                                    String sn = childSnapshot.child("serialNumber").getValue(String.class);
//                                    Log.d("TransactionMatch", "Serial Number: " + sn);
//
//                                    if (sn != null) {
//                                        paymentSN.setText("SN: " + sn);
//                                    } else {
//                                        paymentSN.setText("SN: Tidak ditemukan");
//                                    }
//                                    break; // Keluar setelah menemukan data pertama
//                                }
//                            } else {
//                                Log.d("FirebaseData", "No matching data found for transactionNo: " + transactionNo);
//                                paymentSN.setText("SN: Tidak ditemukan");
//                            }
//                        }
//
//                        @Override
//                        public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//                        }
//
//                        @Override
//                        public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
//                        }
//
//                        @Override
//                        public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError databaseError) {
//                            Log.e("FirebaseError", "Database error: " + databaseError.getMessage());
//                            paymentSN.setText("SN: Gagal memuat data");
//                        }
//                    });
//        } else {
//            Log.e("TransactionCheck", "Transaction ID is null");
//            paymentSN.setText("SN: Tidak ditemukan juga");
//        }

            btnKembali.setOnClickListener(v -> {
                Intent intent = new Intent(ActivityPaymentSuccess.this, Home.class);
                startActivity(intent);
                finish();
            });
        }
    }
}