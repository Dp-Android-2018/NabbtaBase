package dp.com.nabbtabase.servise.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import dp.com.nabbtabase.servise.model.pojo.Category;
import dp.com.nabbtabase.utils.ConfigurationFile;
import dp.com.nabbtabase.utils.CustomUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CategoriesRepository {

    private static CategoriesRepository instance;

    private CategoriesRepository() {
    }

    public static CategoriesRepository getInstance() {
        if (instance == null) {
            instance = new CategoriesRepository();
        }
        return instance;
    }

    public LiveData<List<Category>> getCategories(Application application) {
        MutableLiveData<List<Category>> categories = new MutableLiveData<>();
        CustomUtils.getInstance().getEndpoint(application).getCategories(
                )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(categoryResponseResponse -> {
                    if (categoryResponseResponse.code() == ConfigurationFile.Constants.SUCCESS_CODE) {
                        categories.postValue(categoryResponseResponse.body().getCategories());
                    }

                }, throwable -> {

                });

        return categories;
    }
}
