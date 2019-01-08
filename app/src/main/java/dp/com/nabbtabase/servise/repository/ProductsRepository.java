package dp.com.nabbtabase.servise.repository;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;

import dp.com.nabbtabase.servise.model.pojo.Product;
import dp.com.nabbtabase.servise.model.response.Products;
import dp.com.nabbtabase.utils.ConfigurationFile;
import dp.com.nabbtabase.utils.CustomUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class ProductsRepository {

    private static ProductsRepository instance;
    Application application;
    private MutableLiveData<Response<Products>> products;
    ObservableList<Product> productList;

    private ProductsRepository() {
        products = new MutableLiveData<>();
        productList = new ObservableArrayList<>();
    }

    public static ProductsRepository getInstance() {
        if (instance == null) {
            instance = new ProductsRepository();
        }
        return instance;
    }

    public MutableLiveData<Response<Products>> getAllProducts(Application application, String categoryId, String pageid) {
        this.application = application;
        String token="Bearer "+CustomUtils.getInstance().getSaveUserObject(application).getApiToken();
        CustomUtils.getInstance().getEndpoint(application).getProducts(
                ConfigurationFile.Constants.API_KEY,
                ConfigurationFile.Constants.CONTENT_TYPE,
                ConfigurationFile.Constants.CONTENT_TYPE,token,null, categoryId, pageid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(productsResponse -> {
                    if (productsResponse.code() == ConfigurationFile.Constants.SUCCESS_CODE) {
                        products.setValue(productsResponse);
                    }
                });
        return products;
    }

//    public void filter(int id){
//        String categoryId=String.valueOf(id);
//        getAllProducts(application,categoryId,"0");
//    }
}
