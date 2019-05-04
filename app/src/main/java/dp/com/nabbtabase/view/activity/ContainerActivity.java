package dp.com.nabbtabase.view.activity;

import androidx.lifecycle.ViewModelProviders;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.Nullable;
import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.view.View;

import dp.com.nabbtabase.R;
import dp.com.nabbtabase.application.MyApp;
import dp.com.nabbtabase.databinding.ActivityContainerBinding;
import dp.com.nabbtabase.view.fragment.AccountFragment;
import dp.com.nabbtabase.view.fragment.HomeFragment;
import dp.com.nabbtabase.view.fragment.OffersFragment;
import dp.com.nabbtabase.view.fragment.ProductsFragment;
import dp.com.nabbtabase.view.fragment.ServicesFragment;
import dp.com.nabbtabase.viewmodel.ActionBarViewModel;
import dp.com.nabbtabase.viewmodel.CartViewModel;
import dp.com.nabbtabase.viewmodel.ContainerViewModel;

public class ContainerActivity extends BaseActivity {

    ContainerViewModel viewModel;
    CartViewModel cartViewModel;
    ActionBarViewModel actionBarViewModel;
    ActivityContainerBinding binding;
    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(ContainerViewModel.class);
        cartViewModel = ViewModelProviders.of(this).get(CartViewModel.class);
        actionBarViewModel = new ActionBarViewModel(this, false, true, false);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_container);
        binding.setViewModel(viewModel);
        binding.bottomBar.setDefaultTab(R.id.main_tap);
        binding.actionBar.setViewModel(actionBarViewModel);
        //cartViewModel.getCartProducts().observe(this, cartProducts -> {
        //MyApp.setNotificationCounter(cartProducts.size());
        System.out.println("items in cart container :" + MyApp.getNotificationCounter());
        actionBarViewModel.notificationCounter.set(String.valueOf(MyApp.getNotificationCounter()));
        //});
        bottombar();
    }


    public void bottombar() {
        binding.bottomBar.setOnTabSelectListener(tabId -> {
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
        });
    }

    public void navigationFragments(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStack();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        //if(CustomUtils.getInstance().getSaveUserObject(getApplicationContext())!=null) {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Snackbar.make(binding.clRoot, R.string.press_twice, Snackbar.LENGTH_LONG).show();
        new Handler().postDelayed(() -> doubleBackToExitPressedOnce = false, 2000);
    }
    //}


    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("items in cart container :" + MyApp.getNotificationCounter());
        actionBarViewModel.notificationCounter.set(String.valueOf(MyApp.getNotificationCounter()));
        //binding.bottomBar.setDefaultTab(R.id.main_tap);
    }

    @Override
    protected void onStart() {
        super.onStart();
        System.out.println("items in cart container :" + MyApp.getNotificationCounter());
        actionBarViewModel.notificationCounter.set(String.valueOf(MyApp.getNotificationCounter()));
        if (MyApp.getNotificationCounter() <= 0) {
            actionBarViewModel.cartVisibailty.set(View.GONE);
        } else {
            actionBarViewModel.cartVisibailty.set(View.VISIBLE);
        }

    }
}
