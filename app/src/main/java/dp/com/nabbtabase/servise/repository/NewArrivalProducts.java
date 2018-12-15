package dp.com.nabbtabase.servise.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import java.util.List;

import dp.com.nabbtabase.servise.model.pojo.Product;
import dp.com.nabbtabase.servise.model.response.Products;
import dp.com.nabbtabase.utils.ConfigurationFile;
import dp.com.nabbtabase.utils.CustomUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class NewArrivalProducts {
    private static NewArrivalProducts instance;

    private NewArrivalProducts() {
    }

    public static NewArrivalProducts getInstance() {
        if (instance==null){
            instance=new NewArrivalProducts();
        }
        return instance;
    }

    public LiveData<List<Product>>getNewArrivalsProducts(Application application){
        final MutableLiveData<List<Product>>newArrivalProducts=new MutableLiveData<>();

        CustomUtils.getInstance().getEndpoint(application).getProducts(
                ConfigurationFile.Constants.API_KEY,
                ConfigurationFile.Constants.CONTENT_TYPE,
                ConfigurationFile.Constants.CONTENT_TYPE,"-time",null)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(productsResponse -> {
                    if (productsResponse.code()==ConfigurationFile.Constants.SUCCESS_CODE){
                        newArrivalProducts.setValue(productsResponse.body().getProducts());
                    }

                }, throwable -> {

                });
        return newArrivalProducts;
    }
}
