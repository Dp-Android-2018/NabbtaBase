package dp.com.nabbtabase.servise.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.AbsListView;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

import dp.com.nabbtabase.servise.model.pojo.Product;
import dp.com.nabbtabase.servise.model.response.Products;
import dp.com.nabbtabase.utils.ConfigurationFile;
import dp.com.nabbtabase.utils.CustomUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class ProductsRepository {

    private static ProductsRepository instance;
    private String next=null;
    private String pageId="0";
    private boolean loading=false;
    Application application;
    private MutableLiveData<List<Product>>products;
    ObservableList<Product> productList;

    private ProductsRepository() {
        products=new MutableLiveData<>();
        productList=new ObservableArrayList<>();
    }

    public static ProductsRepository getInstance() {
        if(instance==null){
            instance=new ProductsRepository();
        }
        return instance;
    }

    public MutableLiveData<List<Product>> getAllProducts(Application application,String categoryId,String pageid) {
        this.application=application;
        loading=true;
        System.out.println("category id  : "+categoryId);
        System.out.println("pageid : "+pageid);
        CustomUtils.getInstance().getEndpoint(application).getProducts(
                ConfigurationFile.Constants.API_KEY,
                ConfigurationFile.Constants.CONTENT_TYPE,
                ConfigurationFile.Constants.CONTENT_TYPE,null,categoryId,pageid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(productsResponse -> {
                    if(productsResponse.code()==ConfigurationFile.Constants.SUCCESS_CODE) {
                        products.setValue(productsResponse.body().getProducts());
//                        if (pageid == "0") {
//                            products.getValue().clear();
//                            products.setValue(productsResponse.body().getProducts());
//                        }else {
//                            products.getValue().addAll(productsResponse.body().getProducts());
//                            products.notify();
//                        }
                        next = productsResponse.body().getLinks().getNext();
                        if (next != null) {
                            pageId = next.substring(next.length() - 1);
                        }
                    }
                    loading=false;
                }, throwable -> {

                });
        return products;
    }

    public RecyclerView.OnScrollListener onScrollListener(){
        return new RecyclerView.OnScrollListener(){
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (((LinearLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition()==products.getValue().size()-1) {
                    if (next != null && loading == false) {
                        // loading = true;
                        System.out.println("Load More Data Success ");
                        getAllProducts(application,null,pageId);
                    }
                }
            }
        };
    }

    public void filter(int id){
        String categoryId=String.valueOf(id);
        getAllProducts(application,categoryId,"0");
    }
}
