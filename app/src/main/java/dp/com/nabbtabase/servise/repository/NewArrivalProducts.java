package dp.com.nabbtabase.servise.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import dp.com.nabbtabase.servise.model.pojo.Product;
import dp.com.nabbtabase.utils.ConfigurationFile;
import dp.com.nabbtabase.utils.CustomUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class NewArrivalProducts {
    private static NewArrivalProducts instance;
    String token;
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
        if(CustomUtils.getInstance().getSaveUserObject(application)!=null){
            token="Bearer "+CustomUtils.getInstance().getSaveUserObject(application).getApiToken();
        }
        CustomUtils.getInstance().getEndpoint(application).getProducts(
               token,"-time",null,"0")
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
