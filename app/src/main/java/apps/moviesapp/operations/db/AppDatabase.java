package apps.moviesapp.operations.db;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import apps.moviesapp.daos.MoviesDao;
import apps.moviesapp.domin.models.MovieDetailsResponse;
import apps.moviesapp.utils.App;

@Database(entities = {MovieDetailsResponse.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase db;
    private static final Object mutex = new Object();

    public abstract MoviesDao moviesDao();

    public static AppDatabase getInstance() {
        if (db == null) {
            synchronized (mutex) {
                if (db == null) {
                    db = Room.databaseBuilder(App.context, AppDatabase.class, "local.db").build();
                }
            }
        }
        return db;
    }
}
