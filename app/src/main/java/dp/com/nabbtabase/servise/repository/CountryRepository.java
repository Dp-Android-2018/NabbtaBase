package dp.com.nabbtabase.servise.repository;


import android.app.Application;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import dp.com.nabbtabase.servise.model.pojo.Country;
import dp.com.nabbtabase.utils.ConfigurationFile;
import dp.com.nabbtabase.utils.CustomUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

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
                )
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
