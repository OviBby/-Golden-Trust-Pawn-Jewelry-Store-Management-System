package lk.ijse.jewelryshop.entity;

public class Item {
    private String itemId;
    private String description;
    private String metalType;
    private double weightGrams;
    private String purity;
    private double price;
    private String addedDate;
    private String currentStockStatus;
    private int qty;
    private double unitPrice;



    public Item(String itemId, String description, String metalType, double weightGrams, String purity, double price, String addedDate, String currentStockStatus, int qty, double unitPrice) {
        this.itemId = itemId;
        this.description = description;
        this.metalType = metalType;
        this.weightGrams = weightGrams;
        this.purity = purity;
        this.price = price;
        this.addedDate = addedDate;
        this.currentStockStatus = currentStockStatus;
        this.qty = qty;
        this.unitPrice = unitPrice;
    }

    public Item(String description, int qty, double weightGrams, String purity, double unitPrice) {
        this.description = description;
        this.qty = qty;
        this.weightGrams = Double.parseDouble(String.valueOf(weightGrams));
        this.purity = purity;
        this.unitPrice = Double.parseDouble(String.valueOf(unitPrice));
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMetalType() {
        return metalType;
    }

    public void setMetalType(String metalType) {
        this.metalType = metalType;
    }

    public double getWeightGrams() {
        return weightGrams;
    }

    public void setWeightGrams(double weightGrams) {
        this.weightGrams = weightGrams;
    }

    public String getPurity() {
        return purity;
    }

    public void setPurity(String purity) {
        this.purity = purity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(String addedDate) {
        this.addedDate = addedDate;
    }

    public String getCurrentStockStatus() {
        return currentStockStatus;
    }

    public void setCurrentStockStatus(String currentStockStatus) {
        this.currentStockStatus = currentStockStatus;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }


}
