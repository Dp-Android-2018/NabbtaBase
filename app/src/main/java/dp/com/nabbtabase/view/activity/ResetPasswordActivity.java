package dp.com.nabbtabase.view.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;

import dp.com.nabbtabase.MyViewModelFactory;
import dp.com.nabbtabase.R;
import dp.com.nabbtabase.databinding.ActivityResetPasswordBinding;
import dp.com.nabbtabase.servise.model.request.ResetPasswordRequest;
import dp.com.nabbtabase.utils.ConfigurationFile;
import dp.com.nabbtabase.view.callback.CallBackInterface;
import dp.com.nabbtabase.viewmodel.ResetPasswordViewModel;

public class ResetPasswordActivity extends AppCompatActivity implements CallBackInterface {


    ResetPasswordViewModel viewModel;
    ActivityResetPasswordBinding binding;
    ResetPasswordRequest resetPasswordRequest;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        resetPasswordRequest=(ResetPasswordRequest) getIntent().getSerializableExtra(ConfigurationFile.IntentConstants.RESET_PASSWORD_DATA);
        viewModel=ViewModelProviders.of(this,new MyViewModelFactory(this.getApplication(),resetPasswordRequest)).get(ResetPasswordViewModel.class);
        viewModel.setCallBackInterface(this);
        binding=DataBindingUtil.setContentView(this, R.layout.activity_reset_password);
        binding.setViewModel(viewModel);
    }

    @Override
    public void updateUi(int code) {
        switch (code){
            case ConfigurationFile.Constants.FILL_ALL_DATA_ERROR_CODE:
            {
                Snackbar.make(binding.clRoot,R.string.fill_all_data_error_message,Snackbar.LENGTH_LONG).show();
                break;
            }
            case ConfigurationFile.Constants.PASSWORD_LENGTH_ERROR:
            {
                Snackbar.make(binding.clRoot,R.string.password_lengrh_error_message,Snackbar.LENGTH_LONG).show();
                break;
            }
            case ConfigurationFile.Constants.PASSWORD_CONFIRMATION_ERROR:
            {
                Snackbar.make(binding.clRoot,R.string.password_confirmation,Snackbar.LENGTH_LONG).show();
                break;
            }

        }

    }

    @Override
    public void errorMessage(String error) {

    }
}
