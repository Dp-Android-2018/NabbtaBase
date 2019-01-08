package dp.com.nabbtabase.viewmodel;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;
import android.support.annotation.NonNull;

import dp.com.nabbtabase.view.callback.CallBackInterface;

public class CreateShippingAddressFactory implements ViewModelProvider.Factory {
    Application application;
    Context context;
    CallBackInterface callBackInterface;

    public CreateShippingAddressFactory(Application application, Context context,CallBackInterface callBackInterface) {
        this.application = application;
        this.context = context;
        this.callBackInterface=callBackInterface;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T)new CreateShippingAddressViewModel(application,context,callBackInterface);
    }
}
