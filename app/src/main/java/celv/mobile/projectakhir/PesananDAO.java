package celv.mobile.projectakhir;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface PesananDAO {
    @Insert
    void insert(Pesanan pesanan);

    @Query("SELECT * FROM pesanan")
    List<Pesanan> getAll();
}
