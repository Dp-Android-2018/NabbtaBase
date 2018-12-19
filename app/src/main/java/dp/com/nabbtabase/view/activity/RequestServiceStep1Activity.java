package dp.com.nabbtabase.view.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import dp.com.nabbtabase.R;
import dp.com.nabbtabase.databinding.ActivityRequestServiceStep1Binding;
import dp.com.nabbtabase.utils.ConfigurationFile;

public class RequestServiceStep1Activity extends AppCompatActivity {

    ActivityRequestServiceStep1Binding binding;
    int id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id=getIntent().getIntExtra(ConfigurationFile.IntentConstants.SERVICE_ID,0);
        binding=DataBindingUtil.setContentView(this,R.layout.activity_request_service_step1);
    }
}
