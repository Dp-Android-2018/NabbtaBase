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
import dp.com.nabbtabase.databinding.FragmentHomeBinding;
import dp.com.nabbtabase.servise.model.pojo.Product;
import dp.com.nabbtabase.view.adapter.ProductsAdapter;
import dp.com.nabbtabase.viewmodel.HomeViewModel;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private ProductsAdapter bestSellerAdapter;
    private ProductsAdapter offersAdapter;
    private ProductsAdapter newArrivalsAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding=DataBindingUtil.inflate(inflater, R.layout.fragment_home,container,false);
        bestSellerAdapter=new ProductsAdapter();
        offersAdapter=new ProductsAdapter();
        newArrivalsAdapter=new ProductsAdapter();
        setRecycler();
        View view=binding.getRoot();
        return view;
    }

    public void setRecycler(){
        binding.rvBestseller.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        binding.rvBestseller.setAdapter(bestSellerAdapter);
        binding.rvOffers.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        binding.rvOffers.setAdapter(offersAdapter);
        binding.rvNew.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        binding.rvNew.setAdapter(newArrivalsAdapter);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final HomeViewModel viewModel=ViewModelProviders.of(this).get(HomeViewModel.class);
        observableViewModel(viewModel);

    }

    private void observableViewModel(HomeViewModel viewModel){
        viewModel.getBestSeller().observe(this, products -> {
            if (products!=null){
                bestSellerAdapter.setProducts(products);
            }
        });

        viewModel.getNewArrival().observe(this, products -> {
            if (products!=null){
                newArrivalsAdapter.setProducts(products);
            }
        });

        viewModel.getOffers().observe(this, products -> {
            if(products!=null){
                offersAdapter.setProducts(products);
            }
        });
    }
}
