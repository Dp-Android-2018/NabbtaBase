package dp.com.nabbtabase.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.view.View;

import dp.com.nabbtabase.utils.ValidationUtils;

public class ProfileViewModel extends AndroidViewModel {
    LiveData<String>firstName;
    LiveData<String>lastName;
    LiveData<String>email;
    LiveData<String>phone;
    public ProfileViewModel(@NonNull Application application) {
        super(application);
    }


    public void done(View view){
        if (ValidationUtils.isEmpty(firstName.getValue())||
                ValidationUtils.isEmpty(lastName.getValue())||
                ValidationUtils.isEmpty(email.getValue())||
                ValidationUtils.isEmpty(phone.getValue())){
            // TODO: 11/28/2018 call fill all data
        }
    }
}
