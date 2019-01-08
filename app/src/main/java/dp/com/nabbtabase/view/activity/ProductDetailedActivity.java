package dp.com.nabbtabase.view.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import dp.com.nabbtabase.MyViewModelFactory;
import dp.com.nabbtabase.R;
import dp.com.nabbtabase.application.MyApp;
import dp.com.nabbtabase.databinding.ActivityProductDetailedBinding;
import dp.com.nabbtabase.servise.model.pojo.Product;
import dp.com.nabbtabase.servise.repository.CreateCommentRepository;
import dp.com.nabbtabase.servise.repository.DeleteItemFromCartRepository;
import dp.com.nabbtabase.utils.ConfigurationFile;
import dp.com.nabbtabase.utils.CustomUtils;
import dp.com.nabbtabase.view.adapter.CommentsAdapter;
import dp.com.nabbtabase.view.adapter.ViewPagerAdapter;
import dp.com.nabbtabase.view.callback.CallBackInterface;
import dp.com.nabbtabase.viewmodel.ActionBarViewModel;
import dp.com.nabbtabase.viewmodel.ProductDetailedViewModel;
import me.crosswall.lib.coverflow.CoverFlow;

public class ProductDetailedActivity extends AppCompatActivity implements CallBackInterface {
    ActivityProductDetailedBinding binding;
    private Product product;
    CommentsAdapter adapter;
    ProductDetailedViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        product = (Product) getIntent().getSerializableExtra(ConfigurationFile.IntentConstants.PRODUCT_DATA);
        viewModel = ViewModelProviders.of(this, new MyViewModelFactory(this.getApplication(), product)).get(ProductDetailedViewModel.class);
        viewModel.setCallBackInterface(this, ProductDetailedActivity.this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_detailed);
        adapter = new CommentsAdapter();
        binding.rvComments.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        binding.rvComments.setAdapter(adapter);
        CreateCommentRepository.getInstance().setCallBackInterface(this);
        binding.setViewModel(viewModel);
        binding.actionBar.setViewModel(new ActionBarViewModel(this,true,true,true));
        initViewPager();
        observeViewModel(viewModel);
    }


    public void initViewPager() {
        binding.pagerContainer.setOverlapEnabled(true);
        final ViewPager viewPager = binding.pagerContainer.getViewPager();
        ViewPagerAdapter adapter = new ViewPagerAdapter(ProductDetailedActivity.this, product.getImageUrls());
        viewPager.setOffscreenPageLimit(adapter.getCount());
        viewPager.setAdapter(adapter);

        new CoverFlow.Builder().with(viewPager)
                .scale(0.3f)
                .pagerMargin(-80)
                .spaceSize(0f)
                .build();
    }

    @Override
    public void updateUi(int code) {
        switch (code) {
            case ConfigurationFile.Constants.FILL_ALL_DATA_ERROR_CODE: {
                showSnackBar(getString(R.string.fill_all_data_error_message));
                break;
            }
            case ConfigurationFile.Constants.SUCCESS_CODE_SECOND: {
                showSnackBar(getString(R.string.comment_successfully_message));
                break;
            }
            case ConfigurationFile.Constants.ALREADY_ACTIVATED: {
                showSnackBar(getString(R.string.comment_before_message));
                break;
            }
        }
    }

    @Override
    public void errorMessage(String error) {

    }

    private void observeViewModel(ProductDetailedViewModel viewModel) {
        viewModel.getComments().observe(this, comments -> {
            if (comments != null) {
                adapter.setComments(comments);
            }
        });
    }

    public void addToCart(View view) {
        if(viewModel.product.isInCart()){
            viewModel.getDeleteItemCode().observe(this, integer -> {
                if(integer==ConfigurationFile.Constants.SUCCESS_CODE) {
                    showSnackBar(getResources().getString(R.string.cart_item_deleted_message));
                    product.setInCart(false);
                    viewModel.inCart.set(false);
                }
            });
        }else {
            if (CustomUtils.getInstance().getSaveUserObject(this) != null) {
                viewModel.getAddToCartResponse().observe(this, stringResponseResponse -> {
                    if (stringResponseResponse.code() == ConfigurationFile.Constants.SUCCESS_CODE_SECOND) {
                        showSnackBar(getResources().getString(R.string.product_added_to_cart_message));
                        product.setInCart(true);
                        viewModel.inCart.set(true);
                    }
                });
            } else {
                showSnackBar(getString(R.string.you_must_be_Register));
            }
        }
    }

    public void showSnackBar(String message) {
        Snackbar.make(binding.clRoot, message, Snackbar.LENGTH_LONG).show();
    }
}
