package dp.com.nabbtabase.servise.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import dp.com.nabbtabase.servise.model.request.AddToCartRequest;
import dp.com.nabbtabase.servise.model.response.StringResponse;
import dp.com.nabbtabase.utils.ConfigurationFile;
import dp.com.nabbtabase.utils.CustomUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class AddToCartRepository {

    private static AddToCartRepository instance;

    private AddToCartRepository() {
    }

    public static AddToCartRepository getInstance() {
        if(instance==null){
            instance=new AddToCartRepository();
        }
        return instance;
    }

    public LiveData<Response<StringResponse>>addToCart(Application application,String token,int productId){
        MutableLiveData<Response<StringResponse>>response=new MutableLiveData<>();
        CustomUtils.getInstance().getEndpoint(application).addProductToCart(
               token,new AddToCartRequest(productId))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringResponseResponse -> {
                    response.setValue(stringResponseResponse);

                }, throwable -> {

                });
        return response;
    }
}
