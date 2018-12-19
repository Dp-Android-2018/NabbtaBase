package dp.com.nabbtabase.view.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;

import com.goodiebag.pinview.Pinview;

import dp.com.nabbtabase.R;
import dp.com.nabbtabase.databinding.ActivityCodeBinding;
import dp.com.nabbtabase.servise.model.request.CheckCodeRequest;
import dp.com.nabbtabase.servise.model.request.ResetPasswordRequest;
import dp.com.nabbtabase.servise.repository.CodeRepository;
import dp.com.nabbtabase.utils.ConfigurationFile;
import dp.com.nabbtabase.view.callback.CallBackInterface;
import dp.com.nabbtabase.viewmodel.CodeViewModel;

public class CodeActivity extends AppCompatActivity implements CallBackInterface {
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
        binding.firstPinView.setPinViewEventListener(new Pinview.PinViewEventListener() {
            @Override
            public void onDataEntered(Pinview pinview, boolean b) {
                request = new CheckCodeRequest();
                request.setLogin(login);
                request.setCode(pinview.getValue());
                CodeRepository.getInstance().checkCode(viewModel.application, request);
            }
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
                Snackbar.make(binding.clRoot, "code has been sent", Snackbar.LENGTH_LONG).show();
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
}
