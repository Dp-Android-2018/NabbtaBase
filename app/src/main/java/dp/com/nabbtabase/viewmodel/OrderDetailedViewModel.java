package dp.com.nabbtabase.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import dp.com.nabbtabase.servise.model.pojo.OrderDetailed;
import dp.com.nabbtabase.servise.repository.DeleteOrderRepository;
import dp.com.nabbtabase.servise.repository.OrderDetailedRepository;

public class OrderDetailedViewModel extends AndroidViewModel {


   private LiveData<OrderDetailed>order;
   private LiveData<Integer>deleteOrderCode;
   int orderId;
    public OrderDetailedViewModel(@NonNull Application application,int orderId) {
        super(application);
        this.orderId=orderId;
        order=OrderDetailedRepository.getInstance().getOrder(application,orderId);
    }

    public LiveData<OrderDetailed> getOrder() {
        return order;
    }

    public LiveData<Integer> getDeleteOrderCode() {
        deleteOrderCode=DeleteOrderRepository.getInstance().deleteOrder(getApplication(),orderId);
        return deleteOrderCode;
    }
}
