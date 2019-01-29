package dp.com.nabbtabase.ViewModelFactory;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import dp.com.nabbtabase.viewmodel.OrderDetailedViewModel;

public class OrderDeatiledViewModelFactory implements ViewModelProvider.Factory{

    Application application;
    int orderId;

    public OrderDeatiledViewModelFactory(Application application, int orderId) {
        this.application = application;
        this.orderId = orderId;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T)new OrderDetailedViewModel(application,orderId);
    }
}
