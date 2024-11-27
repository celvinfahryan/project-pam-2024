package com.example.filkomfood;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter{

    private final Context ctx;
    private final List<History> data;

    public HistoryAdapter(Context ctx, List<History> data){
        this.ctx = ctx;
        this.data=data;
    }

    public class HistoryVH extends RecyclerView.ViewHolder{
        private final ImageView IVprofil;
        private final TextView TvJudul;
        private final TextView TvWaktu;
        private final TextView TvSelesai;
        private final TextView TvMakanan;
        private final TextView TvHarga;
        private final Button btDetail;

        public HistoryVH(@NonNull View itemView) {
            super(itemView);
            this.IVprofil = itemView.findViewById(R.id.IVprofil);
            this.TvJudul = itemView.findViewById(R.id.TvJudul);
            this.TvWaktu = itemView.findViewById(R.id.TvWaktu);
            this.TvSelesai = itemView.findViewById(R.id.TvSelesai);
            this.TvMakanan = itemView.findViewById(R.id.TvMakanan);
            this.TvHarga = itemView.findViewById(R.id.TvHarga);
            this.btDetail = itemView.findViewById(R.id.btdetail);
        }
}
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rowView = LayoutInflater.from(this.ctx)
                .inflate(R.layout.layoutisi, parent, false);
        RecyclerView.ViewHolder vh = new HistoryVH(rowView);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        History h = this.data.get(position);
        HistoryVH vh = (HistoryVH) holder;

        Picasso.get()
                .load(h.getGambar()) // asumsi getProfileUrl mengembalikan URL gambar profil
                .placeholder(R.drawable.makanmakan    ) // Gambar sementara saat loading
                .error(R.drawable.ic_launcher_background)       // Gambar jika terjadi error
                .into(vh.IVprofil);

        vh.TvJudul.setText(h.getJudul());
        vh.TvWaktu.setText(h.getWaktu());
        vh.TvSelesai.setText(h.getSelesai());
        vh.TvMakanan.setText(h.getMakanan());
        vh.TvHarga.setText(h.getHarga());

        ((HistoryVH) holder).btDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent untuk berpindah ke RangkumanHistory.java

                Intent intent = new Intent(ctx, RangkumanHistory.class);
                ctx.startActivity(intent); // Memulai activity
            }
        });
    }

    @Override
    public int getItemCount() {

        return this.data.size();
    }}
