package dp.com.nabbtabase.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.view.View;

import dp.com.nabbtabase.application.MyApp;
import dp.com.nabbtabase.servise.model.pojo.CartProduct;
import dp.com.nabbtabase.servise.repository.CartProductsRepository;
import dp.com.nabbtabase.servise.repository.DeleteItemFromCartRepository;
import dp.com.nabbtabase.utils.CustomUtils;
import dp.com.nabbtabase.view.callback.DeleteCartItemListiner;

public class CartListItemViewModel {

    private CartProduct cartProduct;
    Context context;
    public ObservableField<String>quantity;
    public ObservableField<String>total;
    int quantityValue;
    double totalValue;


    public CartListItemViewModel(CartProduct cartProduct,Context context) {
        this.cartProduct=cartProduct;
        this.context=context;
        initVariables();
    }

    public void setCartProduct(CartProduct cartProduct) {
        this.cartProduct = cartProduct;
    }

    public void initVariables(){
        quantity=new ObservableField<>(String.valueOf(cartProduct.getQuantity()));
        total=new ObservableField<>(String.valueOf(cartProduct.getTotal()));
        quantityValue=cartProduct.getQuantity();
        totalValue=cartProduct.getTotal();
    }

    public String getImageUrl(){
        return cartProduct.getProduct().getImageUrls()!=null?
        cartProduct.getProduct().getImageUrls().get(0):"";
    }

    public String getPrice(){
        return String.valueOf(cartProduct.getProduct().getRegularPrice());
    }


    public String getSalePrice(){
        return String.valueOf(cartProduct.getProduct().getSalePrice());
    }

    public String getName(){
        return cartProduct.getProduct().getName();
    }
    public String getCategory(){
        return cartProduct.getProduct().getCategory().getName();
    }

    public void addQuantity(View view){
        quantityValue+=1;
        totalValue+=cartProduct.getProduct().getSalePrice();
        MyApp.setTotal(MyApp.getTotal()+cartProduct.getProduct().getSalePrice());
        updateValus();
    }

    public void minseQuantity(View view){
        if(quantityValue>0) {
            quantityValue -= 1;
            totalValue -= cartProduct.getProduct().getSalePrice();
            MyApp.setTotal(MyApp.getTotal()-cartProduct.getProduct().getSalePrice());
            updateValus();
        }
    }
    public void updateValus(){
        CustomUtils.getInstance().showProgressDialog(context);
        quantity.set(String.valueOf(quantityValue));
        total.set(String.valueOf(totalValue));
        cartProduct.setQuantity(quantityValue);
        cartProduct.setTotal(totalValue);
        CartProductsRepository.getInstance().updateProductCartItem(MyApp.getInstance(),cartProduct.getProduct().getId(),quantityValue);
    }
    public void delete(View view){
        DeleteItemFromCartRepository.getInstance().deleteItemFromCart(MyApp.getInstance(),cartProduct.getProduct().getId(),cartProduct.getTotal());
    }

}

