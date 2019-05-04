package dp.com.nabbtabase.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.annotation.NonNull;

import java.util.List;

import dp.com.nabbtabase.servise.model.pojo.Country;
import dp.com.nabbtabase.servise.model.request.ShippingAddressRequest;
import dp.com.nabbtabase.servise.repository.CountryRepository;
import dp.com.nabbtabase.servise.repository.UpdateAddressRepository;

public class ShippingAddressViewModel extends AndroidViewModel {

    private final LiveData<List<Country>> countries;
    private LiveData<Integer> code;
    private ShippingAddressRequest request;
    private Application application;

    public ShippingAddressViewModel(@NonNull Application application) {
        super(application);
        this.application = application;
        countries = CountryRepository.getInstance().getCountries(application);
    }


    public LiveData<Integer> getCode(ShippingAddressRequest request) {
        code = UpdateAddressRepository.getInstance().updateAddress(application, request);
        return code;
    }

    public LiveData<List<Country>> getCountries() {
        return countries;
    }
}
