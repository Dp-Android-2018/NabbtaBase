package dp.com.nabbtabase.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.view.View;

import dp.com.nabbtabase.utils.ValidationUtils;

public class ResetPasswordViewModel extends AndroidViewModel {

    LiveData<String>password;
    LiveData<String>passwordConfirmation;
    public ResetPasswordViewModel(@NonNull Application application) {
        super(application);
    }

    public void confirmPassword(View view){
        if(ValidationUtils.isEmpty(password.getValue())||
           ValidationUtils.isEmpty(passwordConfirmation.getValue())){
            // TODO: 11/28/2018 call snakebar fill all data
        }else if(password.getValue().length()<8){
            // TODO: 11/28/2018 call snakebar password lenth
        }else if (!password.getValue().equals(passwordConfirmation.getValue())){
            // TODO: 11/28/2018 call snakebar pass and pass confirmation dont match
        }else {
            // TODO: 11/28/2018 call back end
        }
    }
}
