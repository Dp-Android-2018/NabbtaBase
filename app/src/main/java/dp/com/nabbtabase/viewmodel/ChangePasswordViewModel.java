package dp.com.nabbtabase.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.annotation.NonNull;

import dp.com.nabbtabase.servise.model.request.ChangePasswordRequest;
import dp.com.nabbtabase.servise.repository.ChangePasswordRepository;

public class ChangePasswordViewModel extends AndroidViewModel {
    private final LiveData<Integer> code;

    public ChangePasswordViewModel(@NonNull Application application, ChangePasswordRequest request) {
        super(application);
        code = ChangePasswordRepository.getInstance().changePassword(application, request);
    }

    public LiveData<Integer> getCode() {
        return code;
    }
}
