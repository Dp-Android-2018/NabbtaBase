package dp.com.nabbtabase.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.view.View;

import dp.com.nabbtabase.utils.ValidationUtils;

public class ChangePasswordViewModel extends AndroidViewModel {
    public ObservableField<String> oldPassword;
    public ObservableField<String>password;
    public ObservableField<String>passwordConfirmation;
    public ChangePasswordViewModel(@NonNull Application application) {
        super(application);
    }

    public void done(View view){
        if(ValidationUtils.isEmpty(oldPassword.get())||
           ValidationUtils.isEmpty(password.get())||
           ValidationUtils.isEmpty(passwordConfirmation.get())){
            // TODO: 11/28/2018 call fill all data
        }else if (password.get().length()<8){
            // TODO: 11/28/2018 call password length
        }else if (!password.get().equals(passwordConfirmation.get())){
            // TODO: 11/28/2018 call pass and pass conf dont match
        }else{
            // TODO: 11/28/2018 call back end
        }

    }
}
