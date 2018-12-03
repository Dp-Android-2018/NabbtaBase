package dp.com.nabbtabase.servise.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
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

    private LoginRepository() { }

    public static LoginRepository getInstance(){
        if(instance==null){
            instance=new LoginRepository();
        }
        return instance;
    }
    public LiveData<LoginRegisterContent> login(Application application, LoginRequest request){
        final MutableLiveData<LoginRegisterContent> data=new MutableLiveData<>();

        CustomUtils.getInstance().getEndpoint(application).login(ConfigurationFile.Constants.API_KEY,
                ConfigurationFile.Constants.CONTENT_TYPE,ConfigurationFile.Constants.CONTENT_TYPE,request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(loginRegisterResponseResponse -> {
                    Log.i("Login cond",""+loginRegisterResponseResponse.code());
                    if (loginRegisterResponseResponse.code()==ConfigurationFile.Constants.SUCCESS_CODE){
                        data.setValue(loginRegisterResponseResponse.body().getLoginRegisterContent());
                    }
                    callBackInterface.updateUi(loginRegisterResponseResponse.code());

                }, throwable -> {

                    Log.e("Login Error",throwable.getMessage());

                });

        return data;
    }

    public void setCallBackInterface(CallBackInterface callBackInterface) {
        this.callBackInterface = callBackInterface;
    }
}
