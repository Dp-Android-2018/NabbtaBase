package dp.com.nabbtabase.servise.model.response;

import com.google.gson.annotations.SerializedName;

public class ShippingAddressResponse {

    @SerializedName("id")
    private int id;

    @SerializedName("message")
    private String message;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
