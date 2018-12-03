package dp.com.nabbtabase.servise.model.pojo;

import com.google.gson.annotations.SerializedName;

import javax.inject.Singleton;

public class Phone {

    @SerializedName("phone")
    private String phone;

    @SerializedName("confirmed")
    private boolean confirmed;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }
}
