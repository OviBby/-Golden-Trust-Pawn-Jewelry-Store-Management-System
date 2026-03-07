package lk.ijse.jewelryshop.dto;

public class CustomerTM {
    private String receiptNo;
    private String date;
    private String item;
    private double amount;
    private String status;

    public CustomerTM(String receiptNo, String date, String item, double amount, String status) {
        this.receiptNo = receiptNo;
        this.date = date;
        this.item = item;
        this.amount = amount;
        this.status = status;
    }

    // Getters
    public String getReceiptNo() { return receiptNo; }
    public String getDate() { return date; }
    public String getItem() { return item; }
    public double getAmount() { return amount; }
    public String getStatus() { return status; }
}
