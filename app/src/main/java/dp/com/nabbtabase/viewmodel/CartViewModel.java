package dp.com.nabbtabase.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableDouble;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import java.util.List;

import dp.com.nabbtabase.application.MyApp;
import dp.com.nabbtabase.servise.model.pojo.CartProduct;
import dp.com.nabbtabase.servise.repository.CartProductsRepository;

public class CartViewModel extends AndroidViewModel {

    private MutableLiveData<List<CartProduct>>cartProducts;
    Application application;

    public CartViewModel(@NonNull Application application) {
        super(application);
        this.application=application;
        CartProducts();
    }

    public void CartProducts(){
        cartProducts=CartProductsRepository.getInstance().getCartProducts(application);
    }

    public MutableLiveData<List<CartProduct>> getCartProducts() {
        return cartProducts;
    }


}

