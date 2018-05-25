package at.fhj.swd.k_uber.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import at.fhj.swd.k_uber.models.StockItemDO;

@Dao
public interface StockItemDAO {
    @Query("SELECT * FROM item")
    List<StockItemDO> getAllUsers();

    @Insert
    void insert(StockItemDO... items);

}
