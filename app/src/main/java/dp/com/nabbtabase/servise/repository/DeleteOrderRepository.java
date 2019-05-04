package dp.com.nabbtabase.servise.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import dp.com.nabbtabase.utils.CustomUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class DeleteOrderRepository {

    private static DeleteOrderRepository instance;

    private DeleteOrderRepository() {
    }

    public static DeleteOrderRepository getInstance() {
        if(instance==null){
            instance=new DeleteOrderRepository();
        }
        return instance;
    }

    public LiveData<Integer>deleteOrder(Application application,int id){
        MutableLiveData<Integer>code=new MutableLiveData<>();
        String token="Bearer "+CustomUtils.getInstance().getSaveUserObject(application).getApiToken();
        CustomUtils.getInstance().getEndpoint(application).deleteOrder(
               token,id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringResponseResponse -> {
                    code.setValue(stringResponseResponse.code());
                }, throwable -> {

                });
        return code;
    }
}
