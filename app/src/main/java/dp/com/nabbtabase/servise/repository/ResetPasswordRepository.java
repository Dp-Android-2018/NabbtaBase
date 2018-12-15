package dp.com.nabbtabase.servise.repository;

import android.app.Application;

import dp.com.nabbtabase.servise.model.request.ResetPasswordRequest;
import dp.com.nabbtabase.servise.model.response.StringResponse;
import dp.com.nabbtabase.utils.ConfigurationFile;
import dp.com.nabbtabase.utils.CustomUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class ResetPasswordRepository {

    private static ResetPasswordRepository instance;

    private ResetPasswordRepository() {
    }

    public static ResetPasswordRepository getInstance() {
        return instance;
    }

    public void resetPassword(Application application, ResetPasswordRequest resetPasswordRequest){
        CustomUtils.getInstance().getEndpoint(application).resetPassword(
                ConfigurationFile.Constants.API_KEY,
                ConfigurationFile.Constants.CONTENT_TYPE,
                ConfigurationFile.Constants.CONTENT_TYPE,
                resetPasswordRequest.getToken(),resetPasswordRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringResponseResponse -> {

                }, throwable -> {

                });
    }
}
