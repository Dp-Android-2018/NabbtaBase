package dp.com.nabbtabase.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

import dp.com.nabbtabase.servise.model.pojo.Category;
import dp.com.nabbtabase.servise.model.response.Products;
import dp.com.nabbtabase.servise.repository.CategoriesRepository;
import dp.com.nabbtabase.servise.repository.ProductsRepository;
import retrofit2.Response;

public class ProductsViewModel extends AndroidViewModel {
    private LiveData<Response<Products>> products;
    private final LiveData<List<Category>> categories;
    Application application;
    public ProductsViewModel(@NonNull Application application) {
        super(application);
        this.application = application;
        getProducts( null, "0");
        categories = CategoriesRepository.getInstance().getCategories(application);
    }

    public void getProducts(String categoryId, String pageId) {
        products = ProductsRepository.getInstance().getAllProducts(application, categoryId, pageId);
    }

    public LiveData<Response<Products>> getProducts() {
        return products;
    }

    public LiveData<List<Category>> getCategories() {
        return categories;
    }
}
