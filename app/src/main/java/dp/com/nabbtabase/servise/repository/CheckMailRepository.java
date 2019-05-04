package dp.com.nabbtabase.servise.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import dp.com.nabbtabase.servise.model.request.CheckMailRequest;
import dp.com.nabbtabase.servise.model.response.CheckMailResponse;
import dp.com.nabbtabase.utils.ConfigurationFile;
import dp.com.nabbtabase.utils.CustomUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CheckMailRepository {

    private static CheckMailRepository instance;

    private CheckMailRepository() {
    }

    public static CheckMailRepository getInstance() {
        if(instance==null){
            instance=new CheckMailRepository();
        }
        return instance;
    }

    public LiveData<CheckMailResponse> existMail(Application application, CheckMailRequest request){
        MutableLiveData<CheckMailResponse>response=new MutableLiveData<>();
        CustomUtils.getInstance().getEndpoint(application).checkExistMail(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(checkMailResponseResponse -> {
                    if(checkMailResponseResponse.code()==ConfigurationFile.Constants.SUCCESS_CODE) {
                        response.setValue(checkMailResponseResponse.body());
                    }
                });
        return response;
    }
}
