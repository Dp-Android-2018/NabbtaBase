package dp.com.nabbtabase.view.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.squareup.picasso.Picasso;

import dp.com.nabbtabase.R;
import dp.com.nabbtabase.databinding.ActivityServiceDetailBinding;
import dp.com.nabbtabase.servise.model.pojo.ServiceContent;
import dp.com.nabbtabase.utils.ConfigurationFile;
import dp.com.nabbtabase.utils.CustomUtils;
import dp.com.nabbtabase.viewmodel.ActionBarViewModel;

public class ServiceDetailedActivity extends BaseActivity {

    ActivityServiceDetailBinding binding;
    private ServiceContent serviceContent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        serviceContent = (ServiceContent) getIntent().getParcelableExtra(ConfigurationFile.IntentConstants.SERVICE_DATA);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_service_detail);
        binding.actionBar.setViewModel(new ActionBarViewModel(this,false,true,true));
        if(CustomUtils.getInstance().getAppLanguage(this).equals("ar")) {
            binding.actionBar.ivBack.setRotation(180);
        }
        setDataToView();
    }

    public void setDataToView() {
        binding.tvName.setText(serviceContent.getName());
        binding.tvDescription.setText(serviceContent.getDescription());
        Picasso.with(this).load(serviceContent.getImageUrl()).into(binding.imageView25);
    }

    public void requestService(View view) {
        if(CustomUtils.getInstance().getSaveUserObject(this)==null) {
            Snackbar.make(binding.clRoot,getResources().getString(R.string.you_must_be_Register),Snackbar.LENGTH_LONG).show();
            return;
        }
        Intent intent = new Intent(this, RequestServiceStep1Activity.class);
        intent.putExtra(ConfigurationFile.IntentConstants.SERVICE_ID, serviceContent.getId());
        startActivity(intent);
    }
}
