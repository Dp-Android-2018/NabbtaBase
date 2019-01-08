package dp.com.nabbtabase.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import dp.com.nabbtabase.servise.model.request.ServiceRequest;
import dp.com.nabbtabase.servise.repository.ServiceRequestRepository;


public class RequestServiceStep2ViewModel extends AndroidViewModel {

    private final LiveData<Integer> code;
    public RequestServiceStep2ViewModel(@NonNull Application application,ServiceRequest serviceRequest) {
        super(application);
        code=ServiceRequestRepository.getInstance().requestService(application,serviceRequest);
    }

    public LiveData<Integer> getCode() {
        return code;
    }
}
