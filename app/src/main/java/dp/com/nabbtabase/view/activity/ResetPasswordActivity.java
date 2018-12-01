package dp.com.nabbtabase.view.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import dp.com.nabbtabase.R;
import dp.com.nabbtabase.databinding.ActivityResetPasswordBinding;
import dp.com.nabbtabase.viewmodel.ResetPasswordViewModel;

public class ResetPasswordActivity extends AppCompatActivity {


    ResetPasswordViewModel viewModel;
    ActivityResetPasswordBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel=ViewModelProviders.of(this).get(ResetPasswordViewModel.class);
        binding=DataBindingUtil.setContentView(this, R.layout.activity_reset_password);
        binding.setViewModel(viewModel);
    }
}
