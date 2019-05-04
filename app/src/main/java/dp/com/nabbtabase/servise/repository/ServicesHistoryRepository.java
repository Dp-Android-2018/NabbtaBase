package dp.com.nabbtabase.servise.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import dp.com.nabbtabase.servise.model.pojo.ServiceHistoryItem;
import dp.com.nabbtabase.utils.ConfigurationFile;
import dp.com.nabbtabase.utils.CustomUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ServicesHistoryRepository {

    private static ServicesHistoryRepository instance;

    private ServicesHistoryRepository() {
    }

    public static ServicesHistoryRepository getInstance() {
        if(instance==null){
            instance=new ServicesHistoryRepository();
        }
        return instance;
    }

    public LiveData<List<ServiceHistoryItem>> getServicesHistory(Application application){
        MutableLiveData<List<ServiceHistoryItem>>servicesHistory=new MutableLiveData<>();
        String token="Bearer "+CustomUtils.getInstance().getSaveUserObject(application).getApiToken();
        CustomUtils.getInstance().getEndpoint(application).getServicesHistory(
               token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringResponseResponse -> {
                    System.out.println("services history code :"+stringResponseResponse.code());
                    if(stringResponseResponse.code()==ConfigurationFile.Constants.SUCCESS_CODE){
                        servicesHistory.setValue(stringResponseResponse.body().getServiceHistoryItems());
                    }
                }, throwable -> {

                });

        return servicesHistory;
    }
}
