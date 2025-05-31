package model;
import java.io.Serializable;

public class Item implements Serializable{
    private int ID;
    private String itemName;
    private String Description;
    private double unitPrice;
    private int stockQuantity;

    public Item() {
        super();
    }

    public Item(int ID, String itemName, String description, int stockquantity) {
        super();
        this.ID = ID;
        this.itemName = itemName;
        Description = description;
        stockQuantity = stockquantity;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }
}
