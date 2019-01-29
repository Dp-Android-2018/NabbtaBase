package dp.com.nabbtabase.servise.model.pojo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderDetailed {

    @SerializedName("id")
    private int id;

    @SerializedName("payment_method")
    private PaymentMethod paymentMethod;

    @SerializedName("shipping_cost")
    private double shippingCost;

    @SerializedName("delivery_date")
    private String deliveryDate;

    @SerializedName("date")
    private String date;

    @SerializedName("status_code")
    private int statusCode;

    @SerializedName("total")
    private double total;

    @SerializedName("products")
    private List<ResetProduct>products;

    @SerializedName("code")
    private String code;

    @SerializedName("products_total")
    private double productsTotal;

    public double getProductsTotal() {
        return productsTotal;
    }

    public void setProductsTotal(double productsTotal) {
        this.productsTotal = productsTotal;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public double getShippingCost() {
        return shippingCost;
    }

    public void setShippingCost(double shippingCost) {
        this.shippingCost = shippingCost;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public List<ResetProduct> getProducts() {
        return products;
    }

    public void setProducts(List<ResetProduct> products) {
        this.products = products;
    }
}
