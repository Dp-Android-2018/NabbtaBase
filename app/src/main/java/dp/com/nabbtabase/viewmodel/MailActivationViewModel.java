package dp.com.nabbtabase.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.annotation.NonNull;

import dp.com.nabbtabase.servise.repository.SendMailRepository;

public class MailActivationViewModel extends AndroidViewModel {

    private LiveData<Integer> code;
    Application application;

    public MailActivationViewModel(@NonNull Application application) {
        super(application);
        this.application = application;
    }


    public LiveData<Integer> getCode() {
        code = SendMailRepository.getInstance().sendMail(application);
        return code;
    }
}
