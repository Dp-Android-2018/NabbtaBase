package dp.com.nabbtabase.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.view.View;

import dp.com.nabbtabase.servise.model.request.ResetPasswordRequest;
import dp.com.nabbtabase.servise.repository.ResetPasswordRepository;
import dp.com.nabbtabase.utils.ConfigurationFile;
import dp.com.nabbtabase.utils.ValidationUtils;
import dp.com.nabbtabase.view.callback.CallBackInterface;

public class ResetPasswordViewModel extends AndroidViewModel {

    public ObservableField<String> password;
    public ObservableField<String>passwordConfirmation;
    private CallBackInterface callBackInterface;
    private ResetPasswordRequest resetPasswordRequest;
    private Application application;
    public ResetPasswordViewModel(@NonNull Application application) {
        super(application);
        this.application=application;
        password=new ObservableField<>();
        passwordConfirmation=new ObservableField<>();
    }

    public void confirmPassword(View view){
        if(ValidationUtils.isEmpty(password.get())||
           ValidationUtils.isEmpty(passwordConfirmation.get())){
            // TODO: 11/28/2018 call snakebar fill all data
            callBackInterface.updateUi(ConfigurationFile.Constants.FILL_ALL_DATA_ERROR_CODE);
        }else if(password.get().length()<8){
            // TODO: 11/28/2018 call snakebar password lenth
            callBackInterface.updateUi(ConfigurationFile.Constants.PASSWORD_LENGTH_ERROR);
        }else if (!password.get().equals(passwordConfirmation.get())){
            // TODO: 11/28/2018 call snakebar pass and pass confirmation dont match
            callBackInterface.updateUi(ConfigurationFile.Constants.PASSWORD_CONFIRMATION_ERROR);
        }else {
            // TODO: 11/28/2018 call back end
            resetPasswordRequest.setPassword(password.get());
            resetPasswordRequest.setPasswordConfirmation(passwordConfirmation.get());
            ResetPasswordRepository.getInstance().resetPassword(application,resetPasswordRequest);
        }
    }

    public void setCallBackInterface(CallBackInterface callBackInterface) {
        this.callBackInterface = callBackInterface;
    }

    public void setResetPasswordRequest(ResetPasswordRequest resetPasswordRequest) {
        this.resetPasswordRequest = resetPasswordRequest;
    }
}
