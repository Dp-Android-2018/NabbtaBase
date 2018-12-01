package dp.com.nabbtabase.servise.model.response;

import com.google.gson.annotations.SerializedName;

import dp.com.nabbtabase.servise.model.pojo.LoginRegisterContent;

public class LoginRegisterResponse {

    @SerializedName("data")
    private LoginRegisterContent loginRegisterContent;

    public LoginRegisterContent getLoginRegisterContent() {
        return loginRegisterContent;
    }

    public void setLoginRegisterContent(LoginRegisterContent loginRegisterContent) {
        this.loginRegisterContent = loginRegisterContent;
    }
}
