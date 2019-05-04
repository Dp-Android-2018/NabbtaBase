package dp.com.nabbtabase.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.annotation.NonNull;

import java.util.List;

import dp.com.nabbtabase.servise.model.pojo.CartProduct;
import dp.com.nabbtabase.servise.repository.CartProductsRepository;

public class CartViewModel extends AndroidViewModel {

    private MutableLiveData<List<CartProduct>> cartProducts;
    Application application;

    public CartViewModel(@NonNull Application application) {
        super(application);
        this.application = application;
        CartProducts();
    }

    public void CartProducts() {
        cartProducts = CartProductsRepository.getInstance().getCartProducts(application);
    }

    public MutableLiveData<List<CartProduct>> getCartProducts() {
        return cartProducts;
    }


}

