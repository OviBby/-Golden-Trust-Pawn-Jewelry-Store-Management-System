package lk.ijse.jewelryshop.entity;

public class RepairDetails {

    private String repairId;
    private String customerName;
    private String repairDate;
    private String status;
    private String submittedDate;
    private String completedDate;
    private double estimatedCost;
    private String repairDescription;
    private double advancepayment;
    private double amount;

    public RepairDetails() {}

    public RepairDetails(String repairId, String customerName, String repairDate, String status, String submittedDate, String completedDate, double estimatedCost, String repairDescription, double advancepayment, double amount) {
        this.repairId = repairId;
        this.customerName = customerName;
        this.repairDate = repairDate;
        this.status = status;
        this.submittedDate = submittedDate;
        this.completedDate = completedDate;
        this.estimatedCost = estimatedCost;
        this.repairDescription = repairDescription;
        this.advancepayment = advancepayment;
        this.amount = amount;
    }


    public RepairDetails(String repairId, String customerName, String repairDescription, String submittedDate, String status, Double estimatedCost) {
        this.repairId = repairId;
        this.customerName = customerName;
        this.submittedDate = submittedDate;
        this.repairDescription = repairDescription;
        this.status = status;
        this.estimatedCost = estimatedCost;
    }



    public RepairDetails(String repairItemId, String customerName, String completedDate, String repairDescription, Double estimatedCost, Double advancePayment, Double amount) {
        this.repairId = repairItemId;
        this.customerName = customerName;
        this.completedDate = completedDate;
        this.repairDescription = repairDescription;
        this.estimatedCost = estimatedCost;
        this.advancepayment = advancePayment;
        this.amount = amount;
    }

    public RepairDetails(String customerName, String repairDescription, String submittedDate, String status, double estimatedCost) {
        this.customerName = customerName;
        this.repairDescription = repairDescription;
        this.submittedDate = submittedDate;
        this.status = status;
        this.estimatedCost = estimatedCost;
    }


    public String getRepairId() { return repairId; }
    public String getCustomerName() { return customerName; }
    public String getRepairDate() { return repairDate; }
    public String getStatus() { return status; }
    public String getSubmittedDate() { return submittedDate; }
    public String getCompletedDate() { return completedDate; }
    public double getEstimatedCost() { return estimatedCost; }
    public String getRepairDescription() { return repairDescription; }
    public double getAdvancepayment() { return advancepayment; }
    public double getAmount() { return amount; }


    public void setRepairId(String repairId) { this.repairId = repairId; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }
    public void setRepairDate(String repairDate) { this.repairDate = repairDate; }
    public void setStatus(String status) { this.status = status; }
    public void setSubmittedDate(String submittedDate) { this.submittedDate = submittedDate; }
    public void setCompletedDate(String completedDate) { this.completedDate = completedDate; }
    public void setEstimatedCost(double estimatedCost) { this.estimatedCost = estimatedCost; }
    public void setRepairDescription(String repairDescription) { this.repairDescription = repairDescription; }
    public void setAdvancepayment(double advancepayment) { this.advancepayment = advancepayment; }
    public void setAmount(double amount) { this.amount = amount; }
}
