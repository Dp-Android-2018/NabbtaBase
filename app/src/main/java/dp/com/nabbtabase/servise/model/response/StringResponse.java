package dp.com.nabbtabase.servise.model.response;

import com.google.gson.annotations.SerializedName;

public class StringResponse {
    @SerializedName("message")
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
