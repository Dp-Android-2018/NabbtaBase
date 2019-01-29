package dp.com.nabbtabase.servise.model.response;

import com.google.gson.annotations.SerializedName;

import dp.com.nabbtabase.servise.model.pojo.OrderDetailed;

public class OrderDetailedResponse {

    @SerializedName("data")
    private OrderDetailed orderDetailed;

    public OrderDetailed getOrderDetailed() {
        return orderDetailed;
    }

    public void setOrderDetailed(OrderDetailed orderDetailed) {
        this.orderDetailed = orderDetailed;
    }
}
