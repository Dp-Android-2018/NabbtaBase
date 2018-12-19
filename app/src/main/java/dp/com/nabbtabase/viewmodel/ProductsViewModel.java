package dp.com.nabbtabase.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

import dp.com.nabbtabase.servise.model.pojo.Category;
import dp.com.nabbtabase.servise.model.pojo.Product;
import dp.com.nabbtabase.servise.repository.CategoriesRepository;
import dp.com.nabbtabase.servise.repository.ProductsRepository;

public class ProductsViewModel extends AndroidViewModel {
    private final LiveData<List<Product>> products;
    private final LiveData<List<Category>>categories;

    public ProductsViewModel(@NonNull Application application) {
        super(application);
        products=ProductsRepository.getInstance().getAllProducts(application,null,"0");
        categories=CategoriesRepository.getInstance().getCategories(application);
    }


    public LiveData<List<Product>> getProducts(){
        return products;
    }

    public LiveData<List<Category>> getCategories() {
        return categories;
    }
}
