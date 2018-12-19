package dp.com.nabbtabase.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.view.View;

import dp.com.nabbtabase.servise.model.request.ChangePasswordRequest;
import dp.com.nabbtabase.servise.repository.ChangePasswordRepository;
import dp.com.nabbtabase.utils.ValidationUtils;

public class ChangePasswordViewModel extends AndroidViewModel {
    private final LiveData<Integer>code;
    public ChangePasswordViewModel(@NonNull Application application, ChangePasswordRequest request) {
        super(application);
        code=ChangePasswordRepository.getInstance().changePassword(application,request);
    }

    public LiveData<Integer> getCode() {
        return code;
    }
}
