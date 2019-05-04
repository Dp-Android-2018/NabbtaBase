package dp.com.nabbtabase.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.annotation.NonNull;

import dp.com.nabbtabase.servise.model.request.ActivatePhoneRequest;
import dp.com.nabbtabase.servise.repository.ActivatePhoneRepository;

public class ActivationViewModel extends AndroidViewModel {

    private LiveData<Integer> code;
    Application application;

    public ActivationViewModel(@NonNull Application application) {
        super(application);
        this.application = application;
    }

    public LiveData<Integer> getCode(ActivatePhoneRequest request) {
        code = ActivatePhoneRepository.getInstance().ActivatePhone(application, request);
        return code;
    }


}
