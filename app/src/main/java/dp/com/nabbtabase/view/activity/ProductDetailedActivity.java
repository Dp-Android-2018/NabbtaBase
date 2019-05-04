package dp.com.nabbtabase.view.activity;

import androidx.lifecycle.ViewModelProviders;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.snackbar.Snackbar;
import androidx.viewpager.widget.ViewPager;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.view.View;

import dp.com.nabbtabase.R;
import dp.com.nabbtabase.ViewModelFactory.MyViewModelFactory;
import dp.com.nabbtabase.application.MyApp;
import dp.com.nabbtabase.databinding.ActivityProductDetailedBinding;
import dp.com.nabbtabase.servise.model.pojo.Product;
import dp.com.nabbtabase.servise.repository.CreateCommentRepository;
import dp.com.nabbtabase.utils.ConfigurationFile;
import dp.com.nabbtabase.utils.CustomUtils;
import dp.com.nabbtabase.view.adapter.CommentsAdapter;
import dp.com.nabbtabase.view.adapter.ProductsAdapter;
import dp.com.nabbtabase.view.adapter.ViewPagerAdapter;
import dp.com.nabbtabase.view.callback.CallBackInterface;
import dp.com.nabbtabase.viewmodel.ActionBarViewModel;
import dp.com.nabbtabase.viewmodel.ProductDetailedViewModel;
import me.crosswall.lib.coverflow.CoverFlow;

public class ProductDetailedActivity extends BaseActivity implements CallBackInterface {
    ActivityProductDetailedBinding binding;
    private Product product;
    CommentsAdapter adapter;
    ProductDetailedViewModel viewModel;
    ProductsAdapter relatedproductsAdapter;
    ActionBarViewModel actionBarViewModel;
    MyApp app = MyApp.getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app.createSelectedProducts();
        product = (Product) getIntent().getSerializableExtra(ConfigurationFile.IntentConstants.PRODUCT_DATA);
        viewModel = ViewModelProviders.of(this, new MyViewModelFactory(this.getApplication(), product)).get(ProductDetailedViewModel.class);
        actionBarViewModel = new ActionBarViewModel(this, true, true, true);
        viewModel.setCallBackInterface(this, ProductDetailedActivity.this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_detailed);
        System.out.println("product is in cart : " + product.isInCart());
        if (app.getSelectedProducts().containsKey(product.getId())) {
            viewModel.inCart.set(app.getSelectedProducts().get(product.getId()));
        } else {
            viewModel.inCart.set(product.isInCart());
        }
        adapter = new CommentsAdapter();
        relatedproductsAdapter = new ProductsAdapter(true);
        binding.rvComments.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        binding.rvRelatedProduct.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        binding.rvComments.setAdapter(adapter);
        binding.rvRelatedProduct.setAdapter(relatedproductsAdapter);
        CreateCommentRepository.getInstance().setCallBackInterface(this);
        binding.setViewModel(viewModel);
        binding.actionBar.setViewModel(actionBarViewModel);
        if (CustomUtils.getInstance().getAppLanguage(this).equals("ar")) {
            binding.actionBar.ivBack.setRotation(180);
        }
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
        //CustomUtils.getInstance().cancelDialog();
        switch (code) {
            case ConfigurationFile.Constants.FILL_ALL_DATA_ERROR_CODE: {
                showSnackBar(getString(R.string.fill_all_data_error_message));
                break;
            }
            case ConfigurationFile.Constants.SUCCESS_CODE_SECOND: {
                showSnackBar(getString(R.string.comment_successfully_message));
                binding.etComment.setText("");
                binding.ratingBar.setRating(0);
                break;
            }
            case ConfigurationFile.Constants.SUSBENDED: {
                showSnackBar(getString(R.string.active_account_message));
                break;
            }
            case ConfigurationFile.Constants.ALREADY_ACTIVATED: {
                showSnackBar(getString(R.string.comment_before_message));
                binding.etComment.setText("");
                binding.ratingBar.setRating(0);
                break;
            }
        }
    }

    @Override
    public void errorMessage(String error) {
        Snackbar.make(binding.clRoot, error, Snackbar.LENGTH_LONG).show();
    }

    private void observeViewModel(ProductDetailedViewModel viewModel) {
        viewModel.getComments().observe(this, comments -> {
            if (comments != null) {
                adapter.setComments(comments);
            }
        });
        viewModel.getRelatedProducts().observe(this, products -> {
            if (products != null) {
                relatedproductsAdapter.setProducts(products);
            }
        });
    }

    public void addToCart(View view) {
        if (viewModel.product.getQuantity() <= 0) {
            showSnackBar(getString(R.string.out_of_stock_message));
            return;
        } else if (viewModel.product.isInCart()) {
            CustomUtils.getInstance().showProgressDialog(this);
            viewModel.getDeleteItemCode().observe(this, integer -> {
                if (integer == ConfigurationFile.Constants.SUCCESS_CODE) {
                    showSnackBar(getResources().getString(R.string.cart_item_deleted_message));
                    MyApp.setNotificationCounter(MyApp.getNotificationCounter() - 1);
                    binding.actionBar.getViewModel().notificationCounter.set(String.valueOf(MyApp.getNotificationCounter()));
                    if (MyApp.getNotificationCounter() <= 0) {
                        actionBarViewModel.cartVisibailty.set(View.GONE);
                    } else {
                        actionBarViewModel.cartVisibailty.set(View.VISIBLE);
                    }
                    System.out.println("items in cart product detailed : ");
                    product.setInCart(false);
                    viewModel.inCart.set(false);
                    app.addProductToSelectedProducts(product.getId(), false);
                }
            });
        } else {
            CustomUtils.getInstance().showProgressDialog(this);
            if (CustomUtils.getInstance().getSaveUserObject(this) != null) {
                viewModel.getAddToCartResponse().observe(this, stringResponseResponse -> {
                    if (stringResponseResponse.code() == ConfigurationFile.Constants.SUCCESS_CODE_SECOND) {
                        showSnackBar(getResources().getString(R.string.product_added_to_cart_message));
                        MyApp.setNotificationCounter(MyApp.getNotificationCounter() + 1);
                        binding.actionBar.getViewModel().notificationCounter.set(String.valueOf(MyApp.getNotificationCounter()));
                        System.out.println("items in cart product detailed : ");
                        product.setInCart(true);
                        viewModel.inCart.set(true);
                        app.addProductToSelectedProducts(product.getId(), true);
                    } else if (stringResponseResponse.code() == ConfigurationFile.Constants.INVALED_EMAIL_PASSWORD) {
                        CustomUtils.getInstance().logout(this);
                    }
                });
            } else {
                CustomUtils.getInstance().showRegisterAlertDialog(this);
                //showSnackBar(getString(R.string.you_must_be_Register));
            }
        }
        CustomUtils.getInstance().cancelDialog();
    }

    public void showSnackBar(String message) {
        Snackbar.make(binding.clRoot, message, Snackbar.LENGTH_LONG).show();
    }
}
