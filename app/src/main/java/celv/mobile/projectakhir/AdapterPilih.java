package celv.mobile.projectakhir;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterPilih extends RecyclerView.Adapter<AdapterPilih.Viewholder> {
    ArrayList<Menu> items;
    Context context;

    public AdapterPilih(ArrayList<Menu> items, Context context) {
        this.items = items;
        this.context = context;
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        TextView time, menu, price;
        ImageView pic;
        Button addBtn;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.time);
            menu = itemView.findViewById(R.id.description);
            price = itemView.findViewById(R.id.price);
            pic = itemView.findViewById(R.id.pic);
            addBtn = itemView.findViewById(R.id.addBtn);
        }
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rowView = LayoutInflater.from(parent.getContext()).inflate(R.layout.rowview_pilih, parent, false);
        return new Viewholder(rowView);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        Menu item = items.get(position);
        holder.time.setText(items.get(position).getTime());
        holder.menu.setText(items.get(position).getMenu());
        holder.price.setText(String.format("Rp %,d", (long) item.price));

        Glide.with(context)
                .load(item.getPic()) // URL gambar dari JSON
                .placeholder(R.drawable.trends) // Placeholder saat loading
                .error(R.drawable.trends) // Gambar error jika gagal memuat
                .into(holder.pic);

        holder.addBtn.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailMakanan.class);
            intent.putExtra("menu", item.menu);
            intent.putExtra("price", item.price);
            intent.putExtra("pic", item.pic);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
