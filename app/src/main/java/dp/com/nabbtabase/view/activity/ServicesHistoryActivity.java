package dp.com.nabbtabase.view.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import java.util.List;

import dp.com.nabbtabase.R;
import dp.com.nabbtabase.databinding.ActivityServicesHistoryBinding;
import dp.com.nabbtabase.servise.model.pojo.ServiceHistoryItem;
import dp.com.nabbtabase.view.adapter.ServiceHistoryAdapter;
import dp.com.nabbtabase.viewmodel.ActionBarViewModel;
import dp.com.nabbtabase.viewmodel.ServicesHistoryViewModel;

public class ServicesHistoryActivity extends AppCompatActivity {
    ActivityServicesHistoryBinding binding;
    ServiceHistoryAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ServicesHistoryViewModel viewModel=ViewModelProviders.of(this).get(ServicesHistoryViewModel.class);
        observeViewModel(viewModel);
        binding=DataBindingUtil.setContentView(this,R.layout.activity_services_history);
        adapter=new ServiceHistoryAdapter();
        binding.rvServicesHistory.setAdapter(adapter);
        binding.rvServicesHistory.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        binding.actionBar.setViewModel(new ActionBarViewModel(this,false,false,true));
    }

    public void observeViewModel(ServicesHistoryViewModel viewModel){
        viewModel.getServicesHistory().observe(this, new Observer<List<ServiceHistoryItem>>() {
            @Override
            public void onChanged(@Nullable List<ServiceHistoryItem> serviceHistoryItems) {
                if(serviceHistoryItems!=null){
                    adapter.setHistoryItems(serviceHistoryItems);
                }
            }
        });
    }
}
