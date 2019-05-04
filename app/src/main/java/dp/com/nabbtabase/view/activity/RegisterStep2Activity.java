package dp.com.nabbtabase.view.activity;

import android.app.Dialog;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.content.Intent;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.List;

import dp.com.nabbtabase.R;
import dp.com.nabbtabase.databinding.ActivityRegisterStep2Binding;
import dp.com.nabbtabase.servise.model.pojo.Country;
import dp.com.nabbtabase.servise.model.request.RegisterRequest;
import dp.com.nabbtabase.servise.repository.RegisterRepository;
import dp.com.nabbtabase.utils.ConfigurationFile;
import dp.com.nabbtabase.utils.CustomUtils;
import dp.com.nabbtabase.view.callback.CallBackInterface;
import dp.com.nabbtabase.viewmodel.RegisterStep2ViewModel;


public class RegisterStep2Activity extends BaseActivity implements CallBackInterface {

    private RegisterStep2ViewModel viewModel;
    private ActivityRegisterStep2Binding binding;
    private RegisterRequest registerRequest;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRegisterRequest();
        viewModel = ViewModelProviders.of(this).get(RegisterStep2ViewModel.class);
        viewModel.setRegisterRequest(registerRequest);
        viewModel.setCallBackInterface(this, RegisterStep2Activity.this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register_step2);
        binding.setViewModel(viewModel);
        RegisterRepository.getInstance().setCallBackInterface(this, RegisterStep2Activity.this);
        observableViewModel(viewModel);
    }

    public void setRegisterRequest() {
        registerRequest = (RegisterRequest) getIntent().getSerializableExtra(ConfigurationFile.IntentConstants.REGISTER_STEP1_DATA);
    }

    @Override
    public void updateUi(int code) {
        switch (code) {
            case ConfigurationFile.Constants.SELECT_COUNTRY: {
                Snackbar.make(binding.clRoot, "Select Country First", Snackbar.LENGTH_LONG).show();
                break;
            }
            case ConfigurationFile.Constants.FILL_ALL_DATA_ERROR_CODE: {
                Snackbar.make(binding.clRoot, R.string.fill_all_data_error_message, Snackbar.LENGTH_LONG).show();
                break;
            }
            case ConfigurationFile.Constants.SUCCESS_CODE_SECOND: {
                Intent intent = new Intent(this, ActivationActivity.class);
                startActivity(intent);
                finishAffinity();
                //CustomUtils.getInstance().moveToContainer(RegisterStep2Activity.this);
                break;
            }
            case ConfigurationFile.Constants.NO_INTERNET_CONNECTION_CODE: {
                Snackbar.make(binding.clRoot, R.string.no_internet_connection_error_message, Snackbar.LENGTH_LONG).show();
                break;
            }
            case ConfigurationFile.Constants.SHOW_PROGRESS_DIALOG: {
                CustomUtils.getInstance().showProgressDialog(this);
                viewModel.callSignUpRepository();
            }
        }
    }

    @Override
    public void errorMessage(String error) {
        System.out.println("Error message  :" + error);
        Log.e("Error message", error);
        Snackbar.make(binding.clRoot, error, Snackbar.LENGTH_LONG).show();
    }

    public void policy(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = (this).getLayoutInflater();
        View filter = inflater.inflate(R.layout.bottom_dialog_layout, null);
        builder.setView(filter);
        builder.setCancelable(true);
        TextView title= filter.findViewById(R.id.tv_title);
        TextView content=filter.findViewById(R.id.tv_content);
        title.setText(getString(R.string.policy));
        Dialog dialog = builder.create();
        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.BOTTOM;
        window.setAttributes(wlp);
        filter.findViewById(R.id.iv_dialog_back).setOnClickListener(v -> dialog.dismiss());
        //window.setBackgroundDrawableResource(Color.WHITE);
        dialog.show();
    }

    public void terms(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = (this).getLayoutInflater();
        View filter = inflater.inflate(R.layout.bottom_dialog_layout, null);
        builder.setView(filter);
        builder.setCancelable(true);
        TextView title= filter.findViewById(R.id.tv_title);
        TextView content=filter.findViewById(R.id.tv_content);
        title.setText(getString(R.string.terms_and_conditions));
        content.setText(getString(R.string.terms_content));
        Dialog dialog = builder.create();
        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.BOTTOM;
        window.setAttributes(wlp);
        filter.findViewById(R.id.iv_dialog_back).setOnClickListener(v -> dialog.dismiss());
        //window.setBackgroundDrawableResource(Color.WHITE);
        dialog.show();
    }

    private void observableViewModel(RegisterStep2ViewModel viewModel) {
        viewModel.getCountries().observe(this, countries -> viewModel.adapter.setCountries(countries));
    }


}
