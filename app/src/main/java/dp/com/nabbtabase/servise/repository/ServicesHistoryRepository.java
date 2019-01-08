package dp.com.nabbtabase.servise.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import java.util.List;

import dp.com.nabbtabase.servise.model.pojo.ServiceHistoryItem;
import dp.com.nabbtabase.utils.ConfigurationFile;
import dp.com.nabbtabase.utils.CustomUtils;

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
                ConfigurationFile.Constants.API_KEY,
                ConfigurationFile.Constants.CONTENT_TYPE,
                ConfigurationFile.Constants.CONTENT_TYPE,token);
        return servicesHistory;
    }
}
