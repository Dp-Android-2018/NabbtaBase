package dp.com.nabbtabase.view.fragment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import dp.com.nabbtabase.R;
import dp.com.nabbtabase.databinding.FragmentServicesBinding;
import dp.com.nabbtabase.servise.model.pojo.ServiceContent;
import dp.com.nabbtabase.view.adapter.ServicesAdapter;
import dp.com.nabbtabase.viewmodel.OffersViewModel;
import dp.com.nabbtabase.viewmodel.ServicesViewModel;

public class ServicesFragment extends Fragment {

    FragmentServicesBinding binding;
    ServicesAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_services,container,false);
        adapter=new ServicesAdapter();
        binding.rvServices.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        binding.rvServices.setAdapter(adapter);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final ServicesViewModel viewModel=ViewModelProviders.of(this).get(ServicesViewModel.class);
        observableViewModel(viewModel);
    }
    public void observableViewModel(ServicesViewModel viewModel){
       viewModel.getServices().observe(this, serviceContents -> {
           if(serviceContents!=null){
               adapter.setServices(serviceContents);
           }
       });
    }
}
