package at.fhj.swd.k_uber.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import at.fhj.swd.k_uber.models.StockItemDO;

@Database(entities = {StockItemDO.class}, version = 1)
public abstract class StockItemDataBase extends RoomDatabase {
    public abstract StockItemDAO dao();
}
