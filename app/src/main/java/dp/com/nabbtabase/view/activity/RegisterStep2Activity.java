package dp.com.nabbtabase.view.activity;
import android.app.Activity;
import android.app.Dialog;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import dp.com.nabbtabase.R;
import dp.com.nabbtabase.databinding.ActivityRegisterStep2Binding;
import dp.com.nabbtabase.servise.model.request.RegisterRequest;
import dp.com.nabbtabase.utils.ConfigurationFile;
import dp.com.nabbtabase.view.callback.CallBackInterface;
import dp.com.nabbtabase.viewmodel.RegisterStep2ViewModel;


public class RegisterStep2Activity extends AppCompatActivity implements CallBackInterface {

    private RegisterStep2ViewModel viewModel;
    private ActivityRegisterStep2Binding binding;
    private RegisterRequest registerRequest;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRegisterRequest();
        viewModel=ViewModelProviders.of(this).get(RegisterStep2ViewModel.class);
        viewModel.setRegisterRequest(registerRequest);
        viewModel.setCallBackInterface(this);
        binding=DataBindingUtil.setContentView(this,R.layout.activity_register_step2);
        binding.setViewModel(viewModel);
    }

    public void setRegisterRequest(){
        registerRequest=(RegisterRequest)getIntent().getSerializableExtra(ConfigurationFile.IntentConstants.REGISTER_STEP1_DATA);
    }

    @Override
    public void updateUi(int code) {

    }

    public void policy(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater =(this).getLayoutInflater();
        View filter = inflater.inflate(R.layout.bottom_dialog_layout, null);
        builder.setView(filter);
        builder.setCancelable(true);
        Dialog dialog=builder.create();
        Window window=dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity=Gravity.BOTTOM;
        window.setAttributes(wlp);
        window.setBackgroundDrawableResource(R.color.transparent);
        dialog.show();
    }
}
