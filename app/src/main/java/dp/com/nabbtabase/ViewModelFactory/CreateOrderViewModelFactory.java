package dp.com.nabbtabase.ViewModelFactory;

import android.app.Application;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.annotation.NonNull;

import dp.com.nabbtabase.servise.model.request.CreateOrderRequest;
import dp.com.nabbtabase.view.viewholder.PaymentViewModel;

public class CreateOrderViewModelFactory implements ViewModelProvider.Factory {

    Application application;
    CreateOrderRequest request;

    public CreateOrderViewModelFactory(Application application, CreateOrderRequest request) {
        this.application = application;
        this.request = request;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new PaymentViewModel(application, request);
    }
}
