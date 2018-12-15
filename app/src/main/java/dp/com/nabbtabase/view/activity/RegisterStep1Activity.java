package dp.com.nabbtabase.view.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import dp.com.nabbtabase.R;
import dp.com.nabbtabase.databinding.ActivityRegisterStep1Binding;
import dp.com.nabbtabase.servise.model.request.RegisterRequest;
import dp.com.nabbtabase.utils.ConfigurationFile;
import dp.com.nabbtabase.view.callback.CallBackInterface;
import dp.com.nabbtabase.viewmodel.RegisterStep1ViewModel;

public class RegisterStep1Activity extends AppCompatActivity implements CallBackInterface {
    private RegisterStep1ViewModel viewModel;
    private ActivityRegisterStep1Binding binding;
    private RegisterRequest registerRequest;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel=ViewModelProviders.of(this).get(RegisterStep1ViewModel.class);
        viewModel.setCallBackInterface(this);
        binding=DataBindingUtil.setContentView(this,R.layout.activity_register_step1);
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
            case ConfigurationFile.Constants.INVALED_EMAIL:
            {
                Snackbar.make(binding.clRoot,"Invalid Email Address",Snackbar.LENGTH_LONG).show();
                break;
            }
            case ConfigurationFile.Constants.PASSWORD_LENGTH_ERROR:
            {
                Snackbar.make(binding.clRoot,R.string.password_lengrh_error_message,Snackbar.LENGTH_LONG).show();
                break;
            }
            case ConfigurationFile.Constants.PASSWORD_CONFIRMATION_ERROR:
            {
                Snackbar.make(binding.clRoot,"Password And Password Confirmation don't match",Snackbar.LENGTH_LONG).show();
                break;
            }
            case ConfigurationFile.Constants.REGISTER_STEP2:
            {
                registerRequest=viewModel.getRegisterRequest();
                Intent intent=new Intent(this,RegisterStep2Activity.class);
                intent.putExtra(ConfigurationFile.IntentConstants.REGISTER_STEP1_DATA,registerRequest);
                startActivity(intent);
                break;
            }
        }

    }

    @Override
    public void errorMessage(String error) {

    }
}
