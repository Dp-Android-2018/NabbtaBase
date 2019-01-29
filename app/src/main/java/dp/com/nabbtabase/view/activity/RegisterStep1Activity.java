package dp.com.nabbtabase.view.activity;

import android.animation.ObjectAnimator;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;

import dp.com.nabbtabase.R;
import dp.com.nabbtabase.databinding.ActivityRegisterStep1Binding;
import dp.com.nabbtabase.servise.model.request.RegisterRequest;
import dp.com.nabbtabase.utils.ConfigurationFile;
import dp.com.nabbtabase.utils.CustomUtils;
import dp.com.nabbtabase.view.callback.CallBackInterface;
import dp.com.nabbtabase.viewmodel.RegisterStep1ViewModel;

public class RegisterStep1Activity extends BaseActivity implements CallBackInterface {
    private RegisterStep1ViewModel viewModel;
    private ActivityRegisterStep1Binding binding;
    private RegisterRequest registerRequest;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        viewModel=ViewModelProviders.of(this).get(RegisterStep1ViewModel.class);
        viewModel.setCallBackInterface(this);
        binding=DataBindingUtil.setContentView(this,R.layout.activity_register_step1);
        binding.setViewModel(viewModel);
        if (CustomUtils.getInstance().getAppLanguage(this).equals("ar")) {
            binding.ivAnim.setTranslationX(550);
        }
        binding.ivLogin.setOnClickListener(v -> {
            binding.ivLogin.setVisibility(View.GONE);
            binding.ivAnim.setVisibility(View.VISIBLE);
            if(CustomUtils.getInstance().getAppLanguage(this).equals("ar")) {
                ObjectAnimator animation = ObjectAnimator.ofFloat(binding.ivAnim, "translationX", -displayMetrics.widthPixels-35);
                animation.setDuration(3000);
                animation.start();
            }else {
                ObjectAnimator animation = ObjectAnimator.ofFloat(binding.ivAnim, "translationX", displayMetrics.widthPixels-35);
                animation.setDuration(3000);
                animation.start();
            }
            new Handler().postDelayed(() -> login(),2300);
        });
    }

    @Override
    public void updateUi(int code) {
        switch (code){
            case ConfigurationFile.Constants.FILL_ALL_DATA_ERROR_CODE:
            {
                Snackbar.make(binding.clRoot,R.string.fill_all_data_error_message,Snackbar.LENGTH_LONG).show();
                break;
            }
            case ConfigurationFile.Constants.INVALED_EMAIL:
            {
                Snackbar.make(binding.clRoot,"Invalid Email Address",Snackbar.LENGTH_LONG).show();
                break;
            }
            case ConfigurationFile.Constants.PASSWORD_LENGTH_ERROR:
            {
                Snackbar.make(binding.clRoot,R.string.password_lengrh_error_message,Snackbar.LENGTH_LONG).show();
                break;
            }
            case ConfigurationFile.Constants.PASSWORD_CONFIRMATION_ERROR:
            {
                Snackbar.make(binding.clRoot,"Password And Password Confirmation don't match",Snackbar.LENGTH_LONG).show();
                break;
            }
            case ConfigurationFile.Constants.REGISTER_STEP2:
            {
                registerRequest=viewModel.getRegisterRequest();
                Intent intent=new Intent(this,RegisterStep2Activity.class);
                intent.putExtra(ConfigurationFile.IntentConstants.REGISTER_STEP1_DATA,registerRequest);
                startActivity(intent);
                break;
            }
        }

    }

    @Override
    public void errorMessage(String error) {

    }

    public void login(){
        Intent intent=new Intent(this,LoginActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.left_to_right,R.anim.right_to_left);
        finishAffinity();
    }
}
