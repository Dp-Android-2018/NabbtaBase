package dp.com.nabbtabase.servise.model.request;

import com.google.gson.annotations.SerializedName;

public class UpdateCartItemRequest {

    @SerializedName("quantity")
    private int quantity;

    public UpdateCartItemRequest(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
