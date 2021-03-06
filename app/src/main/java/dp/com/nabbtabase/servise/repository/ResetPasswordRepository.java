package dp.com.nabbtabase.servise.repository;

import android.app.Application;

import dp.com.nabbtabase.servise.model.request.ResetPasswordRequest;
import dp.com.nabbtabase.servise.model.response.StringResponse;
import dp.com.nabbtabase.utils.ConfigurationFile;
import dp.com.nabbtabase.utils.CustomUtils;
import dp.com.nabbtabase.view.callback.CallBackInterface;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class ResetPasswordRepository {

    private static ResetPasswordRepository instance;

    private ResetPasswordRepository() {
    }

    public static ResetPasswordRepository getInstance() {
        if(instance==null){
            instance=new ResetPasswordRepository();
        }
        return instance;
    }

    CallBackInterface callBackInterface;

    public void resetPassword(Application application, ResetPasswordRequest resetPasswordRequest){
        CustomUtils.getInstance().getEndpoint(application).resetPassword(

                resetPasswordRequest.getToken(),resetPasswordRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringResponseResponse -> {
                    callBackInterface.updateUi(stringResponseResponse.code());
                }, throwable -> {

                });
    }

    public void setCallBackInterface(CallBackInterface callBackInterface) {
        this.callBackInterface = callBackInterface;
    }
}
