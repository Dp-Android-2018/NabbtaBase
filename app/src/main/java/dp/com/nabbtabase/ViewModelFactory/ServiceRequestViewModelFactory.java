package dp.com.nabbtabase.ViewModelFactory;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;
import android.telecom.Call;

import dp.com.nabbtabase.servise.model.request.ServiceRequest;
import dp.com.nabbtabase.view.callback.CallBackInterface;
import dp.com.nabbtabase.viewmodel.RequestServiceStep2ViewModel;

public class ServiceRequestViewModelFactory implements ViewModelProvider.Factory {

    private Application mApplication;
    private ServiceRequest serviceRequest;
    private CallBackInterface callBackInterface;

    public ServiceRequestViewModelFactory(Application mApplication, ServiceRequest serviceRequest, CallBackInterface callBackInterface) {
        this.mApplication = mApplication;
        this.serviceRequest = serviceRequest;
        this.callBackInterface = callBackInterface;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T)new RequestServiceStep2ViewModel(mApplication,serviceRequest,callBackInterface);
    }
}
