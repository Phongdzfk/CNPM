package model;
import java.io.Serializable;

public class SubAgent implements Serializable {
    private int ID;
    private String brandName;
    private String Address;
    private String phoneNumber;

    public SubAgent() {
        super();
    }

    public SubAgent(int ID, String brandName, String address, String phoneNumber) {
        super();
        this.ID = ID;
        this.brandName = brandName;
        Address = address;
        this.phoneNumber = phoneNumber;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
