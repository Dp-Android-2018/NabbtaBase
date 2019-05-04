package dp.com.nabbtabase.servise.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import android.content.Context;
import android.util.Log;

import dp.com.nabbtabase.servise.model.pojo.LoginRegisterContent;
import dp.com.nabbtabase.servise.model.request.LoginRequest;
import dp.com.nabbtabase.utils.ConfigurationFile;
import dp.com.nabbtabase.utils.CustomUtils;
import dp.com.nabbtabase.view.callback.CallBackInterface;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class LoginRepository {

    private static LoginRepository instance;
    private CallBackInterface callBackInterface;
    private Context context;

    private LoginRepository() {
    }

    public static LoginRepository getInstance() {
        if (instance == null) {
            instance = new LoginRepository();
        }
        return instance;
    }

    public LiveData<LoginRegisterContent> login(Application application, LoginRequest request) {
        final MutableLiveData<LoginRegisterContent> data = new MutableLiveData<>();

        CustomUtils.getInstance().getEndpoint(application).login(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(loginRegisterResponseResponse -> {
                    Log.i("Login cond", "" + loginRegisterResponseResponse.code());
                    if (loginRegisterResponseResponse.code() == ConfigurationFile.Constants.SUCCESS_CODE) {
                        data.setValue(loginRegisterResponseResponse.body().getLoginRegisterContent());
                        CustomUtils.getInstance().saveDataToPrefs(loginRegisterResponseResponse.body().getLoginRegisterContent(), context);
                    }
                    CustomUtils.getInstance().cancelDialog();
                    callBackInterface.updateUi(loginRegisterResponseResponse.code());


                }, throwable -> {
                    CustomUtils.getInstance().cancelDialog();
                });
        return data;
    }

    public void setCallBackInterface(CallBackInterface callBackInterface, Context context) {
        this.callBackInterface = callBackInterface;
        this.context = context;
    }
}
