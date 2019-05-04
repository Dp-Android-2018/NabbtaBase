package dp.com.nabbtabase.servise.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import dp.com.nabbtabase.utils.CustomUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SendMailRepository {

    private static SendMailRepository instance;

    private SendMailRepository() {
    }

    public static SendMailRepository getInstance() {
        if(instance==null){
            instance=new SendMailRepository();
        }
        return instance;
    }

    public LiveData<Integer>sendMail(Application application){
        MutableLiveData<Integer>code=new MutableLiveData<>();
        String token="Bearer "+CustomUtils.getInstance().getSaveUserObject(application).getApiToken();
        CustomUtils.getInstance().getEndpoint(application).sendMail(
               token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringResponseResponse ->
                                code.setValue(stringResponseResponse.code())
                        , throwable -> {

                        });
        return code;
    }
}
