package dp.com.nabbtabase.servise.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import org.json.JSONObject;

import dp.com.nabbtabase.servise.model.request.ServiceRequest;
import dp.com.nabbtabase.servise.model.response.StringResponse;
import dp.com.nabbtabase.utils.ConfigurationFile;
import dp.com.nabbtabase.utils.CustomUtils;
import dp.com.nabbtabase.view.callback.CallBackInterface;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class ServiceRequestRepository {

    private static ServiceRequestRepository instance;

    private ServiceRequestRepository() {

    }

    public static ServiceRequestRepository getInstance() {
        if (instance==null){
            instance=new ServiceRequestRepository();
        }
        return instance;
    }

    public LiveData<Integer> requestService(Application application, ServiceRequest request,CallBackInterface callBackInterface){
        MutableLiveData<Integer>code=new MutableLiveData<>();
        String token="Bearer "+CustomUtils.getInstance().getSaveUserObject(application).getApiToken();
        System.out.println("token is : "+token);
        CustomUtils.getInstance().getEndpoint(application).requestService(
               token,request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringResponseResponse -> {
                    System.out.println("REQUEST SERVICE code is : "+stringResponseResponse.code());
                    code.setValue(stringResponseResponse.code());
                     if(stringResponseResponse.code()==ConfigurationFile.Constants.INVALED_DATA_CODE) {
                         JSONObject jObjError = new JSONObject(stringResponseResponse.errorBody().string());
                         String error = (String) jObjError.getJSONArray("errors").get(0);
                         callBackInterface.errorMessage(error);
                     }
                }, throwable -> {
                    System.out.println("REQUEST SERVICE ERROR IS : "+throwable.getMessage());
                });
        CustomUtils.getInstance().cancelDialog();
        return code;
    }

}
