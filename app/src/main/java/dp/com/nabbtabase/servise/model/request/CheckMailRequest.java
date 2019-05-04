package dp.com.nabbtabase.servise.model.request;

import com.google.gson.annotations.SerializedName;

public class CheckMailRequest {

    @SerializedName("email")
    private String email;

    public CheckMailRequest(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
