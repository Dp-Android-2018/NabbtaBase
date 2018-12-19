package dp.com.nabbtabase.servise.model.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import dp.com.nabbtabase.servise.model.pojo.ServiceContent;

public class ServicesResponse {

    @SerializedName("data")
    private List<ServiceContent> services;

    public List<ServiceContent> getServices() {
        return services;
    }

    public void setServices(List<ServiceContent> services) {
        this.services = services;
    }
}
