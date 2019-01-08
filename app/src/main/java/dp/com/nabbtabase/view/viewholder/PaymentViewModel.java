package dp.com.nabbtabase.view.viewholder;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import dp.com.nabbtabase.servise.model.request.CreateOrderRequest;
import dp.com.nabbtabase.servise.repository.CreateOrderRepository;

public class PaymentViewModel extends AndroidViewModel {

    private LiveData<Integer>code;

    public PaymentViewModel(@NonNull Application application, CreateOrderRequest request) {
        super(application);
        System.out.println("enter view Model :"+request.getAddressId());
        code=CreateOrderRepository.getInstance().createOrder(application,request);
    }

    public LiveData<Integer> getCode() {
        return code;
    }
}


