package dp.com.nabbtabase.view.activity;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import dp.com.nabbtabase.R;
import dp.com.nabbtabase.databinding.ActivityRegisterStep2Binding;
import dp.com.nabbtabase.viewmodel.RegisterStep2ViewModel;


public class RegisterStep2Activity extends AppCompatActivity {

    RegisterStep2ViewModel viewModel;
    ActivityRegisterStep2Binding binding;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel=ViewModelProviders.of(this).get(RegisterStep2ViewModel.class);
        binding=DataBindingUtil.setContentView(this,R.layout.activity_register_step2);
        binding.setViewModel(viewModel);
    }

}
