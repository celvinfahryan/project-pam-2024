package celv.mobile.projectakhir;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Pesanan.class}, version = 1, exportSchema = false)
public abstract class PesananDatabase extends RoomDatabase {
    private static PesananDatabase INSTANCE;

    public abstract PesananDAO pesananDAO();

    public static synchronized PesananDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            PesananDatabase.class,
                            "pesanan-db")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }
}
