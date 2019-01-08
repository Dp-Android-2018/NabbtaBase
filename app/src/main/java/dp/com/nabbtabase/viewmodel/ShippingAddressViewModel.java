package dp.com.nabbtabase.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import java.util.List;

import dp.com.nabbtabase.servise.model.pojo.Country;
import dp.com.nabbtabase.servise.model.request.ShippingAddressRequest;
import dp.com.nabbtabase.servise.repository.CountryRepository;
import dp.com.nabbtabase.servise.repository.ShippingAddressRepository;

public class ShippingAddressViewModel extends AndroidViewModel {

    private final   LiveData<List<Country>> countries;
    private LiveData<Integer> code;
    private ShippingAddressRequest request;
    private Application application;

    public ShippingAddressViewModel(@NonNull Application application) {
        super(application);
        this.application=application;
        countries=CountryRepository.getInstance().getCountries(application);
    }

    public void setRequest(ShippingAddressRequest request) {
        this.request = request;
        //code=ShippingAddressRepository.getInstance().shippingAddress(application,request);
    }

    public LiveData<Integer> getCode() {
        return code;
    }

    public LiveData<List<Country>> getCountries() {
        return countries;
    }
}
