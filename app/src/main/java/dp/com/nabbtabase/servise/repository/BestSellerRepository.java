package dp.com.nabbtabase.servise.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;
import java.util.List;
import dp.com.nabbtabase.servise.model.pojo.Product;
import dp.com.nabbtabase.utils.ConfigurationFile;
import dp.com.nabbtabase.utils.CustomUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class BestSellerRepository {
    private static BestSellerRepository instance;

    private BestSellerRepository() {
    }

    public static BestSellerRepository getInstance() {
        if (instance==null){
            instance=new BestSellerRepository();
        }
        return instance;
    }

    public LiveData<List<Product>>getBestSeller(Application application){
        final MutableLiveData<List<Product>>bestSellerProducts=new MutableLiveData<>();
        CustomUtils.getInstance().getEndpoint(application).getBesetSeller(
                ConfigurationFile.Constants.API_KEY,
                ConfigurationFile.Constants.CONTENT_TYPE,
                ConfigurationFile.Constants.CONTENT_TYPE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(productsResponse -> {
                    Log.i("bestseller code",""+productsResponse.code());
                    if (productsResponse.code()==ConfigurationFile.Constants.SUCCESS_CODE){
                        bestSellerProducts.setValue(productsResponse.body().getProducts());
                    }

                }, throwable -> {
                    Log.e("bestseller Error",throwable.getMessage());
                });
        return bestSellerProducts;
    }
}
