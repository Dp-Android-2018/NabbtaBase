package dp.com.nabbtabase.servise.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableInt;

import java.nio.file.Path;

import dp.com.nabbtabase.servise.model.pojo.LoginRegisterContent;
import dp.com.nabbtabase.servise.model.request.EditProfileRequest;
import dp.com.nabbtabase.servise.model.response.StringResponse;
import dp.com.nabbtabase.utils.ConfigurationFile;
import dp.com.nabbtabase.utils.CustomUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class EditProfileRepository {

    private static EditProfileRepository instance;
    private LoginRegisterContent data;

    private EditProfileRepository() {
    }

    public static EditProfileRepository getInstance() {
        if(instance==null){
            instance=new EditProfileRepository();
        }
        return instance;
    }

    public LiveData<Integer> editProfile(Application application, EditProfileRequest request){
        data=CustomUtils.getInstance().getSaveUserObject(application);
        MutableLiveData<Integer>code=new MutableLiveData<>();
        CustomUtils.getInstance().getEndpoint(application).editProfile(

                "Bearer "+data.getApiToken(),request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringResponseResponse -> {
                    if(stringResponseResponse.code()==ConfigurationFile.Constants.SUCCESS_CODE){
                        data.setLastName(request.getLastName());
                        data.setFirstName(request.getFirstName());
                        CustomUtils.getInstance().saveDataToPrefs(data,application);
                    }
                    code.setValue(stringResponseResponse.code());
                }, throwable -> {

                });
        return code;
    }
}
