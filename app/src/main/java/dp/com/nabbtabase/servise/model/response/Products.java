package dp.com.nabbtabase.servise.model.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import dp.com.nabbtabase.servise.model.pojo.Product;

public class Products {

    @SerializedName("data")
    List<Product>products;

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
