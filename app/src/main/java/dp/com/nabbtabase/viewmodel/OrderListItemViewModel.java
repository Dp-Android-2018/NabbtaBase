package dp.com.nabbtabase.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import dp.com.nabbtabase.servise.model.pojo.OrderItem;
import dp.com.nabbtabase.utils.ConfigurationFile;
import dp.com.nabbtabase.view.activity.OrderDetailedActivity;

public class OrderListItemViewModel {

    private OrderItem orderItem;
    Context context;

    public OrderListItemViewModel(OrderItem orderItem,Context context) {
        this.orderItem = orderItem;
        this.context=context;
    }

    public void setOrderItem(OrderItem orderItem) {
        this.orderItem = orderItem;
    }

    public String getCode() {
        return orderItem.getCode();
    }

    public String getDate() {
        return orderItem.getDate();
    }

    public String getStatus() {
        switch (orderItem.getStatusCode()) {
            case 0: {
                return "Order Placed";
            }
            case 1: {
                return "Confirmed";
            }
            case 2: {
                return "Pending";
            }
            case 3: {
                return "On the way";
            }
            case 4: {
                return "Delivered";
            }
            case 5: {
                return "Returned";
            }
            default: {
                return "no status";
            }
        }
    }

    public void onItemClick(View view){
        Intent intent=new Intent(context,OrderDetailedActivity.class);
        intent.putExtra(ConfigurationFile.IntentConstants.ORDER_ID,orderItem.getId());
        context.startActivity(intent);
    }

}
