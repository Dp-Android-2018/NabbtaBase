package dp.com.nabbtabase.view.activity;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;
import android.content.Intent;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.snackbar.Snackbar;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import dp.com.nabbtabase.R;
import dp.com.nabbtabase.application.MyApp;
import dp.com.nabbtabase.databinding.ActivityCardBinding;
import dp.com.nabbtabase.servise.model.pojo.CartProduct;
import dp.com.nabbtabase.servise.repository.CartProductsRepository;
import dp.com.nabbtabase.servise.repository.DeleteItemFromCartRepository;
import dp.com.nabbtabase.utils.ConfigurationFile;
import dp.com.nabbtabase.utils.CustomUtils;
import dp.com.nabbtabase.view.adapter.CartAdapter;
import dp.com.nabbtabase.view.callback.DeleteCartItemListiner;
import dp.com.nabbtabase.view.callback.UpdateCartItemInterFace;
import dp.com.nabbtabase.viewmodel.ActionBarViewModel;
import dp.com.nabbtabase.viewmodel.CartViewModel;

public class CartActivity extends BaseActivity implements DeleteCartItemListiner, UpdateCartItemInterFace {
    CartViewModel viewModel;
    ActivityCardBinding binding;
    CartAdapter adapter;
    ObservableList<CartProduct> cartProducts;
    double cartProducttotalSum = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cartProducts = new ObservableArrayList<>();
        CustomUtils.getInstance().showProgressDialog(this);
        viewModel = ViewModelProviders.of(this).get(CartViewModel.class);
        observViewModel(viewModel);
        binding = DataBindingUtil.setContentView(CartActivity.this, R.layout.activity_card);
        MyApp.setBinding(binding);
        adapter = new CartAdapter();
        binding.rvCartProducts.setAdapter(adapter);
        binding.rvCartProducts.setLayoutManager(new LinearLayoutManager(this));
        binding.setViewModel(viewModel);
        binding.actionBar.setViewModel(new ActionBarViewModel(this, false, false, true));
        if (CustomUtils.getInstance().getAppLanguage(this).equals("ar")) {
            binding.actionBar.ivBack.setRotation(180);
        }
        DeleteItemFromCartRepository.getInstance().setDeleteCartItemListiner(this);
        CartProductsRepository.getInstance().setCartItemInterFace(this);
    }

    public void observViewModel(CartViewModel viewModel) {
        viewModel.getCartProducts().observe(this, cartProducts -> {
            if (cartProducts != null) {
                System.out.println("Card Products Size :" + cartProducts.size());
                this.cartProducts.clear();
                this.cartProducts.addAll(cartProducts);
                MyApp.setNotificationCounter(cartProducts.size());
                resetRecyclerView();
                for (int i = 0; i < cartProducts.size(); i++) {
                    System.out.println("for iteration : " + i);
                    cartProducttotalSum += cartProducts.get(i).getTotal();
                }
                System.out.println("cartProducttotalSum : " + cartProducttotalSum);
                MyApp.setTotal(cartProducttotalSum);
                CustomUtils.getInstance().cancelDialog();
            }
        });
    }

    public void resetRecyclerView() {
        adapter.setCartProducts(cartProducts);
        adapter.notifyDataSetChanged();
        binding.rvCartProducts.setAdapter(adapter);
        binding.rvCartProducts.invalidate();
        setView(cartProducts.size() > 0 ? false : true);

    }

    @Override
    public void itemDeleted(LiveData<Integer> code, int cartId, double total) {
        System.out.println("listener called : " + code);
        code.observe(this, integer -> {
            if (integer == ConfigurationFile.Constants.SUCCESS_CODE) {
                Snackbar.make(binding.clRoot, R.string.cart_item_deleted_message, Snackbar.LENGTH_LONG).show();
                MyApp.setTotal(MyApp.getTotal() - total);
                System.out.println("Card Id :" + cartId);
                MyApp.setNotificationCounter(MyApp.getNotificationCounter() - 1);
                binding.actionBar.getViewModel().notificationCounter.set(String.valueOf(MyApp.getNotificationCounter()));
//
                for (CartProduct cartProduct : cartProducts) {
                    System.out.println("Card Id " + cartProduct.getId());
                    if (cartProduct.getProduct().getId() == cartId) {
                        System.out.println("Card Id Data deleted:" + cartProduct.getId());
                        cartProducts.remove(cartProduct);
                        resetRecyclerView();
                        break;
                    }
                }
            }
        });
    }


    public void setView(boolean emptyData) {
        if (emptyData) {
            binding.rvCartProducts.setVisibility(View.GONE);
            binding.ivNoData.setVisibility(View.VISIBLE);
            binding.tvEmtyData.setVisibility(View.VISIBLE);
        } else {
            binding.rvCartProducts.setVisibility(View.VISIBLE);
            binding.ivNoData.setVisibility(View.GONE);
            binding.tvEmtyData.setVisibility(View.GONE);
        }
    }

    @Override
    public void itemUpdated(int code) {
        CustomUtils.getInstance().cancelDialog();
        if (code == ConfigurationFile.Constants.SUCCESS_CODE) {
            Snackbar.make(binding.clRoot, R.string.item_updated_successfully, Snackbar.LENGTH_LONG).show();
        } else {
            Snackbar.make(binding.clRoot, R.string.fail_to_update_cart, Snackbar.LENGTH_LONG).show();
        }
    }

    public void proceed(View view) {
        if (cartProducts.size() > 0) {
            Intent intent = new Intent(this, DeliveryOptionsActivity.class);
            startActivity(intent);
        } else {
            Snackbar.make(binding.clRoot, R.string.cart_empty, Snackbar.LENGTH_LONG).show();
        }
    }
}
