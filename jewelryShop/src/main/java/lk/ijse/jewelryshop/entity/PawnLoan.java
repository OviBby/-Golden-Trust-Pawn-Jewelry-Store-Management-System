package lk.ijse.jewelryshop.entity;

public class PawnLoan {

    private String loanId;
    private String pawnItemId;
    private String customerId;
    private Double loanAmount;
    private Double interestRate;
    private String startDate;
    private String dueDate;
    private String status;
    private String pawnType;
    private String loanMonths;

    // Item Information
    private String itemCode;
    private String itemName;
    private Double weight;
    private String goldCarat;
    private Double pricePerGram;
    private Double totalWeight;

    // Financial Information
    private Double assessedValue;
    private Double stampFee;
    private Double netValue;

    // Customer Information
    private String customerName;
    private String customerNic;
    private String customerPhone;
    private String customerAddress;

    // ==================== Constructors ====================

    // Empty Constructor
    public PawnLoan() {}

    // Full Constructor
    public PawnLoan(String loanId, String pawnItemId, String customerId,
                       Double loanAmount, Double interestRate,
                       String startDate, String dueDate, String status,
                       String pawnType, String itemCode, String itemName,
                       Double weight, String goldCarat, Double pricePerGram,
                       String loanMonths, String customerName, String customerNic,
                       String customerPhone, String customerAddress,
                       Double totalWeight, Double assessedValue,
                       Double stampFee, Double netValue) {
        this.loanId = loanId;
        this.pawnItemId = pawnItemId;
        this.customerId = customerId;
        this.loanAmount = loanAmount;
        this.interestRate = interestRate;
        this.startDate = startDate;
        this.dueDate = dueDate;
        this.status = status;
        this.pawnType = pawnType;
        this.itemCode = itemCode;
        this.itemName = itemName;
        this.weight = weight;
        this.goldCarat = goldCarat;
        this.pricePerGram = pricePerGram;
        this.loanMonths = loanMonths;
        this.customerName = customerName;
        this.customerNic = customerNic;
        this.customerPhone = customerPhone;
        this.customerAddress = customerAddress;
        this.totalWeight = totalWeight;
        this.assessedValue = assessedValue;
        this.stampFee = stampFee;
        this.netValue = netValue;
    }

    // Constructor for Table View (common fields)
    public PawnLoan(String loanId, String customerName, String customerNic,
                       Double loanAmount, String startDate, String dueDate,
                       String status) {
        this.loanId = loanId;
        this.customerName = customerName;
        this.customerNic = customerNic;
        this.loanAmount = loanAmount;
        this.startDate = startDate;
        this.dueDate = dueDate;
        this.status = status;
    }

    // Constructor for Creating New Loan
    public PawnLoan(String loanId, String pawnItemId, String customerId,
                       Double loanAmount, Double interestRate,
                       String startDate, String  dueDate, String status,
                       String loanMonths) {
        this.loanId = loanId;
        this.pawnItemId = pawnItemId;
        this.customerId = customerId;
        this.loanAmount = loanAmount;
        this.interestRate = interestRate;
        this.startDate = startDate;
        this.dueDate = dueDate;
        this.status = status;
        this.loanMonths = loanMonths;
    }

    public PawnLoan(String pawnType, String loanId, String itemName, String startdate, double weight, double pricePerGram, String loanmonth) {
        this.pawnType = pawnType;
        this.loanId = loanId;
        this.itemName =itemName;
        this.startDate = startdate;
        this.weight = weight;
        this.pricePerGram = pricePerGram;
        this.loanMonths = loanmonth;
    }

    public PawnLoan(String pawnType, String loanId, String itemName, String startdate, double weight, double pricePerGram, String goldCarat, String loanMonth) {
        this.pawnType = pawnType;
        this.loanId = loanId;
        this.itemName =itemName;
        this.startDate = startdate;
        this.weight = weight;
        this.pricePerGram = pricePerGram;
        this.goldCarat = goldCarat;
        this.loanMonths = loanMonth;
    }

    public PawnLoan(String pawnType, String itemName, double weight, String goldCarat, double pricePerGram) {
        this.pawnType = pawnType;
        this.itemName = itemName;
        this.weight = weight;
        this.goldCarat = goldCarat ;
        this.pricePerGram = pricePerGram;
    }

    public PawnLoan(String loanId,String customerName,String customerNic,String customerPhone,String customerAddress,double totalWeight,double loanAmount,double stampFee,double interestRate,double netValue) {
        this.loanId = loanId;
        this.customerName = customerName;
        this.customerNic = customerNic;
        this.customerPhone = customerPhone;
        this.customerAddress = customerAddress;
        this.totalWeight = totalWeight;
        this.loanAmount = loanAmount;
        this.stampFee = stampFee;
        this.interestRate = interestRate;
        this.netValue = netValue;
    }

    // ==================== Getters ====================

    public String getLoanId() {
        return loanId;
    }

    public String getPawnItemId() {
        return pawnItemId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public Double getLoanAmount() {
        return loanAmount;
    }

    public Double getInterestRate() {
        return interestRate;
    }

    public String  getStartDate() {
        return startDate;
    }

    public  String  getDueDate() {
        return dueDate;
    }

    public String getStatus() {
        return status;
    }

    public String getPawnType() {
        return pawnType;
    }

    public String getItemCode() {
        return itemCode;
    }

    public String getItemName() {
        return itemName;
    }

    public Double getWeight() {
        return weight;
    }

    public String getGoldCarat() {
        return goldCarat;
    }

    public Double getPricePerGram() {
        return pricePerGram;
    }

    public String getLoanMonths() {
        return loanMonths;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getCustomerNic() {
        return customerNic;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public Double getTotalWeight() {
        return totalWeight;
    }

    public Double getAssessedValue() {
        return assessedValue;
    }

    public Double getStampFee() {
        return stampFee;
    }

    public Double getNetValue() {
        return netValue;
    }

    // ==================== Setters ====================

    public void setLoanId(String loanId) {
        this.loanId = loanId;
    }

    public void setPawnItemId(String pawnItemId) {
        this.pawnItemId = pawnItemId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public void setLoanAmount(Double loanAmount) {
        this.loanAmount = loanAmount;
    }

    public void setInterestRate(Double interestRate) {
        this.interestRate = interestRate;
    }

    public void setStartDate( String  startDate) {
        this.startDate = startDate;
    }

    public void setDueDate( String  dueDate) {
        this.dueDate = dueDate;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setPawnType(String pawnType) {
        this.pawnType = pawnType;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public void setGoldCarat(String goldCarat) {
        this.goldCarat = goldCarat;
    }

    public void setPricePerGram(Double pricePerGram) {
        this.pricePerGram = pricePerGram;
    }

    public void setLoanMonths(String loanMonths) {
        this.loanMonths = loanMonths;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setCustomerNic(String customerNic) {
        this.customerNic = customerNic;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public void setTotalWeight(Double totalWeight) {
        this.totalWeight = totalWeight;
    }

    public void setAssessedValue(Double assessedValue) {
        this.assessedValue = assessedValue;
    }

    public void setStampFee(Double stampFee) {
        this.stampFee = stampFee;
    }

    public void setNetValue(Double netValue) {
        this.netValue = netValue;
    }

    // ==================== toString ====================

    @Override
    public String toString() {
        return "PawnLoan{" +
                "loanId='" + loanId + '\'' +
                ", customerName='" + customerName + '\'' +
                ", loanAmount=" + loanAmount +
                ", status='" + status + '\'' +
                ", startDate=" + startDate +
                ", dueDate=" + dueDate +
                '}';
    }
}
