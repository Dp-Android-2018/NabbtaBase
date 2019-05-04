package dp.com.nabbtabase.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.annotation.NonNull;

import java.util.List;

import dp.com.nabbtabase.servise.model.pojo.Product;
import dp.com.nabbtabase.servise.repository.OfferRepository;

public class OffersViewModel extends AndroidViewModel {

    private final LiveData<List<Product>> offers;

    public OffersViewModel(@NonNull Application application) {
        super(application);
        offers = OfferRepository.getInstance().getOffers(application);
    }


    public LiveData<List<Product>> getOffers() {
        return offers;
    }

}
