package dp.com.nabbtabase.servise.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import dp.com.nabbtabase.servise.model.request.CreateOrderRequest;
import dp.com.nabbtabase.servise.model.response.CreateOrderResponse;
import dp.com.nabbtabase.utils.ConfigurationFile;
import dp.com.nabbtabase.utils.CustomUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class CreateOrderRepository {

    private static CreateOrderRepository instance;

    private CreateOrderRepository() {
    }

    public static CreateOrderRepository getInstance() {
        if(instance==null){
            instance=new CreateOrderRepository();
        }
        return instance;
    }

    public LiveData<Integer>createOrder(Application application, CreateOrderRequest request){
        MutableLiveData<Integer>code=new MutableLiveData<>();
        String token="Bearer "+CustomUtils.getInstance().getSaveUserObject(application).getApiToken();
        CustomUtils.getInstance().getEndpoint(application).createOrder(
               token,request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(createOrderResponseResponse -> {
                    System.out.println("Code is : "+createOrderResponseResponse.code());
                    code.setValue(createOrderResponseResponse.code());
                }, throwable -> {

                });
        return code;
    }
}
