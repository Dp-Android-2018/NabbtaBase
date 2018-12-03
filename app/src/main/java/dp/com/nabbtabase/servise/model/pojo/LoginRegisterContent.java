package dp.com.nabbtabase.servise.model.pojo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LoginRegisterContent {

    @SerializedName("name")
    private String name;

    @SerializedName("email")
    private String email;

    @SerializedName("activated")
    private String activated;

    @SerializedName("api_token")
    private String api_token;

    @SerializedName("device_token")
    private String device_token;

    @SerializedName("phones")
    private List<Phone> phones;

    @SerializedName("address")
    private Address address;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getActivated() {
        return activated;
    }

    public void setActivated(String activated) {
        this.activated = activated;
    }

    public String getApi_token() {
        return api_token;
    }

    public void setApi_token(String api_token) {
        this.api_token = api_token;
    }

    public String getDevice_token() {
        return device_token;
    }

    public void setDevice_token(String device_token) {
        this.device_token = device_token;
    }

    public List<Phone> getPhones() {
        return phones;
    }

    public void setPhones(List<Phone> phones) {
        this.phones = phones;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
