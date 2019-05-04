package dp.com.nabbtabase.servise.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import android.util.Log;

import java.util.List;

import dp.com.nabbtabase.servise.model.pojo.Comment;
import dp.com.nabbtabase.utils.ConfigurationFile;
import dp.com.nabbtabase.utils.CustomUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ProductCommentsRepository {

    private static ProductCommentsRepository instance;

    private ProductCommentsRepository() {
    }

    public static ProductCommentsRepository getInstance() {
        if (instance==null){
            instance=new ProductCommentsRepository();
        }
        return instance;
    }

    public LiveData<List<Comment>>getComments(Application application,int id){
        MutableLiveData<List<Comment>>comments=new MutableLiveData<>();

        CustomUtils.getInstance().getEndpoint(application).getProductComments(
               id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(productCommentsResponseResponse -> {
                    Log.i("Comments code",""+productCommentsResponseResponse.code());
                    if(productCommentsResponseResponse.code()==ConfigurationFile.Constants.SUCCESS_CODE){
                        comments.setValue(productCommentsResponseResponse.body().getComments());
                    }

                }, throwable -> {
                    Log.e("Commens Error",throwable.getMessage());
                });

        return comments;
    }
}
