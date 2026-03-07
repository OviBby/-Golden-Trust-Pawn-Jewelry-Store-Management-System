package lk.ijse.jewelryshop.entity;

public class PawnItem {
    private String pawnItemId;
    private String description;
    private double weight;
    private String purity;
    private String currentStatus;
    private double estimatedValue;
    private String acquiredDate;
    private double currentValue;

    public PawnItem() {}

    public PawnItem(String pawnItemId,String description,double weight,String purity,String currentStatus,double estimatedValue,String acquiredDate,double currentValue) {
        this.pawnItemId = pawnItemId;
        this.description = description;
        this.weight = weight;
        this.purity = purity;
        this.currentStatus = currentStatus;
        this.estimatedValue = estimatedValue;
        this.acquiredDate = acquiredDate;
        this.currentValue = currentValue;
    }
    public String getPawnItemId() {
        return pawnItemId;
    }
    public void setPawnItemId(String pawnItemId) {
        this.pawnItemId = pawnItemId;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public double getWeight() {
        return weight;
    }
    public void setWeight(double weight) {
        this.weight = weight;
    }
    public String getPurity() {
        return purity;
    }
    public void setPurity(String purity) {
        this.purity = purity;
    }
    public String getCurrentStatus() {
        return currentStatus;
    }
    public void setCurrentStatus(String currentStatus) {
        this.currentStatus = currentStatus;
    }
    public double getEstimatedValue() {
        return estimatedValue;
    }
    public void setEstimatedValue(double estimatedValue) {
        this.estimatedValue = estimatedValue;
    }
    public String getAcquiredDate() {
        return acquiredDate;
    }
    public void setAcquiredDate(String acquiredDate) {
        this.acquiredDate = acquiredDate;
    }
    public double getCurrentValue() {
        return currentValue;
    }
    public void setCurrentValue(double currentValue) {
        this.currentValue = currentValue;

    }



}
