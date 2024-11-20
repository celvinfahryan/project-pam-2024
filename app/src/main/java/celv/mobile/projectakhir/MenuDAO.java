package celv.mobile.projectakhir;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MenuDAO {
    @Query("SELECT * FROM menu")
    List<Menu> getAllMenu();

    @Query("SELECT * FROM menu WHERE id = :id")
    Menu getById(int id);

    @Query("SELECT * FROM menu WHERE shop LIKE :shop LIMIT 1")
    Menu getByShop(String shop);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Menu... menus);

    @Query("DELETE FROM menu")
    void deleteAll();
}