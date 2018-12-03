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
import dp.com.nabbtabase.servise.repository.LoginRepository;
import dp.com.nabbtabase.utils.ConfigurationFile;
import dp.com.nabbtabase.view.callback.CallBackInterface;
import dp.com.nabbtabase.viewmodel.LoginViewModel;

public class LoginActivity extends AppCompatActivity implements CallBackInterface {
    private LoginViewModel viewModel;
    private ActivityLoginBinding binding;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel=ViewModelProviders.of(this).get(LoginViewModel.class);
        viewModel.setCallBackInterface(this);
        binding=DataBindingUtil.setContentView(this,R.layout.activity_login);
        binding.setViewModel(viewModel);
        LoginRepository.getInstance().setCallBackInterface(this);
    }

    @Override
    public void updateUi(int code) {
        switch (code){
            case ConfigurationFile.Constants.SKIP:{
                moveToContainer();
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
                moveToContainer();
                break;
            }

        }
    }

    public void moveToContainer(){
        Intent intent=new Intent(this,ContainerActivity.class);
        startActivity(intent);
    }
}
