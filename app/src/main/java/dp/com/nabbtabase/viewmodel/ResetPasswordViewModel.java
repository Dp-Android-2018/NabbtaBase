package dp.com.nabbtabase.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.view.View;

import dp.com.nabbtabase.utils.ValidationUtils;

public class ResetPasswordViewModel extends AndroidViewModel {

    public ObservableField<String> password;
    public ObservableField<String>passwordConfirmation;
    public ResetPasswordViewModel(@NonNull Application application) {
        super(application);
    }

    public void confirmPassword(View view){
        if(ValidationUtils.isEmpty(password.get())||
           ValidationUtils.isEmpty(passwordConfirmation.get())){
            // TODO: 11/28/2018 call snakebar fill all data
        }else if(password.get().length()<8){
            // TODO: 11/28/2018 call snakebar password lenth
        }else if (!password.get().equals(passwordConfirmation.get())){
            // TODO: 11/28/2018 call snakebar pass and pass confirmation dont match
        }else {
            // TODO: 11/28/2018 call back end
        }
    }
}
