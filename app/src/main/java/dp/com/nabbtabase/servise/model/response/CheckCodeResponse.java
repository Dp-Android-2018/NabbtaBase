package dp.com.nabbtabase.servise.model.response;

import com.google.gson.annotations.SerializedName;

public class CheckCodeResponse {
    @SerializedName("token")
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
