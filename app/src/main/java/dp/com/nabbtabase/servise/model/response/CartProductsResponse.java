package dp.com.nabbtabase.servise.model.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import dp.com.nabbtabase.servise.model.pojo.CartProduct;

public class CartProductsResponse {
    @SerializedName("data")
    private List<CartProduct>cartProducts;

    public List<CartProduct> getCartProducts() {
        return cartProducts;
    }

    public void setCartProducts(List<CartProduct> cartProducts) {
        this.cartProducts = cartProducts;
    }
}
