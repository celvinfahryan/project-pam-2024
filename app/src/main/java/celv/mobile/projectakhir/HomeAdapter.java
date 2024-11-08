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
    ArrayList<Menu> items;
    Context context;

    public HomeAdapter(ArrayList<Menu> items, Context context) {
        this.items = items;
        this.context = context;
    }

    public class Viewholder extends RecyclerView.ViewHolder {
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
        holder.time.setText(items.get(position).getTime());
        holder.shop.setText(items.get(position).getShop());
        holder.menu.setText(items.get(position).getMenu());

        int imageResource = this.context.getResources().getIdentifier(item.pic , "drawable", this.context.getPackageName());
        holder.pic.setImageResource(imageResource);
        holder.pic.setImageResource(imageResource);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, PilihMakanan.class);
            intent.putExtra("menu", item.getMenu());
            intent.putExtra("time", item.getTime());
            intent.putExtra("price", item.getPrice());
            intent.putExtra("pic", item.getPic());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
