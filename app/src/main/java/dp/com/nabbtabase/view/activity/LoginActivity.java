package dp.com.nabbtabase.view.activity;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;

import dp.com.nabbtabase.R;
import dp.com.nabbtabase.databinding.ActivityLoginBinding;
import dp.com.nabbtabase.servise.repository.ForgetPasswordRepository;
import dp.com.nabbtabase.servise.repository.LoginRepository;
import dp.com.nabbtabase.utils.ConfigurationFile;
import dp.com.nabbtabase.utils.CustomUtils;
import dp.com.nabbtabase.view.callback.CallBackInterface;
import dp.com.nabbtabase.viewmodel.LoginViewModel;

public  class LoginActivity extends AppCompatActivity implements CallBackInterface {
    private LoginViewModel viewModel;
    private ActivityLoginBinding binding;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel=ViewModelProviders.of(this).get(LoginViewModel.class);
        viewModel.setCallBackInterface(this);
        binding=DataBindingUtil.setContentView(this,R.layout.activity_login);
        binding.setViewModel(viewModel);
        LoginRepository.getInstance().setCallBackInterface(this,LoginActivity.this);
        ForgetPasswordRepository.getInstance().setCallBackInterface(this);
    }

    @Override
    public void updateUi(int code) {
        switch (code){
            case ConfigurationFile.Constants.SKIP:{
                CustomUtils.getInstance().moveToContainer(this);
                break;
            }
            case ConfigurationFile.Constants.FILL_ALL_DATA_ERROR_CODE:{
                Snackbar.make(binding.clRoot,R.string.fill_all_data_error_message,Snackbar.LENGTH_LONG).show();
                break;
            }
            case ConfigurationFile.Constants.INVALED_DATA_CODE:{
                Snackbar.make(binding.clRoot,"User Name Or Password Is Invalid ",Snackbar.LENGTH_LONG).show();
                break;
            }
            case ConfigurationFile.Constants.SUCCESS_CODE:{
                CustomUtils.getInstance().moveToContainer(this);
                break;
            }
            case ConfigurationFile.Constants.ENTER_MAIL:{
                Snackbar.make(binding.clRoot,R.string.enter_mail_phone,Snackbar.LENGTH_LONG).show();
                break;
            }
            case ConfigurationFile.Constants.SIGNUP:{
                Intent intent=new Intent(this,RegisterStep1Activity.class);
                startActivity(intent);
                break;
            }
            case ConfigurationFile.Constants.MOVE_TO_CODE_ACTIVITY:{
                Intent intent=new Intent(this,CodeActivity.class);
                intent.putExtra(ConfigurationFile.IntentConstants.LOGIN_INFO,viewModel.mail.get());
                startActivity(intent);
                break;
            }case ConfigurationFile.Constants.INVALED_EMAIL:{
                Snackbar.make(binding.clRoot,"Mail or Phone incorrect try after 2 mints",Snackbar.LENGTH_LONG).show();
                break;
            }
        }
    }

    @Override
    public void errorMessage(String error) {
        Snackbar.make(binding.clRoot,error,Snackbar.LENGTH_LONG).show();
    }


}
