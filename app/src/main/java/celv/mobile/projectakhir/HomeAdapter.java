package celv.mobile.projectakhir;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.Viewholder> {
    ArrayList<Shop> items;
    Context context;

    public HomeAdapter(ArrayList<Shop> items, Context context) {
        this.items = items;
        this.context = context;
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        TextView time, shopName, description;
        ImageView pic;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.time);
            shopName = itemView.findViewById(R.id.shopName);
            description = itemView.findViewById(R.id.description);
            pic = itemView.findViewById(R.id.pic);
        }
    }

    @NonNull
    @Override
    public HomeAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rowView = LayoutInflater.from(parent.getContext()).inflate(R.layout.rowview_home, parent, false);
        return new Viewholder(rowView);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeAdapter.Viewholder holder, int position) {
        Shop shop = items.get(position);
        holder.time.setText(shop.getTime());
        holder.shopName.setText(shop.getShopName());
        holder.description.setText(shop.getDescription());

        Glide.with(context)
                .load(shop.getPic()) // URL gambar dari JSON
                .placeholder(R.drawable.trends) // Placeholder saat loading
                .error(R.drawable.trends) // Gambar error jika gagal memuat
                .into(holder.pic);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, PilihMakanan.class);
            intent.putExtra("idShop", items.get(position).getId());
            intent.putExtra("shopName", items.get(position).getShopName());
            intent.putExtra("pic", items.get(position).getPic());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}