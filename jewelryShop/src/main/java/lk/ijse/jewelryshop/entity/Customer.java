package lk.ijse.jewelryshop.entity;

public class Customer {

    private String customerId;
    private String firstName;
    private String lastName;
    private String address;
    private String nicNumber;
    private String phoneNumber;  // Changed to String
    private String email;

    public Customer(String customerId, String firstName, String lastName,
                       String address, String nicNumber, String phoneNumber, String email) {
        this.customerId = customerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.nicNumber = nicNumber;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public  Customer(String firstName, String lastName,String phoneNumber, String address){
        this.firstName = firstName;
        this.lastName = lastName;
        this.nicNumber = nicNumber;
        this.phoneNumber = phoneNumber;
        this.address = address;

    }

    public  Customer(String firstName, String lastName, String nicNumber, String phoneNumber, String address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.nicNumber = nicNumber;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}
