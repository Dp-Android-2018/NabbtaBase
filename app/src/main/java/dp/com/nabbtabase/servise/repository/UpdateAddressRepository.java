package dp.com.nabbtabase.servise.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import dp.com.nabbtabase.servise.model.request.ShippingAddressRequest;
import dp.com.nabbtabase.servise.model.response.StringResponse;
import dp.com.nabbtabase.utils.ConfigurationFile;
import dp.com.nabbtabase.utils.CustomUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class UpdateAddressRepository {

    private static UpdateAddressRepository instance;

    private UpdateAddressRepository() {
    }

    public static UpdateAddressRepository getInstance() {
        if(instance==null){
            instance=new UpdateAddressRepository();
        }
        return instance;
    }

    public LiveData<Integer>updateAddress(Application application, ShippingAddressRequest request){
        MutableLiveData<Integer>code=new MutableLiveData<>();
        String token="Bearer "+CustomUtils.getInstance().getSaveUserObject(application).getApiToken();
        int id=CustomUtils.getInstance().getSaveUserObject(application).getAddress().getId();
        CustomUtils.getInstance().getEndpoint(application).updateAddress(
               token,id,request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringResponseResponse -> {
                    code.setValue(stringResponseResponse.code());
                }, throwable -> {

                });
        return code;
    }
}
