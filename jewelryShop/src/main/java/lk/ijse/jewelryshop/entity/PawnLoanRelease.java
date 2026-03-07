package lk.ijse.jewelryshop.entity;

import java.time.LocalDate;

public class PawnLoanRelease {

    private String releaseId;
    private String receiptId;
    private String customerId;
    private String customerName;
    private String nicNumber;
    private String phoneNumber;
    private String address;
    private String itemName;
    private double totalWeight;
    private double totalAmount;
    private double stampFees;
    private double currentInterest;
    private double netValue;
    private LocalDate releaseDate;


    public PawnLoanRelease() {}


    public PawnLoanRelease(String releaseId, String receiptId, String customerId, String customerName, String nicNumber, String phoneNumber, String address, String itemName, double totalWeight, double totalAmount, double stampFees, double currentInterest, double netValue, LocalDate releaseDate) {

        this.releaseId = releaseId;
        this.receiptId = receiptId;
        this.customerId = customerId;
        this.customerName = customerName;
        this.nicNumber = nicNumber;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.itemName = itemName;
        this.totalWeight = totalWeight;
        this.totalAmount = totalAmount;
        this.stampFees = stampFees;
        this.currentInterest = currentInterest;
        this.netValue = netValue;
        this.releaseDate = releaseDate;
    }

    public PawnLoanRelease(String releaseId, String receiptId, String customerName, String nicNumber, String phoneNumber,
                           String address, String itemName, double totalWeight, double totalAmount, double stampFees,
                           double currentInterest, double netValue, LocalDate releaseDate) {
        this.releaseId = releaseId;
        this.receiptId = receiptId;
        this.customerName = customerName;
        this.nicNumber = nicNumber;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.itemName = itemName;
        this.totalWeight = totalWeight;
        this.totalAmount = totalAmount;
        this.stampFees = stampFees;
        this.currentInterest = currentInterest;
        this.netValue = netValue;
        this.releaseDate = releaseDate;
    }

    public String getReleaseId() {
        return releaseId;
    }

    public void setReleaseId(String releaseId) {
        this.releaseId = releaseId;
    }

    public String getReceiptId() {
        return receiptId;
    }

    public void setReceiptId(String receiptId) {
        this.receiptId = receiptId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getNicNumber() {
        return nicNumber;
    }

    public void setNicNumber(String nicNumber) {
        this.nicNumber = nicNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getTotalWeight() {
        return totalWeight;
    }

    public void setTotalWeight(double totalWeight) {
        this.totalWeight = totalWeight;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public double getStampFees() {
        return stampFees;
    }

    public void setStampFees(double stampFees) {
        this.stampFees = stampFees;
    }

    public double getCurrentInterest() {
        return currentInterest;
    }

    public void setCurrentInterest(double currentInterest) {
        this.currentInterest = currentInterest;
    }

    public double getNetValue() {
        return netValue;
    }

    public void setNetValue(double netValue) {
        this.netValue = netValue;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Override
    public String toString() {
        return " PawnLoanRelease{" +
                "releaseId='" + releaseId + '\'' +
                ", receiptId='" + receiptId + '\'' +
                ", customerId='" + customerId + '\'' +
                ", customerName='" + customerName + '\'' +
                ", nicNumber='" + nicNumber + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", address='" + address + '\'' +
                ", itemName='" + itemName + '\'' +
                ", totalWeight=" + totalWeight +
                ", totalAmount=" + totalAmount +
                ", stampFees=" + stampFees +
                ", currentInterest=" + currentInterest +
                ", netValue=" + netValue +
                ", releaseDate=" + releaseDate +
                '}';
    }
}
