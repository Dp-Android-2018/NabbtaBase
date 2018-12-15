package dp.com.nabbtabase.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import dp.com.nabbtabase.servise.model.pojo.Product;
import dp.com.nabbtabase.servise.repository.BestSellerRepository;
import dp.com.nabbtabase.servise.repository.NewArrivalProducts;
import dp.com.nabbtabase.servise.repository.OfferRepository;

public class HomeViewModel extends AndroidViewModel {
    private final LiveData<List<Product>>bestSeller;
    private final LiveData<List<Product>>offers;
    private final LiveData<List<Product>> newArrival;

    public HomeViewModel(@NonNull Application application) {
        super(application);
        bestSeller=BestSellerRepository.getInstance().getBestSeller(application);
        offers=OfferRepository.getInstance().getOffers(application);
        newArrival=NewArrivalProducts.getInstance().getNewArrivalsProducts(application);
    }


    public LiveData<List<Product>> getBestSeller() {
        return bestSeller;
    }

    public LiveData<List<Product>> getOffers() {
        return offers;
    }

    public LiveData<List<Product>> getNewArrival() {
        return newArrival;
    }

}
