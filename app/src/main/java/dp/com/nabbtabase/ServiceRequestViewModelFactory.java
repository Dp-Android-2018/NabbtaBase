package dp.com.nabbtabase;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import dp.com.nabbtabase.servise.model.request.ServiceRequest;
import dp.com.nabbtabase.viewmodel.RequestServiceStep2ViewModel;

public class ServiceRequestViewModelFactory implements ViewModelProvider.Factory {

    private Application mApplication;
    private ServiceRequest serviceRequest;

    public ServiceRequestViewModelFactory(Application mApplication, ServiceRequest serviceRequest) {
        this.mApplication = mApplication;
        this.serviceRequest = serviceRequest;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T)new RequestServiceStep2ViewModel(mApplication,serviceRequest);
    }
}
