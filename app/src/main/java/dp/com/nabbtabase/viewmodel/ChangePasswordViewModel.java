package dp.com.nabbtabase.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.view.View;

import dp.com.nabbtabase.utils.ValidationUtils;

public class ChangePasswordViewModel extends AndroidViewModel {
    LiveData<String>oldPassword;
    LiveData<String>password;
    LiveData<String>passwordConfirmation;
    public ChangePasswordViewModel(@NonNull Application application) {
        super(application);
    }

    public void done(View view){
        if(ValidationUtils.isEmpty(oldPassword.getValue())||
           ValidationUtils.isEmpty(password.getValue())||
           ValidationUtils.isEmpty(passwordConfirmation.getValue())){
            // TODO: 11/28/2018 call fill all data
        }else if (password.getValue().length()<8){
            // TODO: 11/28/2018 call password length
        }else if (!password.getValue().equals(passwordConfirmation.getValue())){
            // TODO: 11/28/2018 call pass and pass conf dont match
        }else{
            // TODO: 11/28/2018 call back end
        }

    }
}
