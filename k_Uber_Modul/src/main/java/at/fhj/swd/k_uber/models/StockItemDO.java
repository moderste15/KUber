package at.fhj.swd.k_uber.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "item")
public class StockItemDO {

    @PrimaryKey(autoGenerate = true) private int id;

    @ColumnInfo(name="name")
    private String itemName;
    @ColumnInfo(name="amount")
    private String itemAmount;
    @ColumnInfo(name="lable")
    private String itemLable;
    @ColumnInfo(name = "is_bought")
    private boolean isBought;

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemAmount() {
        return itemAmount;
    }

    public void setItemAmount(String itemAmount) {
        this.itemAmount = itemAmount;
    }

    public String getItemLable() {
        return itemLable;
    }

    public void setItemLable(String itemLable) {
        this.itemLable = itemLable;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isBought() {
        return isBought;
    }

    public void setBought(boolean bought) {
        isBought = bought;
    }

    public StockItemDO(String itemName, String itemAmount, String itemLable, boolean isBought) {
        this.itemName = itemName;
        this.itemAmount = itemAmount;
        this.itemLable = itemLable;
        this.isBought = isBought;
    }
}
