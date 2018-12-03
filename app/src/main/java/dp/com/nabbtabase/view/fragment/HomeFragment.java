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
import dp.com.nabbtabase.databinding.FragmentHomeBinding;
import dp.com.nabbtabase.viewmodel.HomeViewModel;

public class HomeFragment extends Fragment {
    HomeViewModel viewModel;
    FragmentHomeBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewModel=ViewModelProviders.of(this).get(HomeViewModel.class);
        binding=DataBindingUtil.inflate(inflater, R.layout.fragment_home,container,false);
        binding.setViewModel(viewModel);
        View view=binding.getRoot();
        setRecycler();
        return view;
    }

    public void setRecycler(){
        binding.rvBestseller.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        binding.rvOffers.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        binding.rvNew.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
    }
}
