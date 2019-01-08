package dp.com.nabbtabase.view.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.google.gson.Gson;

import dp.com.nabbtabase.R;
import dp.com.nabbtabase.databinding.ActivityServiceDetailBinding;
import dp.com.nabbtabase.servise.model.pojo.ServiceContent;
import dp.com.nabbtabase.utils.ConfigurationFile;

public class ServiceDetailedActivity extends AppCompatActivity {

    ActivityServiceDetailBinding binding;
    private ServiceContent serviceContent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        serviceContent = (ServiceContent) getIntent().getParcelableExtra(ConfigurationFile.IntentConstants.SERVICE_DATA);
        System.out.println("object is :  "+new Gson().toJson(serviceContent));
        System.out.println("name : "+serviceContent.getName());

        binding = DataBindingUtil.setContentView(this, R.layout.activity_service_detail);
        setDataToView();
    }

    public void setDataToView() {
        binding.tvName.setText(serviceContent.getName());
        binding.tvDescription.setText(serviceContent.getDescription());
    }

    public void requestService(View view) {
        Intent intent = new Intent(this, RequestServiceStep1Activity.class);
        intent.putExtra(ConfigurationFile.IntentConstants.SERVICE_ID, serviceContent.getId());
        startActivity(intent);
    }
}
