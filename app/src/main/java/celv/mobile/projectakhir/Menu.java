package celv.mobile.projectakhir;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "menu")
public class Menu{
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "pic")
    public String pic;
    @ColumnInfo(name = "time")
    public String time;
    @ColumnInfo(name = "shop")
    public String shop;
    @ColumnInfo(name = "menu")
    public String menu;
    @ColumnInfo(name = "price")
    public double price;
}