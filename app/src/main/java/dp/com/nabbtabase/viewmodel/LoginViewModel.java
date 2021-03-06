package dp.com.nabbtabase.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import dp.com.nabbtabase.notification.FirebaseToken;
import dp.com.nabbtabase.servise.model.pojo.LoginRegisterContent;
import dp.com.nabbtabase.servise.model.request.LoginRequest;
import dp.com.nabbtabase.servise.repository.ForgetPasswordRepository;
import dp.com.nabbtabase.servise.repository.LoginRepository;
import dp.com.nabbtabase.utils.ConfigurationFile;
import dp.com.nabbtabase.utils.ValidationUtils;
import dp.com.nabbtabase.view.callback.CallBackInterface;

public class LoginViewModel extends AndroidViewModel {
    public ObservableField<String> mail;
    public ObservableField<String> password;
    private Application application;
    private LoginRequest request;
    private CallBackInterface callBackInterface;
    private LiveData<LoginRegisterContent> data;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        this.application = application;
        initVariables();
    }

    public void initVariables() {
        mail = new ObservableField<>();
        password = new ObservableField<>();
        request = new LoginRequest();
    }

    public void login(View view) {

        if (ValidationUtils.isEmpty(mail.get()) ||
                ValidationUtils.isEmpty(password.get())) {
            // TODO: 12/2/2018  call fill all data
            callBackInterface.updateUi(ConfigurationFile.Constants.FILL_ALL_DATA_ERROR_CODE);
        } else {
            request.setEmail(mail.get());
            request.setPassword(password.get());
            FirebaseToken.getInstance().getFirebaseToken().observeForever(new Observer<String>() {
                @Override
                public void onChanged(@Nullable String s) {
                    request.setDevice_token(s);
                    callBackInterface.updateUi(ConfigurationFile.Constants.CALL_LOGIN);
                }
            });
        }
    }

    public void callLogin() {
        data = LoginRepository.getInstance().login(application, request);
    }

    public void skip(View view) {
        callBackInterface.updateUi(ConfigurationFile.Constants.SKIP);
    }

    public void setCallBackInterface(CallBackInterface callBackInterface) {
        this.callBackInterface = callBackInterface;
    }

    public void forgetPassword(View view) {
        if (ValidationUtils.isEmpty(mail.get())) {
            callBackInterface.updateUi(ConfigurationFile.Constants.ENTER_MAIL);
        } else {
            ForgetPasswordRepository.getInstance().sendCode(application, mail.get());
        }
    }
}