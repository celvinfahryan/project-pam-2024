package celv.mobile.projectakhir;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.Viewholder> {
    private ArrayList<CartItem> items;
    private Context context;
    private DatabaseReference cartRef;
    private CartActivity activity;

    public CartAdapter(ArrayList<CartItem> items, Context context, CartActivity activity) {
        this.items = items;
        this.context = context;
        this.activity = activity;
        this.cartRef = FirebaseDatabase.getInstance(CartActivity.FirebaseURL).getReference("Cart");
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        TextView tvMenuName, tvJumlah, tvPrice;
        ImageView ivMenuImage;
        ImageButton btnDelete;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            tvMenuName = itemView.findViewById(R.id.tvMenuName);
            tvJumlah = itemView.findViewById(R.id.tvJumlah);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            ivMenuImage = itemView.findViewById(R.id.ivMenuImage);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rowView = LayoutInflater.from(parent.getContext()).inflate(R.layout.rowview_cart, parent, false);
        return new Viewholder(rowView);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        CartItem item = items.get(position);

        holder.tvMenuName.setText(item.getMenuName());
        holder.tvJumlah.setText("Jumlah: " + item.getJumlah());
        holder.tvPrice.setText(String.format("Rp %,d", (long) (item.getJumlah() * item.getHargaSatuan())));

        Glide.with(context)
                .load(item.getPic())
                .placeholder(R.drawable.ayam_krispy)
                .error(R.drawable.ic_launcher_background)
                .into(holder.ivMenuImage);

        // Set listener untuk tombol hapus
        holder.btnDelete.setOnClickListener(v -> {
            // Hapus item dari Firebase
            cartRef.child(item.getMenuName()).removeValue().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(context, "Item deleted successfully!", Toast.LENGTH_SHORT).show();
                    items.remove(position); // Hapus item dari daftar lokal
                    notifyItemRemoved(position); // Perbarui RecyclerView

                } else {
                    Toast.makeText(context, "Failed to delete item: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
