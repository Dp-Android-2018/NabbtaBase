package dp.com.nabbtabase.view.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import dp.com.nabbtabase.R;
import dp.com.nabbtabase.databinding.ActivityChangePasswordBinding;
import dp.com.nabbtabase.viewmodel.ChangePasswordViewModel;

public class ChangePasswordActivity extends AppCompatActivity {
    ChangePasswordViewModel viewModel;
    ActivityChangePasswordBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel=ViewModelProviders.of(this).get(ChangePasswordViewModel.class);
        binding=DataBindingUtil.setContentView(this, R.layout.activity_change_password);
        binding.setViewModel(viewModel);
    }
}
