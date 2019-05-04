package dp.com.nabbtabase.servise.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import dp.com.nabbtabase.servise.model.request.ShippingAddressRequest;
import dp.com.nabbtabase.servise.model.response.ShippingAddressResponse;
import dp.com.nabbtabase.utils.CustomUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class ShippingAddressRepository {

    private static ShippingAddressRepository instance;

    private ShippingAddressRepository() {
    }

    public static ShippingAddressRepository getInstance() {
        if(instance==null){
            instance=new ShippingAddressRepository();
        }
        return instance;
    }

    public LiveData<Response<ShippingAddressResponse>> shippingAddress(Application application, ShippingAddressRequest request){
        MutableLiveData<Response<ShippingAddressResponse>>response=new MutableLiveData<>();
        String token="Bearer "+CustomUtils.getInstance().getSaveUserObject(application).getApiToken();
        System.out.println("token : "+token);
        CustomUtils.getInstance().getEndpoint(application).shippingAddress(
               token,request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringResponseResponse -> {
                    response.setValue(stringResponseResponse);
                }, throwable -> {

                });
        //System.out.println("code on repository get value "+response.getValue().code());
        return response;
    }
}
