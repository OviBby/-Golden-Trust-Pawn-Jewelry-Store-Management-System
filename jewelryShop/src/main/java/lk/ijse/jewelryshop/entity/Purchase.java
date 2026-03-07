package lk.ijse.jewelryshop.entity;

import java.time.LocalDate;

public class Purchase {

    private String purchaseId;
    private String customerId;
    private String itemId;
    private LocalDate purchaseDate;
    private double purchasePrice;

    // Constructors
    public Purchase () {
    }

    public Purchase (String purchaseId, String customerId, String itemId, LocalDate purchaseDate, double purchasePrice) {
        this.purchaseId = purchaseId;
        this.customerId = customerId;
        this.itemId = itemId;
        this.purchaseDate = purchaseDate;
        this.purchasePrice = purchasePrice;
    }

    // Getters and Setters
    public String getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(String purchaseId) {
        this.purchaseId = purchaseId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public double getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    @Override
    public String toString() {
        return "Purchase {" +
                "purchaseId='" + purchaseId + '\'' +
                ", customerId='" + customerId + '\'' +
                ", itemId='" + itemId + '\'' +
                ", purchaseDate=" + purchaseDate +
                ", purchasePrice=" + purchasePrice +
                '}';
    }
}
