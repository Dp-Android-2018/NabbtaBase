package dp.com.nabbtabase.servise.repository;

import android.app.Application;
import android.databinding.ObservableField;
import dp.com.nabbtabase.servise.model.request.CheckCodeRequest;
import dp.com.nabbtabase.utils.ConfigurationFile;
import dp.com.nabbtabase.utils.CustomUtils;
import dp.com.nabbtabase.view.callback.CallBackInterface;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CodeRepository {

    private static CodeRepository instance;
    private CallBackInterface callBackInterface;

    public void setCallBackInterface(CallBackInterface callBackInterface) {
        this.callBackInterface = callBackInterface;
    }

    private CodeRepository() {
    }

    public static CodeRepository getInstance() {
        if (instance==null)
        {
            instance=new CodeRepository();
        }
        return instance;
    }

    public void checkCode(Application application, CheckCodeRequest request){
        ObservableField<String> token=new ObservableField<>();
        CustomUtils.getInstance().getEndpoint(application).checkCode(
               request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(checkCodeResponseResponse -> {
                    if (checkCodeResponseResponse.code()==ConfigurationFile.Constants.SUCCESS_CODE){
                        callBackInterface.errorMessage(checkCodeResponseResponse.body().getToken());
                    }
                }, throwable -> {

                });
    }
}
