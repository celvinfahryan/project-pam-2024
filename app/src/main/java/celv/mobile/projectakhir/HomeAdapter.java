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
import java.util.ArrayList;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.Viewholder> {
    private final ArrayList<Menu> items;
    private final Context context;

    public HomeAdapter(ArrayList<Menu> items, Context context) {
        this.items = items;
        this.context = context;
    }

    public static class Viewholder extends RecyclerView.ViewHolder {
        TextView time, shop, menu;
        ImageView pic;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.time);
            shop = itemView.findViewById(R.id.shop);
            menu = itemView.findViewById(R.id.menu);
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
        Menu item = items.get(position);
        holder.time.setText(item.time);
        holder.shop.setText(item.shop);
        holder.menu.setText(item.menu);
        int imageResource = context.getResources().getIdentifier(item.pic, "drawable", context.getPackageName());
        holder.pic.setImageResource(imageResource);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, PilihMakanan.class);
            intent.putExtra("menu", item.menu);
            intent.putExtra("time", item.time);
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