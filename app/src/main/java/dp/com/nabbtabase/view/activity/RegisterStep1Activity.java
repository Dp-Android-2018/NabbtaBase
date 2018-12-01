package dp.com.nabbtabase.view.activity;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import dp.com.nabbtabase.R;
import dp.com.nabbtabase.databinding.ActivityRegisterStep1Binding;
import dp.com.nabbtabase.viewmodel.RegisterStep1ViewModel;

public class RegisterStep1Activity extends AppCompatActivity {
    RegisterStep1ViewModel viewModel;
    ActivityRegisterStep1Binding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel=ViewModelProviders.of(this).get(RegisterStep1ViewModel.class);
        binding=DataBindingUtil.setContentView(this,R.layout.activity_register_step1);
        binding.setViewModel(viewModel);
    }
}
