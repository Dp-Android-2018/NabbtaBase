package dp.com.nabbtabase.view.activity;

import androidx.lifecycle.ViewModelProviders;
import android.content.Intent;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.Nullable;
import com.google.android.material.snackbar.Snackbar;
import android.view.View;

import dp.com.nabbtabase.R;
import dp.com.nabbtabase.databinding.ActivityActivationBinding;
import dp.com.nabbtabase.servise.model.pojo.LoginRegisterContent;
import dp.com.nabbtabase.servise.model.request.ActivatePhoneRequest;
import dp.com.nabbtabase.servise.repository.ForgetPasswordRepository;
import dp.com.nabbtabase.utils.ConfigurationFile;
import dp.com.nabbtabase.utils.CustomUtils;
import dp.com.nabbtabase.view.callback.CallBackInterface;
import dp.com.nabbtabase.viewmodel.ActivationViewModel;

public class ActivationActivity extends BaseActivity implements CallBackInterface {

    ActivityActivationBinding binding;
    ActivationViewModel viewModel;
    ActivatePhoneRequest request;
    private String phone;
    private LoginRegisterContent data;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        request = new ActivatePhoneRequest();
        data = CustomUtils.getInstance().getSaveUserObject(this);
        phone = CustomUtils.getInstance().getSaveUserObject(this).getPhones();
        viewModel = ViewModelProviders.of(this).get(ActivationViewModel.class);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_activation);
        request.setPhone(phone);
        if (CustomUtils.getInstance().getAppLanguage(this).equals("ar")) {
            binding.ivBack.setRotation(180);
        }
        binding.firstPinView.setPinViewEventListener((pinview, b) -> {
            request.setCode(pinview.getValue());
            CustomUtils.getInstance().showProgressDialog(this);
            viewModel.getCode(request).observe(this, integer -> {
                switch (integer) {
                    case ConfigurationFile.Constants.SUCCESS_CODE: {
                        data.setActivated("true");
                        CustomUtils.getInstance().saveDataToPrefs(data, this);
                        Snackbar.make(binding.clRoot, R.string.account_activated_successfully, Snackbar.LENGTH_LONG).show();
                        new Handler().postDelayed(() -> CustomUtils.getInstance().moveToContainer(ActivationActivity.this), 2000);
                        break;
                    }
                    case ConfigurationFile.Constants.ALREADY_ACTIVATED: {
                        Snackbar.make(binding.clRoot, R.string.invalid_code_message, Snackbar.LENGTH_LONG).show();
                    }
                }
            });
        });
    }

    public void back(View view) {
        finishAffinity();
    }

    public void resend(View view) {
        ForgetPasswordRepository.getInstance().setCallBackInterface(this);
        ForgetPasswordRepository.getInstance().sendCode(viewModel.getApplication(), phone);
    }

    @Override
    public void updateUi(int code) {
        switch (code) {
            case ConfigurationFile.Constants.SUCCESS_CODE: {
                errorMessage(getResources().getString(R.string.activation_code_sent));
                break;
            }
            case ConfigurationFile.Constants.INVALED_EMAIL: {
                errorMessage(getString(R.string.incorrect_phone_number_message));
            }
        }
    }

    @Override
    public void errorMessage(String error) {
        Snackbar.make(binding.clRoot, error, Snackbar.LENGTH_LONG).show();
    }

    public void mailActivation(View view) {
        Intent intent = new Intent(this, MailActivationActivity.class);
        startActivity(intent);
    }

    public void logout(View view) {
        CustomUtils.getInstance().logout(this);
    }
}
