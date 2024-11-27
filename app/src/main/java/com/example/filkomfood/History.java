package com.example.filkomfood;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


public class History{
    public int id;
    private String gambar ;
    private String judul ;
    private String waktu ;
    private String selesai ;
    private String makanan;
    private String harga;

    public String getGambar() {
        return gambar;
    }

    public String getJudul() {
        return judul;
    }

    public String getWaktu() {
        return waktu;
    }

    public String getSelesai() {
        return selesai;
    }

    public String getMakanan() {
        return makanan;
    }

    public String getHarga() {
        return harga;
    }

    public History() {
    }


}
