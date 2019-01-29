package dp.com.nabbtabase.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import dp.com.nabbtabase.servise.model.request.ServiceRequest;
import dp.com.nabbtabase.servise.repository.ServiceRequestRepository;
import dp.com.nabbtabase.view.callback.CallBackInterface;


public class RequestServiceStep2ViewModel extends AndroidViewModel {

    private  LiveData<Integer> code;
    Application application;
    //ServiceRequest serviceRequest;
    CallBackInterface callBackInterface;
    public RequestServiceStep2ViewModel(@NonNull Application application, ServiceRequest serviceRequest, CallBackInterface callBackInterface) {
        super(application);
        this.application=application;
        //this.serviceRequest=serviceRequest;
        this.callBackInterface=callBackInterface;
    }


    public LiveData<Integer> getCode(ServiceRequest serviceRequest) {
        code=ServiceRequestRepository.getInstance().requestService(application,serviceRequest,callBackInterface);
        return code;
    }
}
