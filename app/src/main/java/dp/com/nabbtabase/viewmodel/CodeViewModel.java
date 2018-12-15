package dp.com.nabbtabase.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import dp.com.nabbtabase.servise.model.request.CheckCodeRequest;
import dp.com.nabbtabase.servise.model.response.CheckCodeResponse;
import dp.com.nabbtabase.utils.ConfigurationFile;
import dp.com.nabbtabase.utils.CustomUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class CodeViewModel extends AndroidViewModel {
    private String login;
    public Application application;
    public CodeViewModel(@NonNull Application application) {
        super(application);
        this.application=application;
    }

    public void setLogin(String login) {
        this.login = login;
    }

}
