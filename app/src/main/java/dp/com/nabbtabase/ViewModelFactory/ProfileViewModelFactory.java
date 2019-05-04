package dp.com.nabbtabase.ViewModelFactory;

import android.app.Application;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.annotation.NonNull;

import dp.com.nabbtabase.servise.model.request.EditProfileRequest;
import dp.com.nabbtabase.viewmodel.ProfileViewModel;

public class ProfileViewModelFactory implements ViewModelProvider.Factory {
    private Application mApplication;
    private EditProfileRequest mRequest;

    public ProfileViewModelFactory(Application mApplication, EditProfileRequest mRequest) {
        this.mApplication = mApplication;
        this.mRequest = mRequest;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new ProfileViewModel(mApplication, mRequest);
    }
}
