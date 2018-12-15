package dp.com.nabbtabase.servise.model.request;

import com.google.gson.annotations.SerializedName;

public class ForgetPasswordRequest {

    @SerializedName("login")
    private String login;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
