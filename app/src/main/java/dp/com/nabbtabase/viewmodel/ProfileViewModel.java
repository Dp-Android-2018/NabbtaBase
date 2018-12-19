package dp.com.nabbtabase.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.view.View;

import dp.com.nabbtabase.servise.model.pojo.LoginRegisterContent;
import dp.com.nabbtabase.servise.model.request.EditProfileRequest;
import dp.com.nabbtabase.servise.model.response.StringResponse;
import dp.com.nabbtabase.servise.repository.EditProfileRepository;
import dp.com.nabbtabase.utils.ConfigurationFile;
import dp.com.nabbtabase.utils.CustomUtils;
import dp.com.nabbtabase.utils.ValidationUtils;
import dp.com.nabbtabase.view.callback.CallBackInterface;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class ProfileViewModel extends AndroidViewModel {
    public ObservableField<String> firstName;
    public ObservableField<String>lastName;
    public ObservableField<String>email;
    public ObservableField<String>phone;

    private final  LiveData<Integer> code;


    public ProfileViewModel(@NonNull Application application,EditProfileRequest request) {
        super(application);
        //initVariables();
        code=EditProfileRepository.getInstance().editProfile(application,request);
    }


    public void initVariables(){
//        firstName=new ObservableField<>(data.getFirstName());
//        lastName=new ObservableField<>(data.getLastName());
//        email=new ObservableField<>(data.getEmail());
//        phone=new ObservableField<>(data.getPhones());
//        //request=new EditProfileRequest();
    }

    public LiveData<Integer> getCode() {
        return code;
    }
}

