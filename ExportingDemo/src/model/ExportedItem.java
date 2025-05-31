package model;
import java.io.Serializable;

public class ExportedItem implements Serializable{
    private int ID;
    private int Quantity;
    private double unitPrice;
    private double totalPrice;
    private Item i;

    public ExportedItem() {
        super();
    }

    public ExportedItem(int ID, int quantity, double unitPrice, Item i) {
        super();
        this.ID = ID;
        this.Quantity = quantity;
        this.unitPrice = unitPrice;
        this.i = i;
        this.totalPrice = (double)unitPrice * (double)quantity;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Item getI() {
        return i;
    }

    public void setI(Item i) {
        this.i = i;
    }
}
