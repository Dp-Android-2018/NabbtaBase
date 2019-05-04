package dp.com.nabbtabase.servise.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import dp.com.nabbtabase.servise.model.request.CreateOrderRequest;
import dp.com.nabbtabase.utils.CustomUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

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
