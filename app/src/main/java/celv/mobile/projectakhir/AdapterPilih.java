package celv.mobile.projectakhir;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

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
            menu = itemView.findViewById(R.id.menu);
            price = itemView.findViewById(R.id.price);
            pic = itemView.findViewById(R.id.pic);
            addBtn = itemView.findViewById(R.id.addBtn);
        }
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rowView = LayoutInflater.from(parent.getContext()).inflate(R.layout.rowview_pilih, parent, false);
        context = parent.getContext();
        return new Viewholder(rowView);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        Menu item = items.get(position);
        holder.time.setText(items.get(position).getTime());
        holder.menu.setText(items.get(position).getMenu());
        holder.price.setText(String.valueOf(item.getPrice()));

        int imageResource = this.context.getResources().getIdentifier(item.pic , "drawable", this.context.getPackageName());
        holder.pic.setImageResource(imageResource);
        holder.pic.setImageResource(imageResource);

//        holder.addBtn.setOnClickListener(view -> {
//            Intent intent = new Intent(context, DetailMakanan.class);
//            intent.putExtra("title", item.getTitle());
//            intent.putExtra("time", item.getTime());
//            intent.putExtra("price", item.getPrice());
//            intent.putExtra("picAddress", item.getPicAddress());
//            context.startActivity(intent);
//        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
