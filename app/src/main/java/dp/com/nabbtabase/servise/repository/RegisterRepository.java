package dp.com.nabbtabase.servise.repository;

import android.app.Application;

import dp.com.nabbtabase.servise.model.request.RegisterRequest;
import dp.com.nabbtabase.servise.model.response.LoginRegisterResponse;
import dp.com.nabbtabase.utils.ConfigurationFile;
import dp.com.nabbtabase.utils.CustomUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class RegisterRepository  {

    private static RegisterRepository instance;

    private RegisterRepository() { }

    public static RegisterRepository getInstance(){
        if(instance==null){
            instance=new RegisterRepository();
        }
        return instance;
    }

    public void register(Application application, RegisterRequest request){

        CustomUtils.getInstance().getEndpoint(application).register(
                ConfigurationFile.Constants.API_KEY,
                ConfigurationFile.Constants.CONTENT_TYPE,
                ConfigurationFile.Constants.CONTENT_TYPE,request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(loginRegisterResponseResponse -> {

                }, throwable -> {

                });
    }
}
