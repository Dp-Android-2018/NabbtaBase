package dp.com.nabbtabase.servise.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import dp.com.nabbtabase.servise.model.request.ActivatePhoneRequest;
import dp.com.nabbtabase.utils.CustomUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ActivatePhoneRepository {

    private static ActivatePhoneRepository instance;

    private ActivatePhoneRepository() {
    }

    public static ActivatePhoneRepository getInstance() {
        if(instance==null){
            instance=new ActivatePhoneRepository();
        }
        return instance;
    }

    public LiveData<Integer>ActivatePhone(Application application, ActivatePhoneRequest request){
        MutableLiveData<Integer>code=new MutableLiveData<>();
        String token="Bearer "+CustomUtils.getInstance().getSaveUserObject(application).getApiToken();
        CustomUtils.getInstance().getEndpoint(application).
                activatePhone(token,request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringResponseResponse -> {
                    System.out.println("Activate Phone code is : "+stringResponseResponse.code());
                    code.setValue(stringResponseResponse.code());
                }, throwable -> {

                });
        CustomUtils.getInstance().cancelDialog();
        return code;
    }
}
