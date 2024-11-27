package celv.mobile.projectakhir;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.Viewholder> {
    private ArrayList<CartItem> items;
    private Context context;

    public CartAdapter(ArrayList<CartItem> items, Context context) {
        this.items = items;
        this.context = context;
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        TextView tvMenuName, tvJumlah, tvPrice;
        ImageView ivMenuImage;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            tvMenuName = itemView.findViewById(R.id.tvMenuName);
            tvJumlah = itemView.findViewById(R.id.tvJumlah);
            tvPrice = itemView.findViewById(R.id.tvPrice); // Menggunakan ID yang benar
            ivMenuImage = itemView.findViewById(R.id.ivMenuImage);
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

        // Set data ke ViewHolder
        holder.tvMenuName.setText(item.getMenuName());
        holder.tvJumlah.setText("Jumlah: " + item.getJumlah());
        holder.tvPrice.setText(String.format("Rp %,d", (long) (item.getJumlah() * item.getHargaSatuan())));

        // Set image (gunakan default jika URL tidak tersedia)
        int imageResource = R.drawable.trends; // Gambar default
        holder.ivMenuImage.setImageResource(imageResource);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}