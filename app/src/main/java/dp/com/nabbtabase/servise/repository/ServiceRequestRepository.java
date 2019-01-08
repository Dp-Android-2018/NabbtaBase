package dp.com.nabbtabase.servise.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import dp.com.nabbtabase.servise.model.request.ServiceRequest;
import dp.com.nabbtabase.servise.model.response.StringResponse;
import dp.com.nabbtabase.utils.ConfigurationFile;
import dp.com.nabbtabase.utils.CustomUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class ServiceRequestRepository {

    private static ServiceRequestRepository instance;
    private String token;
    private ServiceRequestRepository() {
        token="Bearer ";
    }

    public static ServiceRequestRepository getInstance() {
        if (instance==null){
            instance=new ServiceRequestRepository();
        }
        return instance;
    }

    public LiveData<Integer> requestService(Application application, ServiceRequest request){
        MutableLiveData<Integer>code=new MutableLiveData<>();
        token+=CustomUtils.getInstance().getSaveUserObject(application).getApiToken();
        CustomUtils.getInstance().getEndpoint(application).requestService(
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
