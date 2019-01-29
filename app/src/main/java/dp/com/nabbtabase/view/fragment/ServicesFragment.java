package dp.com.nabbtabase.view.fragment;

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

import dp.com.nabbtabase.R;
import dp.com.nabbtabase.databinding.FragmentServicesBinding;
import dp.com.nabbtabase.utils.CustomUtils;
import dp.com.nabbtabase.view.adapter.ServicesAdapter;
import dp.com.nabbtabase.viewmodel.ServicesViewModel;

public class ServicesFragment extends Fragment {

    FragmentServicesBinding binding;
    ServicesAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_services, container, false);
        adapter = new ServicesAdapter();
        binding.rvServices.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        binding.rvServices.setAdapter(adapter);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        CustomUtils.getInstance().showProgressDialog(getActivity());
        final ServicesViewModel viewModel = ViewModelProviders.of(this).get(ServicesViewModel.class);
        observableViewModel(viewModel);
    }

    public void observableViewModel(ServicesViewModel viewModel) {
        viewModel.getServices().observe(this, serviceContents -> {
            if (serviceContents != null) {
                adapter.setServices(serviceContents);
                CustomUtils.getInstance().cancelDialog();
            }
        });
    }
}
