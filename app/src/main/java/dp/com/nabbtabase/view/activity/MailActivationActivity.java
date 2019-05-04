package dp.com.nabbtabase.view.activity;

import androidx.lifecycle.ViewModelProviders;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.snackbar.Snackbar;
import android.view.View;

import dp.com.nabbtabase.R;
import dp.com.nabbtabase.databinding.ActivityMailActivationBinding;
import dp.com.nabbtabase.utils.ConfigurationFile;
import dp.com.nabbtabase.utils.CustomUtils;
import dp.com.nabbtabase.viewmodel.ActionBarViewModel;
import dp.com.nabbtabase.viewmodel.MailActivationViewModel;

public class MailActivationActivity extends BaseActivity {

    ActivityMailActivationBinding binding;
    MailActivationViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_mail_activation);
        viewModel = ViewModelProviders.of(this).get(MailActivationViewModel.class);
        binding.actionBar.setViewModel(new ActionBarViewModel(this, false, false, true));
        if (CustomUtils.getInstance().getAppLanguage(this).equals("ar")) {
            binding.actionBar.ivBack.setRotation(180);
        }
    }

    public void sendMail(View view) {
        CustomUtils.getInstance().showProgressDialog(this);
        viewModel.getCode().observe(this, integer -> {
            CustomUtils.getInstance().cancelDialog();
            if (integer == ConfigurationFile.Constants.SUCCESS_CODE) {
                Snackbar.make(binding.clRoot, R.string.mail_sent_message, Snackbar.LENGTH_LONG).show();
            } else {
                Snackbar.make(binding.clRoot, R.string.mail_sent_error_message, Snackbar.LENGTH_LONG).show();

            }
        });
    }
}
