package dp.com.nabbtabase.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

public class LoginViewModel extends AndroidViewModel {
    LiveData<String>mail;
    LiveData<String>password;
    public LoginViewModel(@NonNull Application application) {
        super(application);
    }
}
