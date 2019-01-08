package dp.com.nabbtabase.servise.model.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import dp.com.nabbtabase.servise.model.pojo.ServiceHistoryItem;

public class ServiceHistoryResponse {

    @SerializedName("data")
    private List<ServiceHistoryItem> serviceHistoryItems;

    public List<ServiceHistoryItem> getServiceHistoryItems() {
        return serviceHistoryItems;
    }

    public void setServiceHistoryItems(List<ServiceHistoryItem> serviceHistoryItems) {
        this.serviceHistoryItems = serviceHistoryItems;
    }
}
