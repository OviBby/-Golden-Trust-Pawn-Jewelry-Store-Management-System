package lk.ijse.jewelryshop.dto;

public class OrderDTO {
    private String orderId;
    private String item_id;
    private String description;
    private String metalType;
    private double weight;
    private int qty;
    private double uprice;
    private double price;
    private String date;
    private String customername;
    private String nic;


    public OrderDTO(String orderId, String item_id, String description,
                    double weight, int qty, double uprice, double price) {
        this.orderId = orderId;
        this.item_id = item_id;
        this.description = description;
        this.weight = weight;
        this.qty = qty;
        this.uprice = uprice;
        this.price = price;
    }


    public OrderDTO(String item_id, String description, double weight,
                    int qty, double uprice, double price) {
        this.item_id = item_id;
        this.description = description;
        this.weight = weight;
        this.qty = qty;
        this.uprice = uprice;
        this.price = price;
    }


    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
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

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getUprice() {
        return uprice;
    }

    public void setUprice(double uprice) {
        this.uprice = uprice;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "OrderDTO{" +
                "orderId='" + orderId + '\'' +
                ", item_id='" + item_id + '\'' +
                ", description='" + description + '\'' +
                ", weight=" + weight +
                ", qty=" + qty +
                ", uprice=" + uprice +
                ", price=" + price +
                '}';
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCustomername() {
        return customername;
    }

    public void setCustomername(String customername) {
        this.customername = customername;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }
}