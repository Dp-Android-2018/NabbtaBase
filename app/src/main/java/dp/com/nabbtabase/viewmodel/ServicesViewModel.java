package dp.com.nabbtabase.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.annotation.NonNull;

import java.util.List;

import dp.com.nabbtabase.servise.model.pojo.ServiceContent;
import dp.com.nabbtabase.servise.repository.ServicesRepository;

public class ServicesViewModel extends AndroidViewModel {
    private final LiveData<List<ServiceContent>> services;

    public ServicesViewModel(@NonNull Application application) {
        super(application);
        services = ServicesRepository.getInstance().getServices(application);
    }

    public LiveData<List<ServiceContent>> getServices() {
        return services;
    }
}
