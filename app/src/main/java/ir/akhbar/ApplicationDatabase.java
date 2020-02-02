package ir.akhbar;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(version = 1, entities = {NewsTable.class})
public abstract class ApplicationDatabase extends RoomDatabase {

    abstract NewsDao getNewsDao();
}
