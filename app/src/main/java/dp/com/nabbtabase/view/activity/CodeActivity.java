package dp.com.nabbtabase.view.activity;

import androidx.lifecycle.ViewModelProviders;
import android.content.Intent;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.snackbar.Snackbar;
import android.view.View;

import dp.com.nabbtabase.R;
import dp.com.nabbtabase.databinding.ActivityCodeBinding;
import dp.com.nabbtabase.servise.model.request.CheckCodeRequest;
import dp.com.nabbtabase.servise.model.request.ResetPasswordRequest;
import dp.com.nabbtabase.servise.repository.CodeRepository;
import dp.com.nabbtabase.utils.ConfigurationFile;
import dp.com.nabbtabase.view.callback.CallBackInterface;
import dp.com.nabbtabase.viewmodel.CodeViewModel;

public class CodeActivity extends BaseActivity implements CallBackInterface {
    ActivityCodeBinding binding;
    CodeViewModel viewModel;
    CheckCodeRequest request;
    ResetPasswordRequest resetPasswordRequest;
    String login;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        login = getIntent().getStringExtra(ConfigurationFile.IntentConstants.LOGIN_INFO);
        viewModel = ViewModelProviders.of(this).get(CodeViewModel.class);
        viewModel.setLogin(login);
        binding = DataBindingUtil.setContentView(CodeActivity.this, R.layout.activity_code);
        binding.setViewModel(viewModel);
        CodeRepository.getInstance().setCallBackInterface(this);
        binding.firstPinView.setPinViewEventListener((pinview, b) -> {
            request = new CheckCodeRequest();
            request.setLogin(login);
            request.setCode(pinview.getValue());
            System.out.println("Code entered is : " + pinview.getValue());
            CodeRepository.getInstance().checkCode(viewModel.application, request);
        });

    }

    @Override
    public void updateUi(int code) {
        switch (code) {
            case ConfigurationFile.Constants.INVALED_EMAIL: {
                Snackbar.make(binding.clRoot, R.string.invaled_mail, Snackbar.LENGTH_LONG).show();
                break;
            }
            case ConfigurationFile.Constants.MOVE_TO_CODE_ACTIVITY: {
                Snackbar.make(binding.clRoot, R.string.activation_code_sent, Snackbar.LENGTH_LONG).show();
                break;
            }
        }
    }

    @Override
    public void errorMessage(String error) {

        System.out.println("Errore : " + error);
        if (error.endsWith("seconds")) {
            Snackbar.make(binding.clRoot, error, Snackbar.LENGTH_LONG).show();
        } else {
            resetPasswordRequest = new ResetPasswordRequest();
            resetPasswordRequest.setLogin(login);
            resetPasswordRequest.setToken(error);
            Intent intent = new Intent(CodeActivity.this, ResetPasswordActivity.class);
            intent.putExtra(ConfigurationFile.IntentConstants.RESET_PASSWORD_DATA, resetPasswordRequest);
            startActivity(intent);
        }
    }

    public void back(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finishAffinity();
    }


}
