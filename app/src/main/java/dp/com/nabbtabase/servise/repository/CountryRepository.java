package dp.com.nabbtabase.servise.repository;


import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import java.util.List;

import dp.com.nabbtabase.servise.model.pojo.Country;
import dp.com.nabbtabase.servise.model.response.CountryResponse;
import dp.com.nabbtabase.utils.ConfigurationFile;
import dp.com.nabbtabase.utils.CustomUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class CountryRepository {


    private static CountryRepository instance;

    private CountryRepository(){}

    public static CountryRepository getInstance() {
        if (instance==null){
            instance=new CountryRepository();
        }
        return instance;
    }

    public LiveData<List<Country>> getCountries(Application application){
        final MutableLiveData<List<Country>> countries=new MutableLiveData<>();
        CustomUtils.getInstance().getEndpoint(application).getCountries(
                ConfigurationFile.Constants.API_KEY,
                ConfigurationFile.Constants.CONTENT_TYPE,
                ConfigurationFile.Constants.CONTENT_TYPE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(countryResponseResponse -> {
                    if (countryResponseResponse.code()==ConfigurationFile.Constants.SUCCESS_CODE){
                        countries.setValue(countryResponseResponse.body().getCountries());
                    }
                }, throwable -> {

                });

        return countries;
    }
}
