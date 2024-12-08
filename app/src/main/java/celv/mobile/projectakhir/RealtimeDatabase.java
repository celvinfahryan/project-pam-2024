package celv.mobile.projectakhir;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.UUID;

public class RealtimeDatabase {

    private final DatabaseReference mDatabase;
    FirebaseDatabase db;
    private static final String databaseName = "payments";
    private static final String tableName = "items";

    public RealtimeDatabase() {
        db = FirebaseDatabase.getInstance("https://filkomfood-default-rtdb.firebaseio.com");
        mDatabase = db.getReference(databaseName);
    }

    public void listenData(ValueEventListener listener) {
        mDatabase.addValueEventListener(listener);
    }

    public void writeItem(PaymentModel dataItem, DatabaseListener dbListener) {
        mDatabase.child(UUID.randomUUID().toString()).setValue(dataItem).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                dbListener.onSuccess(unused);
            }
        }).addOnFailureListener(dbListener::onError);
    }

    public void deleteItem(PaymentModel dataItem, DatabaseListener dbListener) {
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    String uid = snapshot1.child("uid").getValue(String.class);
                    if (dataItem.getUid().equals(uid)) {
                        // Delete the item
                        snapshot1.getRef().removeValue()
                                .addOnSuccessListener(dbListener::onSuccess)
                                .addOnFailureListener(dbListener::onError);
                        break; // Exit the loop once the item is found
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Firebase", "Error reading data", error.toException());
            }
        });

    }


}
