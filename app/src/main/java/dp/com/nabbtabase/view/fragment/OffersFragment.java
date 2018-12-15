package dp.com.nabbtabase.view.fragment;


import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import dp.com.nabbtabase.R;
import dp.com.nabbtabase.databinding.FragmentOffersBinding;
import dp.com.nabbtabase.servise.model.pojo.Product;
import dp.com.nabbtabase.view.adapter.ProductsAdapter;
import dp.com.nabbtabase.viewmodel.OffersViewModel;

public class OffersFragment extends Fragment {

    private FragmentOffersBinding binding;
    private ProductsAdapter offersAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding=DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.fragment_offers,container,false);
        offersAdapter=new ProductsAdapter();
        binding.rvOffers.setLayoutManager(new GridLayoutManager(getContext(),2));
        binding.rvOffers.setAdapter(offersAdapter);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final OffersViewModel viewModel=ViewModelProviders.of(this).get(OffersViewModel.class);
        observableViewModel(viewModel);
    }
    public void observableViewModel(OffersViewModel viewModel){
        viewModel.getOffers().observe(this, products -> {
            if(products!=null){
                offersAdapter.setProducts(products);
            }
        });
    }
}
