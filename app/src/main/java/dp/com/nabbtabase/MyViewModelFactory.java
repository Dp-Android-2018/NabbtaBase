package dp.com.nabbtabase;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import dp.com.nabbtabase.servise.model.pojo.Product;
import dp.com.nabbtabase.servise.model.request.ResetPasswordRequest;
import dp.com.nabbtabase.viewmodel.ProductDetailedViewModel;

public class MyViewModelFactory implements ViewModelProvider.Factory {
    private Application mApplication;
    private Product mParam;
    private ResetPasswordRequest resetPasswordRequest;


    public MyViewModelFactory(Application application, Product param) {
        this.mApplication = application;
        this.mParam = param;
    }


    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new ProductDetailedViewModel(mApplication, mParam);
    }
}
