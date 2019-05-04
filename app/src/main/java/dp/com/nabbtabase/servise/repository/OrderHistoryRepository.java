package dp.com.nabbtabase.servise.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import dp.com.nabbtabase.servise.model.pojo.OrderItem;
import dp.com.nabbtabase.utils.ConfigurationFile;
import dp.com.nabbtabase.utils.CustomUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class OrderHistoryRepository {

    private static OrderHistoryRepository instance;

    private OrderHistoryRepository() {
    }

    public static OrderHistoryRepository getInstance() {
        if(instance==null){
            instance=new OrderHistoryRepository();
        }
        return instance;
    }

    public LiveData<List<OrderItem>>getOrders(Application application) {
        MutableLiveData<List<OrderItem>> orderHistory=new MutableLiveData<>();
        String token="Bearer "+CustomUtils.getInstance().getSaveUserObject(application).getApiToken();
        CustomUtils.getInstance().getEndpoint(application).getOrderHistory(
               token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(orderHistoryResponseResponse -> {
                    if(orderHistoryResponseResponse.code()==ConfigurationFile.Constants.SUCCESS_CODE){
                        orderHistory.setValue(orderHistoryResponseResponse.body().getOrderItems());
                    }
                }, throwable -> {

                });
        return orderHistory;

    }
}
