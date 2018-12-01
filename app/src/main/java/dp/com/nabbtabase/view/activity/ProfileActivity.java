package dp.com.nabbtabase.view.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import dp.com.nabbtabase.R;
import dp.com.nabbtabase.databinding.ActivityProfileBinding;
import dp.com.nabbtabase.viewmodel.ProfileViewModel;

public class ProfileActivity extends AppCompatActivity {

    ProfileViewModel viewModel;
    ActivityProfileBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel=ViewModelProviders.of(this).get(ProfileViewModel.class);
        binding=DataBindingUtil.setContentView(this, R.layout.activity_profile);
        binding.setViewModel(viewModel);
    }
}
