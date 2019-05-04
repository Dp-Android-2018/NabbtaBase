package dp.com.nabbtabase.servise.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;

import java.util.List;

import dp.com.nabbtabase.servise.model.response.ImageResponse;
import dp.com.nabbtabase.utils.CustomUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MultipartBody;
import retrofit2.Response;

public class UploadImageRepository  {

    private static UploadImageRepository instance;

    private UploadImageRepository() {
    }

    public static UploadImageRepository getInstance() {
        if (instance==null){
            instance=new UploadImageRepository();
        }
        return instance;
    }

    public LiveData<List<String>>getImageUrl(Application application, List<MultipartBody.Part> imageToupload){
        /*System.out.println("Images To Upload Size :"+imageToupload.length);
        for (MultipartBody.Part x : imageToupload){
            System.out.println("Images To Upload Detail "+x.body().toString());
        }*/
        MutableLiveData<List<String>>imageUrl=new MutableLiveData<>();
        CustomUtils.getInstance().getEndpoint(application).uploadImage(

                imageToupload)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Response<ImageResponse>>() {
                    @Override
                    public void accept(Response<ImageResponse> imageResponseResponse) throws Exception {
                        imageUrl.setValue(imageResponseResponse.body().getImage());
                        System.out.println("image url is "+new Gson().toJson(imageResponseResponse));

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        System.out.println("Upload Image Password : "+throwable.getMessage());
                    }
                });

        return imageUrl;
    }
}
