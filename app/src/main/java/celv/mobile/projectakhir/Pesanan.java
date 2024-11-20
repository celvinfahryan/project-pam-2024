package celv.mobile.projectakhir;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "pesanan")
public class Pesanan {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "menu")
    public String menu;

    @ColumnInfo(name = "sambalLevel")
    public String sambalLevel;

    @ColumnInfo(name = "jumlah")
    public int jumlah;

    @ColumnInfo(name = "totalHarga")
    public double totalHarga;

    @ColumnInfo(name = "catatan")
    public String catatan;
}

