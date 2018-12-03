package dp.com.nabbtabase.viewmodel;
import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.content.Context;
import android.content.Intent;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.support.constraint.solver.widgets.ConstraintWidgetGroup;
import android.view.View;

import dp.com.nabbtabase.servise.model.request.RegisterRequest;
import dp.com.nabbtabase.utils.ConfigurationFile;
import dp.com.nabbtabase.utils.ValidationUtils;
import dp.com.nabbtabase.view.activity.RegisterStep2Activity;
import dp.com.nabbtabase.view.callback.CallBackInterface;


public class RegisterStep1ViewModel extends AndroidViewModel {

    public ObservableField<String> firstName;
    public ObservableField<String> lastName;
    public ObservableField<String> email;
    public ObservableField<String> password;
    public ObservableField<String> passwordConfirmation;

    private CallBackInterface callBackInterface;
    private RegisterRequest registerRequest;

    public RegisterStep1ViewModel(@NonNull Application application) {
        super(application);
        initVariables();
    }


    public void initVariables(){
        firstName=new ObservableField<>();
        lastName=new ObservableField<>();
        email=new ObservableField<>();
        password=new ObservableField<>();
        passwordConfirmation=new ObservableField<>();
        registerRequest=new RegisterRequest();
    }
    public void next(View view){
        if(ValidationUtils.isEmpty(firstName.get())||
           ValidationUtils.isEmpty(lastName.get())||
           ValidationUtils.isEmpty(email.get())||
           ValidationUtils.isEmpty(password.get())||
           ValidationUtils.isEmpty(passwordConfirmation.get())) {
            // TODO: 11/28/2018  call snakebar fill data
            callBackInterface.updateUi(ConfigurationFile.Constants.FILL_ALL_DATA_ERROR_CODE);
        }else if (!ValidationUtils.isMail(email.get())){
            // TODO: 12/3/2018 invaid mail
            callBackInterface.updateUi(ConfigurationFile.Constants.INVALED_EMAIL);
        }else if(password.get().length()<8){
            // TODO: 11/28/2018 call snakebar password length less 8
            callBackInterface.updateUi(ConfigurationFile.Constants.PASSWORD_LENGTH_ERROR);
        }else if(!password.get().equals(passwordConfirmation.get())){
            // TODO: 11/28/2018 call snakbar password and password confirmation dont match
            callBackInterface.updateUi(ConfigurationFile.Constants.PASSWORD_CONFIRMATION_ERROR);
        }else {
            // TODO: 12/3/2018 next step
            registerRequest.setName(firstName.get()+" "+lastName.get());
            registerRequest.setEmail(email.get());
            registerRequest.setPassword(password.get());
            registerRequest.setPassword_confirmation(passwordConfirmation.get());
           callBackInterface.updateUi(ConfigurationFile.Constants.REGISTER_STEP2);
        }
    }

    public void setCallBackInterface(CallBackInterface callBackInterface) {
        this.callBackInterface = callBackInterface;
    }

    public RegisterRequest getRegisterRequest() {
        return registerRequest;
    }
}
