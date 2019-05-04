package dp.com.nabbtabase.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.databinding.ObservableField;
import androidx.annotation.NonNull;

import dp.com.nabbtabase.servise.model.request.EditProfileRequest;
import dp.com.nabbtabase.servise.repository.EditProfileRepository;

public class ProfileViewModel extends AndroidViewModel {
    public ObservableField<String> firstName;
    public ObservableField<String> lastName;
    public ObservableField<String> email;
    public ObservableField<String> phone;

    private final LiveData<Integer> code;


    public ProfileViewModel(@NonNull Application application, EditProfileRequest request) {
        super(application);
        //initVariables();
        code = EditProfileRepository.getInstance().editProfile(application, request);
    }


    public void initVariables() {
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

