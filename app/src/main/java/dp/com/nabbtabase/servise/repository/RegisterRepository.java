package dp.com.nabbtabase.servise.repository;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import org.json.JSONObject;

import java.util.List;

import dp.com.nabbtabase.servise.model.request.RegisterRequest;
import dp.com.nabbtabase.utils.ConfigurationFile;
import dp.com.nabbtabase.utils.CustomUtils;
import dp.com.nabbtabase.view.activity.ContainerActivity;
import dp.com.nabbtabase.view.callback.CallBackInterface;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class RegisterRepository  {

    private static RegisterRepository instance;
    private CallBackInterface callBackInterface;
    private Context context;

    private RegisterRepository() { }

    public static RegisterRepository getInstance(){
        if(instance==null){
            instance=new RegisterRepository();
        }
        return instance;
    }

    public void setCallBackInterface(CallBackInterface callBackInterface,Context context) {
        this.callBackInterface = callBackInterface;
        this.context=context;
    }

    public void register(Application application, RegisterRequest request){

        CustomUtils.getInstance().getEndpoint(application).register(
                ConfigurationFile.Constants.API_KEY,
                ConfigurationFile.Constants.CONTENT_TYPE,
                ConfigurationFile.Constants.CONTENT_TYPE,request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(loginRegisterResponseResponse -> {

                    if(loginRegisterResponseResponse.code()==ConfigurationFile.Constants.SUCCESS_CODE_SECOND){
                        CustomUtils.getInstance().saveDataToPrefs(loginRegisterResponseResponse.body().getLoginRegisterContent(),context);

                    }else if(loginRegisterResponseResponse.code()==ConfigurationFile.Constants.INVALED_DATA_CODE){
                        JSONObject jObjError = new JSONObject(loginRegisterResponseResponse.errorBody().string());
                        String error= (String) jObjError.getJSONArray("errors").get(0);
                        callBackInterface.errorMessage(error);
                    }
                    callBackInterface.updateUi(loginRegisterResponseResponse.code());
                }, throwable -> {
                    Log.e("register error",throwable.getMessage());
                });



    }
}
