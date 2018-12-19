package dp.com.nabbtabase.servise.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import dp.com.nabbtabase.servise.model.request.ChangePasswordRequest;
import dp.com.nabbtabase.servise.model.response.StringResponse;
import dp.com.nabbtabase.utils.ConfigurationFile;
import dp.com.nabbtabase.utils.CustomUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class ChangePasswordRepository {

    private static ChangePasswordRepository instance;

    private ChangePasswordRepository() {
    }

    public static ChangePasswordRepository getInstance() {
        if (instance==null){
            instance=new ChangePasswordRepository();
        }
        return instance;
    }

    public LiveData<Integer>changePassword(Application application, ChangePasswordRequest request){
        MutableLiveData<Integer>code=new MutableLiveData<>();
        String token="Bearer "+CustomUtils.getInstance().getSaveUserObject(application).getApiToken();
        CustomUtils.getInstance().getEndpoint(application).changePassword(
                ConfigurationFile.Constants.API_KEY,
                ConfigurationFile.Constants.CONTENT_TYPE,
                ConfigurationFile.Constants.CONTENT_TYPE,token,request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringResponseResponse -> {
                    code.setValue(stringResponseResponse.code());
                }, throwable -> {

                });

        return code;
    }
}
