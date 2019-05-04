package dp.com.nabbtabase.ViewModelFactory;

import android.app.Application;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.annotation.NonNull;

import dp.com.nabbtabase.servise.model.request.ChangePasswordRequest;
import dp.com.nabbtabase.viewmodel.ChangePasswordViewModel;

public class ChangePasswordViewModelFactory implements ViewModelProvider.Factory {
    private Application mApplication;
    private ChangePasswordRequest changePasswordRequest;

    public ChangePasswordViewModelFactory(Application mApplication, ChangePasswordRequest changePasswordRequest) {
        this.mApplication = mApplication;
        this.changePasswordRequest = changePasswordRequest;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new ChangePasswordViewModel(mApplication, changePasswordRequest);
    }
}
