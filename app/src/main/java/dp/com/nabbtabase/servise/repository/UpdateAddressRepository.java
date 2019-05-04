package dp.com.nabbtabase.servise.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import dp.com.nabbtabase.servise.model.request.ShippingAddressRequest;
import dp.com.nabbtabase.utils.CustomUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

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
