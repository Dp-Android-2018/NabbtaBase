package dp.com.nabbtabase.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

import dp.com.nabbtabase.servise.model.pojo.ServiceHistoryItem;
import dp.com.nabbtabase.servise.repository.ServicesHistoryRepository;

public class ServicesHistoryViewModel extends AndroidViewModel {

    private LiveData<List<ServiceHistoryItem>>servicesHistory;
    public ServicesHistoryViewModel(@NonNull Application application) {
        super(application);
        servicesHistory=ServicesHistoryRepository.getInstance().getServicesHistory(application);
    }

    public LiveData<List<ServiceHistoryItem>> getServicesHistory() {
        return servicesHistory;
    }
}
