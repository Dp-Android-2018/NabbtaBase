package dp.com.nabbtabase.view.activity;

import android.animation.ObjectAnimator;
import androidx.lifecycle.ViewModelProviders;
import android.content.Intent;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.Nullable;
import com.google.android.material.snackbar.Snackbar;
import android.util.DisplayMetrics;
import android.view.View;

import dp.com.nabbtabase.R;
import dp.com.nabbtabase.databinding.ActivityLoginBinding;
import dp.com.nabbtabase.servise.repository.ForgetPasswordRepository;
import dp.com.nabbtabase.servise.repository.LoginRepository;
import dp.com.nabbtabase.utils.ConfigurationFile;
import dp.com.nabbtabase.utils.CustomUtils;
import dp.com.nabbtabase.view.callback.CallBackInterface;
import dp.com.nabbtabase.viewmodel.LoginViewModel;

public class LoginActivity extends BaseActivity implements CallBackInterface {
    private LoginViewModel viewModel;
    private ActivityLoginBinding binding;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        viewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        viewModel.setCallBackInterface(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        binding.setViewModel(viewModel);
        if (CustomUtils.getInstance().getAppLanguage(this).equals("ar")) {
            binding.ivSkip.setRotation(180);
            binding.ivAnim.setTranslationX(-550);
        }
        LoginRepository.getInstance().setCallBackInterface(this, LoginActivity.this);
        ForgetPasswordRepository.getInstance().setCallBackInterface(this);
        binding.ivSignup.setOnClickListener(v -> {
            binding.ivSignup.setVisibility(View.GONE);
            binding.ivAnim.setVisibility(View.VISIBLE);
            if (CustomUtils.getInstance().getAppLanguage(this).equals("ar")) {
                ObjectAnimator animation = ObjectAnimator.ofFloat(binding.ivAnim, "translationX", displayMetrics.widthPixels - 35);
                animation.setDuration(3000);
                animation.start();
            } else {
                ObjectAnimator animation = ObjectAnimator.ofFloat(binding.ivAnim, "translationX", -displayMetrics.widthPixels - 35);
                animation.setDuration(3000);
                animation.start();
            }

            new Handler().postDelayed(() -> updateUi(ConfigurationFile.Constants.SIGNUP), 2500);
        });
    }

    @Override
    public void updateUi(int code) {
        switch (code) {
            case ConfigurationFile.Constants.SKIP: {
                CustomUtils.getInstance().moveToContainer(this);
                break;
            }
            case ConfigurationFile.Constants.FILL_ALL_DATA_ERROR_CODE: {
                Snackbar.make(binding.clRoot, R.string.fill_all_data_error_message, Snackbar.LENGTH_LONG).show();
                break;
            }
            case ConfigurationFile.Constants.INVALED_DATA_CODE: {
                Snackbar.make(binding.clRoot, R.string.invalid_user_password_message, Snackbar.LENGTH_LONG).show();
                break;
            }
            case ConfigurationFile.Constants.SUCCESS_CODE: {
                if (CustomUtils.getInstance().getSaveUserObject(this).getActivated().equals("true")) {
                    CustomUtils.getInstance().moveToContainer(this);
                } else {
                    Intent intent = new Intent(this, CodeActivity.class);
                    startActivity(intent);
                    finishAffinity();
                }
                break;
            }
            case ConfigurationFile.Constants.ENTER_MAIL: {
                Snackbar.make(binding.clRoot, R.string.enter_mail_phone, Snackbar.LENGTH_LONG).show();
                break;
            }
            case ConfigurationFile.Constants.SIGNUP: {
                Intent intent = new Intent(this, RegisterStep1Activity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.right_to_left, R.anim.left_to_right);
                finishAffinity();
                break;
            }
            case ConfigurationFile.Constants.MOVE_TO_CODE_ACTIVITY: {
                Intent intent = new Intent(this, CodeActivity.class);
                intent.putExtra(ConfigurationFile.IntentConstants.LOGIN_INFO, viewModel.mail.get());
                startActivity(intent);
                break;
            }
            case ConfigurationFile.Constants.INVALED_EMAIL: {
                Snackbar.make(binding.clRoot, R.string.invaled_mail, Snackbar.LENGTH_LONG).show();
                break;
            }
            case ConfigurationFile.Constants.INVALED_EMAIL_PASSWORD: {
                errorMessage(getResources().getString(R.string.invalid_user_password_message));
                break;
            }
            case ConfigurationFile.Constants.CALL_LOGIN: {
                CustomUtils.getInstance().showProgressDialog(this);
                viewModel.callLogin();
                break;
            }
        }
    }

    @Override
    public void errorMessage(String error) {
        Snackbar.make(binding.clRoot, error, Snackbar.LENGTH_LONG).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (CustomUtils.getInstance().getAppLanguage(this).equals("ar")) {
            binding.ivSkip.setRotation(180);
        }
    }
}
