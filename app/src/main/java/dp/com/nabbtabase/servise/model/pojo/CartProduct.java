package dp.com.nabbtabase.servise.model.pojo;

import com.google.gson.annotations.SerializedName;

public class CartProduct {

    @SerializedName("id")
    private int id;

    @SerializedName("quantity")
    private int quantity;

    @SerializedName("product")
    private Product product;

    @SerializedName("total")
    private double total;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
