package dp.com.nabbtabase.view.activity;

import androidx.lifecycle.ViewModelProviders;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.snackbar.Snackbar;
import android.view.View;

import dp.com.nabbtabase.R;
import dp.com.nabbtabase.ViewModelFactory.ProfileViewModelFactory;
import dp.com.nabbtabase.databinding.ActivityProfileBinding;
import dp.com.nabbtabase.servise.model.pojo.LoginRegisterContent;
import dp.com.nabbtabase.servise.model.request.EditProfileRequest;
import dp.com.nabbtabase.utils.ConfigurationFile;
import dp.com.nabbtabase.utils.CustomUtils;
import dp.com.nabbtabase.utils.ValidationUtils;
import dp.com.nabbtabase.viewmodel.ActionBarViewModel;
import dp.com.nabbtabase.viewmodel.ProfileViewModel;

public class ProfileActivity extends BaseActivity {

    ProfileViewModel viewModel;
    ActivityProfileBinding binding;
    EditProfileRequest request;
    private LoginRegisterContent data;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //viewModel.setCallBackInterface(this,this);
        data = CustomUtils.getInstance().getSaveUserObject(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile);
        binding.actionBar.setViewModel(new ActionBarViewModel(this, false, false, true));
        if (CustomUtils.getInstance().getAppLanguage(this).equals("ar")) {
            binding.actionBar.ivBack.setRotation(180);
        }
        setData();
    }

    public void setData() {
        binding.etLastName.setText(data.getLastName());
        binding.etFirstName.setText(data.getFirstName());
        binding.tvMail.setText(data.getEmail());
        binding.tvPhone.setText(data.getPhones());
    }


    public void done(View view) {
        if (ValidationUtils.isEmpty(binding.etFirstName.getText().toString()) ||
                ValidationUtils.isEmpty(binding.etLastName.getText().toString())) {
            // TODO: 11/28/2018 call fill all data
            Snackbar.make(binding.clRoot, R.string.fill_all_data_error_message, Snackbar.LENGTH_LONG).show();

        } else {
            request = new EditProfileRequest();
            request.setFirstName(binding.etFirstName.getText().toString());
            request.setLastName(binding.etLastName.getText().toString());
            viewModel = ViewModelProviders.of(this, new ProfileViewModelFactory(this.getApplication(), request)).get(ProfileViewModel.class);
            observableViewModel(viewModel);

        }
    }

    public void observableViewModel(ProfileViewModel viewModel) {
        viewModel.getCode().observe(this, integer -> {
            if (integer == ConfigurationFile.Constants.SUCCESS_CODE) {
                Snackbar.make(binding.clRoot, getResources().getString(R.string.data_updated_successfully), Snackbar.LENGTH_LONG).show();
            }
        });
    }
}
