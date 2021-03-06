package dp.com.nabbtabase.servise.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import dp.com.nabbtabase.servise.model.pojo.OrderDetailed;
import dp.com.nabbtabase.servise.model.response.OrderDetailedResponse;
import dp.com.nabbtabase.utils.ConfigurationFile;
import dp.com.nabbtabase.utils.CustomUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class OrderDetailedRepository {

    private static OrderDetailedRepository instance;

    private OrderDetailedRepository() {
    }

    public static OrderDetailedRepository getInstance() {
        if(instance==null){
            instance=new OrderDetailedRepository();
        }
        return instance;
    }

    public LiveData<OrderDetailed>getOrder(Application application,int id){
        MutableLiveData<OrderDetailed>order=new MutableLiveData<>();
        String token="Bearer "+CustomUtils.getInstance().getSaveUserObject(application).getApiToken();
        CustomUtils.getInstance().getEndpoint(application).getOrder(
               token,id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(orderDetailedResponseResponse -> {
                    if (orderDetailedResponseResponse.code()==ConfigurationFile.Constants.SUCCESS_CODE){
                        order.setValue(orderDetailedResponseResponse.body().getOrderDetailed());
                    }
                }, throwable -> {

                });
        return order;
    }
}
