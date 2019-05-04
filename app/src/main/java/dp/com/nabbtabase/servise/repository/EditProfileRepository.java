package dp.com.nabbtabase.servise.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.databinding.ObservableInt;

import dp.com.nabbtabase.servise.model.pojo.LoginRegisterContent;
import dp.com.nabbtabase.servise.model.request.EditProfileRequest;
import dp.com.nabbtabase.utils.ConfigurationFile;
import dp.com.nabbtabase.utils.CustomUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

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
