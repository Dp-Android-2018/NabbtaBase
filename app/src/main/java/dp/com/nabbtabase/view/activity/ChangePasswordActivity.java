package dp.com.nabbtabase.view.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import dp.com.nabbtabase.ChangePasswordViewModelFactory;
import dp.com.nabbtabase.R;
import dp.com.nabbtabase.databinding.ActivityChangePasswordBinding;
import dp.com.nabbtabase.servise.model.request.ChangePasswordRequest;
import dp.com.nabbtabase.utils.ConfigurationFile;
import dp.com.nabbtabase.utils.ValidationUtils;
import dp.com.nabbtabase.viewmodel.ActionBarViewModel;
import dp.com.nabbtabase.viewmodel.ChangePasswordViewModel;

public class ChangePasswordActivity extends AppCompatActivity {
    ChangePasswordViewModel viewModel;
    ActivityChangePasswordBinding binding;
    ChangePasswordRequest changePasswordRequest;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=DataBindingUtil.setContentView(this, R.layout.activity_change_password);
        binding.actionBar.setViewModel(new ActionBarViewModel(this,false,false,true));
    }

    public void changepassword(View view){
        if(ValidationUtils.isEmpty(binding.etOldPassword.getText().toString())||
                ValidationUtils.isEmpty(binding.etNewPassword.getText().toString())||
                ValidationUtils.isEmpty(binding.etNewPasswordConfirmation.getText().toString())){
            // TODO: 11/28/2018 call fill all
            Snackbar.make(binding.clChangePasswordRoot,R.string.fill_all_data_error_message,Snackbar.LENGTH_LONG).show();
        }else if (binding.etNewPassword.getText().toString().length()<8){
            // TODO: 11/28/2018 call password length
            Snackbar.make(binding.clChangePasswordRoot,R.string.password_lengrh_error_message,Snackbar.LENGTH_LONG).show();

        }else if (!binding.etNewPassword.getText().toString().equals(binding.etNewPasswordConfirmation.getText().toString())){
            // TODO: 11/28/2018 call pass and pass conf dont match
            Snackbar.make(binding.clChangePasswordRoot,R.string.password_confirmation,Snackbar.LENGTH_LONG).show();
        }else{
            // TODO: 11/28/2018 call back end
            setChangePasswordRequest();
            viewModel=ViewModelProviders.of(this,new ChangePasswordViewModelFactory(this.getApplication(),changePasswordRequest)).get(ChangePasswordViewModel.class);
            observableViewModel(viewModel);
        }
    }

    public void observableViewModel(ChangePasswordViewModel viewModel){
        viewModel.getCode().observe(this, integer -> {
            if(integer==ConfigurationFile.Constants.SUCCESS_CODE){
                Snackbar.make(binding.clChangePasswordRoot,R.string.password_changed_message,Snackbar.LENGTH_LONG).show();
            }
        });
    }

    public void setChangePasswordRequest(){
        changePasswordRequest=new ChangePasswordRequest();
        changePasswordRequest.setOldPassword(binding.etOldPassword.getText().toString());
        changePasswordRequest.setNewPassword(binding.etNewPassword.getText().toString());
        changePasswordRequest.setNewPasswordConfirmation(binding.etNewPasswordConfirmation.getText().toString());

    }
}
