package dp.com.nabbtabase.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.annotation.NonNull;

import java.util.List;

import dp.com.nabbtabase.servise.model.pojo.OrderItem;
import dp.com.nabbtabase.servise.repository.OrderHistoryRepository;

public class OrderHistoryViewModel extends AndroidViewModel {

    private LiveData<List<OrderItem>> orderHistory;

    public OrderHistoryViewModel(@NonNull Application application) {
        super(application);
        orderHistory = OrderHistoryRepository.getInstance().getOrders(application);
    }

    public LiveData<List<OrderItem>> getOrderHistory() {
        return orderHistory;
    }
}
