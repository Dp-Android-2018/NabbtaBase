package dp.com.nabbtabase.servise.repository;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.databinding.ObservableInt;

import org.json.JSONObject;

import dp.com.nabbtabase.servise.model.request.ForgetPasswordRequest;
import dp.com.nabbtabase.servise.model.response.StringResponse;
import dp.com.nabbtabase.utils.ConfigurationFile;
import dp.com.nabbtabase.utils.CustomUtils;
import dp.com.nabbtabase.view.callback.CallBackInterface;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class ForgetPasswordRepository {

    private static ForgetPasswordRepository instance;
    private CallBackInterface callBackInterface;
    ForgetPasswordRequest request;

    private ForgetPasswordRepository() {
    }

    public static ForgetPasswordRepository getInstance() {
        if(instance==null){
            instance=new ForgetPasswordRepository();
        }
        return instance;
    }

    public void setCallBackInterface(CallBackInterface callBackInterface) {
        this.callBackInterface = callBackInterface;
    }

    public void sendCode(Application application, String  login){

        request=new ForgetPasswordRequest();
        request.setLogin(login);
        CustomUtils.getInstance().getEndpoint(application).forgetPassword(
                ConfigurationFile.Constants.API_KEY,
                ConfigurationFile.Constants.CONTENT_TYPE,
                ConfigurationFile.Constants.CONTENT_TYPE,request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringResponseResponse -> {
                    System.out.println("Forget password code : "+stringResponseResponse.code());
                   if(stringResponseResponse.code()==ConfigurationFile.Constants.SUCCESS_CODE){
                       callBackInterface.updateUi(ConfigurationFile.Constants.MOVE_TO_CODE_ACTIVITY);
                   }else if (stringResponseResponse.code()==ConfigurationFile.Constants.SECONDS_WAIT){
                       JSONObject jObjError = new JSONObject(stringResponseResponse.errorBody().string());
                       String error=String.valueOf(jObjError.getInt("seconds_left"));
                       callBackInterface.errorMessage("try after "+error+" seconds");
                   }else {
                       callBackInterface.updateUi(ConfigurationFile.Constants.INVALED_EMAIL);
                   }
                }, throwable -> {
                    System.out.println("Forget Password Error : "+throwable.getMessage());
                });


    }
}
