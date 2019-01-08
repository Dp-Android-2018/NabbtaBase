package dp.com.nabbtabase.view.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.roughike.bottombar.OnTabSelectListener;

import dp.com.nabbtabase.R;
import dp.com.nabbtabase.databinding.ActivityContainerBinding;
import dp.com.nabbtabase.view.fragment.AccountFragment;
import dp.com.nabbtabase.view.fragment.HomeFragment;
import dp.com.nabbtabase.view.fragment.OffersFragment;
import dp.com.nabbtabase.view.fragment.ProductsFragment;
import dp.com.nabbtabase.view.fragment.ServicesFragment;
import dp.com.nabbtabase.viewmodel.ActionBarViewModel;
import dp.com.nabbtabase.viewmodel.ContainerViewModel;

public class ContainerActivity extends AppCompatActivity {

    ContainerViewModel viewModel;
    ActivityContainerBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(ContainerViewModel.class);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_container);
        binding.setViewModel(viewModel);
        binding.bottomBar.setDefaultTab(R.id.main_tap);
        setActionBar();
        bottombar();
    }

    public void setActionBar(){
        binding.actionBar.setViewModel(new ActionBarViewModel(this,true,true,false));
    }

    public void bottombar() {
        binding.bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(int tabId) {
                switch (tabId) {
                    case R.id.products: {
                        ProductsFragment products = new ProductsFragment();
                        navigationFragments(products);
                        break;
                    }
                    case R.id.service: {
                        ServicesFragment servicesFragment = new ServicesFragment();
                        navigationFragments(servicesFragment);
                        break;
                    }
                    case R.id.main_tap: {
                        HomeFragment homeFragment = new HomeFragment();
                        navigationFragments(homeFragment);
                        break;
                    }
                    case R.id.offer: {
                        OffersFragment offersFragment = new OffersFragment();
                        navigationFragments(offersFragment);
                        break;
                    }
                    case R.id.profile: {
                        AccountFragment accountFragment = new AccountFragment();
                        navigationFragments(accountFragment);
                        break;
                    }
                }
            }
        });
    }

    public void navigationFragments(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStack();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame, fragment);
        fragmentTransaction.commit();
    }
}
