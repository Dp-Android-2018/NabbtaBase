package dp.com.nabbtabase.view.activity;

import android.content.Intent;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.View;

import dp.com.nabbtabase.R;
import dp.com.nabbtabase.databinding.ActivityRequestServiseStep3Binding;
import dp.com.nabbtabase.viewmodel.ActionBarViewModel;

public class RequestServiceStep3Activity extends BaseActivity {

    ActivityRequestServiseStep3Binding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_request_servise_step3);
        binding.actionBar.setViewModel(new ActionBarViewModel(this, false, false, false));
    }

    public void done(View view) {
        Intent intent = new Intent(this, ContainerActivity.class);
        startActivity(intent);
        finishAffinity();
    }
}
