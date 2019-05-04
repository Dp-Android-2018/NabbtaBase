package dp.com.nabbtabase.servise.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import dp.com.nabbtabase.utils.CustomUtils;
import dp.com.nabbtabase.view.callback.DeleteCartItemListiner;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class DeleteItemFromCartRepository {
    private static DeleteItemFromCartRepository instance;
    DeleteCartItemListiner deleteCartItemListiner;

    private DeleteItemFromCartRepository() {
    }

    public static DeleteItemFromCartRepository getInstance() {
        if(instance==null){
            instance=new DeleteItemFromCartRepository();
        }
        return instance;
    }

    public LiveData<Integer>deleteItemFromCart(Application application,int id,double total){
        MutableLiveData<Integer>code=new MutableLiveData<>();
        String token="Bearer "+CustomUtils.getInstance().getSaveUserObject(application).getApiToken();
        CustomUtils.getInstance().getEndpoint(application).deleteItemFromCart(
               token,id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringResResponse -> {
                    System.out.println("Code is : "+stringResResponse.code());
                    code.setValue(stringResResponse.code());
                    System.out.println("call listenier : "+code);
                    deleteCartItemListiner.itemDeleted(code , id,total);
                }, throwable -> {
                    System.out.println("Error is : "+throwable.getMessage());
                });
        return code;
    }

    public void setDeleteCartItemListiner(DeleteCartItemListiner deleteCartItemListiner) {
        this.deleteCartItemListiner = deleteCartItemListiner;
    }
}
