package model;

import java.util.ArrayList;
import java.util.Date;
import java.io.Serializable;

public class ExportBill implements Serializable {
    private int ID;
    private Date dateIssued;
    private User u;
    private SubAgent s;
    private ArrayList<ExportedItem> ExportItem;

    public ExportBill() {
        super();
        ExportItem = new ArrayList<>();
    }

    public ExportBill(int ID, Date dateIssued, User u, SubAgent s, ArrayList<ExportedItem> exportItem) {
        super();
        this.ID = ID;
        this.dateIssued = dateIssued;
        this.u = u;
        this.s = s;
        ExportItem = exportItem;
    }

    public int getID() {

        return ID;
    }

    public void setID(int ID) {

        this.ID = ID;
    }

    public Date getDateIssued() {

        return dateIssued;
    }

    public void setDateIssued(Date dateIssued) {

        this.dateIssued = dateIssued;
    }

    public User getU() {

        return u;
    }

    public void setU(User u) {

        this.u = u;
    }

    public SubAgent getS() {

        return s;
    }

    public void setS(SubAgent s) {

        this.s = s;
    }

    public ArrayList<ExportedItem> getExportItem() {

        return ExportItem;
    }

    public void setExportItem(ArrayList<ExportedItem> exportItem) {

        ExportItem = exportItem;
    }
}
