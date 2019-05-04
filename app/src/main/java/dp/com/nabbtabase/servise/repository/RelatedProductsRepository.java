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

public class RelatedProductsRepository {

    private static RelatedProductsRepository instance;

    private RelatedProductsRepository() {
    }

    public static RelatedProductsRepository getInstance() {
        if(instance==null){
            instance=new RelatedProductsRepository();
        }
        return instance;
    }

    public LiveData<List<Product>>getRelatedProduct(Application application,int catId){
        MutableLiveData<List<Product>>relatedProducts=new MutableLiveData<>();
        String token=null;
        if(CustomUtils.getInstance().getSaveUserObject(application)!=null){
            token="Bearer "+CustomUtils.getInstance().getSaveUserObject(application).getApiToken();
        }
        CustomUtils.getInstance().getEndpoint(application).getProducts(
               token,
                "-rating",String.valueOf(catId),"0")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(productsResponse -> {
                    if(productsResponse.code()==ConfigurationFile.Constants.SUCCESS_CODE){
                        relatedProducts.setValue(productsResponse.body().getProducts());
                    }
                }, throwable -> {

                });
        return relatedProducts;
    }
}
