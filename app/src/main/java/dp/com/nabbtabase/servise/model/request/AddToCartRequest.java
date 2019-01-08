package dp.com.nabbtabase.servise.model.request;

import com.google.gson.annotations.SerializedName;

public class AddToCartRequest {

    @SerializedName("product_id")
    private int productId;

    public AddToCartRequest(int productId) {
        this.productId = productId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }
}
