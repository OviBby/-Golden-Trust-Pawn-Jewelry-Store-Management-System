/*
package lk.ijse.jewelryshop.dto;

public class RepairItemDTO {
    private String repairItemId;
    private String currentCondition;
    private String metalType;
    private double weight;
    private String description;
    private String receivedDate;
    private double advancePayment;
    private String customerName;
    private String phoneNumber;


    public RepairItemDTO(String repairItemId, String currentCondition, String metalType, double weightGrams, String description, String receivedDate, double advancePayment, String customerName, String phone) {
        this.repairItemId = repairItemId;
        this.currentCondition = currentCondition;
        this.metalType = metalType;
        this.weight = weightGrams;
        this.description = description;
        this.receivedDate = receivedDate;
        this.advancePayment = advancePayment;
        this.customerName = customerName;
        this.phoneNumber = phone;
    }

    public RepairItemDTO(String repairItemId, String customerName, String receivedDate, String metalType, String description, String weightGrams) {
        this.repairItemId = repairItemId;
        this.customerName = customerName;
        this.receivedDate = receivedDate;
        this.metalType = metalType;

        this.description = description;
        this.weight = Double.parseDouble(weightGrams);

    }


    public String getRepairItemId() { return repairItemId; }
    public String getCurrentCondition() { return currentCondition; }
    public String getMetalType() { return metalType; }
    public double getWeight() { return weight; }
    public String getDescription() { return description; }
    public String getReceivedDate() { return receivedDate; }
    public double getAdvancePayment() { return advancePayment; }
    public String getCustomerName() { return customerName; }
    public String getPhoneNumber() { return phoneNumber; }


    public void setRepairItemId(String repairItemId) {
        this.repairItemId = repairItemId;
    }
    public  void setCurrentCondition(String currentCondition) { this.currentCondition = currentCondition; }
    public void setMetalType(String metalType) { this.metalType = metalType; }
    public void setWeight(double weight) { this.weight = weight; }
    public void setDescription(String description) { this.description = description; }
    public void setReceivedDate(String receivedDate) { this.receivedDate = receivedDate; }
    public void setAdvancePayment(double advancePayment) { this.advancePayment = advancePayment; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

}*/
package lk.ijse.jewelryshop.dto;

public class RepairItemDTO {
    private String repairItemId;
    private String currentCondition;
    private String metalType;
    private double weight;
    private String description;
    private String receivedDate;
    private double advancePayment;
    private String customerName;
    private String phoneNumber;

    // Main Constructor (9 parameters)
    public RepairItemDTO(String repairItemId, String currentCondition, String metalType,
                         double weight, String description, String receivedDate,
                         double advancePayment, String customerName, String phoneNumber) {
        this.repairItemId = repairItemId;
        this.currentCondition = currentCondition;
        this.metalType = metalType;
        this.weight = weight;
        this.description = description;
        this.receivedDate = receivedDate;
        this.advancePayment = advancePayment;
        this.customerName = customerName;
        this.phoneNumber = phoneNumber;
    }


    public RepairItemDTO(String repairItemId, String customerName, String receivedDate,
                         String metalType, String description, String weight) {
        this.repairItemId = repairItemId;
        this.customerName = customerName;
        this.receivedDate = receivedDate;
        this.metalType = metalType;
        this.description = description;
        this.weight = Double.parseDouble(weight);


    }
    public RepairItemDTO(String repairItemId, String customerName, String description) {
        this.repairItemId = repairItemId;
        this.customerName = customerName;
        this.description = description;
    }
    public RepairItemDTO(String repairItemId, String customerName, String description,double advancePayment) {
        this.repairItemId = repairItemId;
        this.customerName = customerName;
        this.description = description;
        this.advancePayment = advancePayment;
    }
    public RepairItemDTO(String repairItemId,String receivedDate,String customerName, String phoneNumber,  String metalType,String currentCondition, String description, double weight, double advancePayment) {
       this.repairItemId = repairItemId;
       this.receivedDate = receivedDate;
       this.customerName = customerName;
       this.phoneNumber = phoneNumber;
       this.metalType = metalType;
       this.currentCondition = currentCondition;
       this.description = description;
       this.weight = weight;
       this.advancePayment = advancePayment;
    }

    public String getRepairItemId() {
        return repairItemId;
    }

    public void setRepairItemId(String repairItemId) {
        this.repairItemId = repairItemId;
    }

    public String getCurrentCondition() {
        return currentCondition;
    }

    public void setCurrentCondition(String currentCondition) {
        this.currentCondition = currentCondition;
    }

    public String getMetalType() {
        return metalType;
    }

    public void setMetalType(String metalType) {
        this.metalType = metalType;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReceivedDate() {
        return receivedDate;
    }

    public void setReceivedDate(String receivedDate) {
        this.receivedDate = receivedDate;
    }

    public double getAdvancePayment() {
        return advancePayment;
    }

    public void setAdvancePayment(double advancePayment) {
        this.advancePayment = advancePayment;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "RepairItemDTO{" +
                "repairItemId='" + repairItemId + '\'' +
                ", currentCondition='" + currentCondition + '\'' +
                ", metalType='" + metalType + '\'' +
                ", weight=" + weight +
                ", description='" + description + '\'' +
                ", receivedDate='" + receivedDate + '\'' +
                ", advancePayment=" + advancePayment +
                ", customerName='" + customerName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}