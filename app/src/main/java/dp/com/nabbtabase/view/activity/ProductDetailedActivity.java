package dp.com.nabbtabase.view.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import dp.com.nabbtabase.R;
import dp.com.nabbtabase.databinding.ActivityProductDetailedBinding;
import dp.com.nabbtabase.viewmodel.ProductDetailedViewModel;

public class ProductDetailedActivity extends AppCompatActivity {
    ProductDetailedViewModel viewModel;
    ActivityProductDetailedBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel=ViewModelProviders.of(this).get(ProductDetailedViewModel.class);
        binding=DataBindingUtil.setContentView(this, R.layout.activity_product_detailed);
        binding.setViewModel(viewModel);
    }
}
