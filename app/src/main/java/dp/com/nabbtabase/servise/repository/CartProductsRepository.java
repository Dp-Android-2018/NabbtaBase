package dp.com.nabbtabase.servise.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableInt;

import java.util.List;
import java.util.Observable;
import java.util.concurrent.atomic.AtomicInteger;

import dp.com.nabbtabase.servise.model.pojo.CartProduct;
import dp.com.nabbtabase.servise.model.request.UpdateCartItemRequest;
import dp.com.nabbtabase.utils.ConfigurationFile;
import dp.com.nabbtabase.utils.CustomUtils;
import dp.com.nabbtabase.view.callback.UpdateCartItemInterFace;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CartProductsRepository {
    private static CartProductsRepository instance;
    String token;
    UpdateCartItemInterFace cartItemInterFace;

    private CartProductsRepository() {
    }

    public static CartProductsRepository getInstance() {
        if(instance==null){
            instance=new CartProductsRepository();
        }
        return instance;
    }

    public MutableLiveData<List<CartProduct>>getCartProducts(Application application){
        MutableLiveData<List<CartProduct>>cartProducts=new MutableLiveData<>();
        token="Bearer "+CustomUtils.getInstance().getSaveUserObject(application).getApiToken();
        CustomUtils.getInstance().getEndpoint(application).getCartProducts(
                ConfigurationFile.Constants.API_KEY,
                ConfigurationFile.Constants.CONTENT_TYPE,
                ConfigurationFile.Constants.CONTENT_TYPE,token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(cartProductsResponseResponse -> {
                    if(cartProductsResponseResponse.code()==ConfigurationFile.Constants.SUCCESS_CODE) {
                        System.out.println("Card Products Size :"+cartProductsResponseResponse.body().getCartProducts().size());
                        cartProducts.setValue(cartProductsResponseResponse.body().getCartProducts());
                    }
                }, throwable -> {

                });
        return cartProducts;

    }

    public void updateProductCartItem(Application application, int id, int quantity){
        CustomUtils.getInstance().getEndpoint(application).updateCartItem(
                ConfigurationFile.Constants.API_KEY,
                ConfigurationFile.Constants.CONTENT_TYPE,
                ConfigurationFile.Constants.CONTENT_TYPE,token,id,new UpdateCartItemRequest(quantity))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringResponseResponse -> {
                    cartItemInterFace.itemUpdated(stringResponseResponse.code());

                }, throwable -> {

                });
    }

    public void setCartItemInterFace(UpdateCartItemInterFace cartItemInterFace) {
        this.cartItemInterFace = cartItemInterFace;
    }
}
