package dp.com.nabbtabase.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.annotation.NonNull;
import android.view.View;

import dp.com.nabbtabase.servise.repository.ForgetPasswordRepository;

public class CodeViewModel extends AndroidViewModel {
    private String login;
    public Application application;

    public CodeViewModel(@NonNull Application application) {
        super(application);
        this.application = application;
    }

    public void setLogin(String login) {
        this.login = login;
    }


    public void resend(View view) {
        ForgetPasswordRepository.getInstance().sendCode(application, login);
    }


}
