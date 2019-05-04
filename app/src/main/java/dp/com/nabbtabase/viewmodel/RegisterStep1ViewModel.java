package dp.com.nabbtabase.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.databinding.ObservableField;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;

import dp.com.nabbtabase.R;
import dp.com.nabbtabase.servise.model.request.CheckMailRequest;
import dp.com.nabbtabase.servise.model.request.RegisterRequest;
import dp.com.nabbtabase.servise.model.response.CheckMailResponse;
import dp.com.nabbtabase.servise.repository.CheckMailRepository;
import dp.com.nabbtabase.utils.ConfigurationFile;
import dp.com.nabbtabase.utils.ValidationUtils;
import dp.com.nabbtabase.view.callback.CallBackInterface;


public class RegisterStep1ViewModel extends AndroidViewModel {

    public ObservableField<String> firstName;
    public ObservableField<String> lastName;
    public ObservableField<String> email;
    public ObservableField<String> password;
    public ObservableField<String> passwordConfirmation;
    private LiveData<CheckMailResponse> mailResponseLiveData;
    private CallBackInterface callBackInterface;
    private RegisterRequest registerRequest;
    Application application;

    public RegisterStep1ViewModel(@NonNull Application application) {
        super(application);
        this.application = application;
        initVariables();
    }


    public void initVariables() {
        firstName = new ObservableField<>();
        lastName = new ObservableField<>();
        email = new ObservableField<>();
        password = new ObservableField<>();
        passwordConfirmation = new ObservableField<>();
        registerRequest = new RegisterRequest();
    }

    public void next(View view) {
        if (ValidationUtils.isEmpty(firstName.get()) ||
                ValidationUtils.isEmpty(lastName.get()) ||
                ValidationUtils.isEmpty(email.get()) ||
                ValidationUtils.isEmpty(password.get()) ||
                ValidationUtils.isEmpty(passwordConfirmation.get())) {
            // TODO: 11/28/2018  call snakebar fill data
            callBackInterface.updateUi(ConfigurationFile.Constants.FILL_ALL_DATA_ERROR_CODE);
        } else if (!ValidationUtils.isMail(email.get())) {
            // TODO: 12/3/2018 invaid mail
            callBackInterface.updateUi(ConfigurationFile.Constants.INVALED_EMAIL);
        } else if (password.get().length() < 8) {
            // TODO: 11/28/2018 call snakebar password length less 8
            callBackInterface.updateUi(ConfigurationFile.Constants.PASSWORD_LENGTH_ERROR);
        } else if (!password.get().equals(passwordConfirmation.get())) {
            // TODO: 11/28/2018 call snakbar password and password confirmation dont match
            callBackInterface.updateUi(ConfigurationFile.Constants.PASSWORD_CONFIRMATION_ERROR);
        } else {
            // TODO: 12/3/2018 next step
            getMailResponseLiveData().observeForever(new Observer<CheckMailResponse>() {
                @Override
                public void onChanged(@Nullable CheckMailResponse checkMailResponse) {
                    if (checkMailResponse.getExist().equals("false")) {
                        registerRequest.setFirstName(firstName.get());
                        registerRequest.setLastName(lastName.get());
                        registerRequest.setEmail(email.get());
                        registerRequest.setPassword(password.get());
                        registerRequest.setPassword_confirmation(passwordConfirmation.get());
                        callBackInterface.updateUi(ConfigurationFile.Constants.REGISTER_STEP2);
                    } else {
                        callBackInterface.errorMessage(application.getString(R.string.this_mail_exist));
                    }
                }
            });

        }
    }

    public LiveData<CheckMailResponse> getMailResponseLiveData() {
        mailResponseLiveData = CheckMailRepository.getInstance().existMail(getApplication(), new CheckMailRequest(email.get()));
        return mailResponseLiveData;
    }

    public void setCallBackInterface(CallBackInterface callBackInterface) {
        this.callBackInterface = callBackInterface;
    }

    public RegisterRequest getRegisterRequest() {
        return registerRequest;
    }
}
