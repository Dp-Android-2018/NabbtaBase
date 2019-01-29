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
import dp.com.nabbtabase.application.MyApp;
import dp.com.nabbtabase.databinding.FragmentHomeBinding;
import dp.com.nabbtabase.utils.CustomUtils;
import dp.com.nabbtabase.view.adapter.ProductsAdapter;
import dp.com.nabbtabase.viewmodel.HomeViewModel;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private ProductsAdapter bestSellerAdapter;
    private ProductsAdapter offersAdapter;
    private ProductsAdapter newArrivalsAdapter;
    boolean loading;
    HomeViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        loading = false;
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        bestSellerAdapter = new ProductsAdapter(true);
        offersAdapter = new ProductsAdapter(true);
        newArrivalsAdapter = new ProductsAdapter(true);
        setRecycler();
        View view = binding.getRoot();
        return view;
    }

    public void setRecycler() {
        binding.rvBestseller.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.rvBestseller.setAdapter(bestSellerAdapter);
        binding.rvOffers.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.rvOffers.setAdapter(offersAdapter);
        binding.rvNew.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.rvNew.setAdapter(newArrivalsAdapter);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        CustomUtils.getInstance().showProgressDialog(getActivity());
        viewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        observableViewModel(viewModel);

    }

    private void observableViewModel(HomeViewModel viewModel) {
        viewModel.getBestSeller().observe(this, products -> {
            if (products != null) {
                if (products.size() > 0) {
                    binding.rvBestseller.setVisibility(View.VISIBLE);
                    binding.tvNoBestSeller.setVisibility(View.GONE);
                    bestSellerAdapter.setProducts(products);
                    if (!loading) {
                        CustomUtils.getInstance().cancelDialog();
                        loading = true;
                    }
                }
            } else {
                binding.rvBestseller.setVisibility(View.GONE);
                binding.tvNoBestSeller.setVisibility(View.VISIBLE);
            }
        });

        viewModel.getNewArrival().observe(this, products -> {
            if (products != null) {
                if (products.size() > 0) {
                    binding.rvNew.setVisibility(View.VISIBLE);
                    binding.tvNoNewArrival.setVisibility(View.GONE);
                    newArrivalsAdapter.setProducts(products);
                    if (!loading) {
                        CustomUtils.getInstance().cancelDialog();
                        loading = true;
                    }
                }
            } else {
                binding.rvNew.setVisibility(View.GONE);
                binding.tvNoNewArrival.setVisibility(View.VISIBLE);
            }


        });

        viewModel.getOffers().observe(this, products -> {
            if (products != null) {
                if (products.size() > 0) {
                    binding.tvNoOffers.setVisibility(View.GONE);
                    binding.rvOffers.setVisibility(View.VISIBLE);
                    offersAdapter.setProducts(products);
                    if (!loading) {
                        CustomUtils.getInstance().cancelDialog();
                        loading = true;
                    }
                } else {
                    binding.tvNoOffers.setVisibility(View.VISIBLE);
                    binding.rvOffers.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        observableViewModel(viewModel);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        MyApp.getInstance().destorySelectedProductsMap();
    }
}
