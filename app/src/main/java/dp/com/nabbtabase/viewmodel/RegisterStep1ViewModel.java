package dp.com.nabbtabase.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Toast;

import dp.com.nabbtabase.utils.ValidationUtils;
import dp.com.nabbtabase.view.activity.RegisterStep2Activity;

public class RegisterStep1ViewModel extends AndroidViewModel {

    LiveData<String> firstName;
    LiveData<String> lastName;
    LiveData<String> email;
    LiveData<String> password;
    LiveData<String> passwordConfirmation;
    private Context context;
    public RegisterStep1ViewModel(@NonNull Application application) {
        super(application);
    }

    public void next(View view){
        if(ValidationUtils.isEmpty(firstName.getValue())||
         ValidationUtils.isEmpty(lastName.getValue())||
         ValidationUtils.isEmpty(email.getValue())||
         ValidationUtils.isEmpty(password.getValue())||
         ValidationUtils.isEmpty(passwordConfirmation.getValue())){
            // TODO: 11/28/2018  call snakebar fill data

        }else if(password.getValue().length()<8){
            // TODO: 11/28/2018 call snakebar password length less 8
        }else if(!password.getValue().equals(passwordConfirmation.getValue())){
            // TODO: 11/28/2018 call snakbar password and password confirmation dont match
        }else {
            Intent intent=new Intent(context,RegisterStep2Activity.class);
            context.startActivity(intent);
        }
    }
}
