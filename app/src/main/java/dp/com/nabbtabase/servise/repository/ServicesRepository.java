package dp.com.nabbtabase.servise.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import dp.com.nabbtabase.servise.model.pojo.ServiceContent;
import dp.com.nabbtabase.utils.ConfigurationFile;
import dp.com.nabbtabase.utils.CustomUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ServicesRepository {

    private static ServicesRepository instance;

    private ServicesRepository() {
    }

    public static ServicesRepository getInstance() {
        if(instance==null){
            instance=new ServicesRepository();
        }
        return instance;
    }

    public LiveData<List<ServiceContent>>getServices(Application application){
        MutableLiveData<List<ServiceContent>>services=new MutableLiveData<>();
        CustomUtils.getInstance().getEndpoint(application).getServices(
                )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(servicesResponseResponse -> {
                    if(servicesResponseResponse.code()==ConfigurationFile.Constants.SUCCESS_CODE) {
                        services.setValue(servicesResponseResponse.body().getServices());
                    }
                }, throwable -> {

                });

        return services;
    }
}
