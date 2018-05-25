package at.fhj.swd.k_uber.models;

public class StockItemDO {

    private String itemName;
    private String itemAmount;
    private String itemLable;

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

    public StockItemDO(String itemName, String itemAmount, String itemLable) {
        this.itemName = itemName;
        this.itemAmount = itemAmount;
        this.itemLable = itemLable;
    }
}
