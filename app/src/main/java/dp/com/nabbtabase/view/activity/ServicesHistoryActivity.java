package dp.com.nabbtabase.view.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import java.util.List;

import dp.com.nabbtabase.R;
import dp.com.nabbtabase.databinding.ActivityServicesHistoryBinding;
import dp.com.nabbtabase.servise.model.pojo.ServiceHistoryItem;
import dp.com.nabbtabase.utils.CustomUtils;
import dp.com.nabbtabase.view.adapter.ServiceHistoryAdapter;
import dp.com.nabbtabase.viewmodel.ActionBarViewModel;
import dp.com.nabbtabase.viewmodel.ServicesHistoryViewModel;

public class ServicesHistoryActivity extends BaseActivity {
    ActivityServicesHistoryBinding binding;
    ServiceHistoryAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CustomUtils.getInstance().showProgressDialog(this);
        ServicesHistoryViewModel viewModel=ViewModelProviders.of(this).get(ServicesHistoryViewModel.class);
        observeViewModel(viewModel);
        binding=DataBindingUtil.setContentView(this,R.layout.activity_services_history);
        adapter=new ServiceHistoryAdapter();
        binding.rvServicesHistory.setAdapter(adapter);
        binding.rvServicesHistory.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        binding.actionBar.setViewModel(new ActionBarViewModel(this,false,false,true));
        if(CustomUtils.getInstance().getAppLanguage(this).equals("ar")) {
            binding.actionBar.ivBack.setRotation(180);
        }
    }

    public void observeViewModel(ServicesHistoryViewModel viewModel){
        viewModel.getServicesHistory().observe(this, serviceHistoryItems -> {
            if(serviceHistoryItems!=null){
                if(serviceHistoryItems.size()<=0){
                    binding.rvServicesHistory.setVisibility(View.GONE);
                    binding.tvNoServices.setVisibility(View.VISIBLE);
                }else {
                    binding.rvServicesHistory.setVisibility(View.VISIBLE);
                    binding.tvNoServices.setVisibility(View.GONE);
                }
                adapter.setHistoryItems(serviceHistoryItems);
                CustomUtils.getInstance().cancelDialog();
            }
        });
    }
}
