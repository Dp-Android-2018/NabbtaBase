package dp.com.nabbtabase.servise.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import android.util.Log;

import java.util.List;

import dp.com.nabbtabase.servise.model.pojo.Product;
import dp.com.nabbtabase.utils.ConfigurationFile;
import dp.com.nabbtabase.utils.CustomUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class OfferRepository {
    private static OfferRepository instance;

    private OfferRepository() {
    }

    public static OfferRepository getInstance()
    {
        if (instance==null){
            instance=new OfferRepository();
        }
        return instance ;
    }

    public LiveData<List<Product>>getOffers(Application application){
        final MutableLiveData<List<Product>>offers=new MutableLiveData<>();
        String token=null;
        if(CustomUtils.getInstance().getSaveUserObject(application)!=null){
            token="Bearer "+CustomUtils.getInstance().getSaveUserObject(application).getApiToken();
        }
        CustomUtils.getInstance().getEndpoint(application).getOffers(
               token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(productsResponse -> {
                    Log.i("offers code",""+productsResponse.code());
                    if (productsResponse.code()==ConfigurationFile.Constants.SUCCESS_CODE){
                        offers.setValue(productsResponse.body().getProducts());
                    }
                }, throwable -> {
                    Log.e("offers error",throwable.getMessage());

                });

        return offers;
    }
}
