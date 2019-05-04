package dp.com.nabbtabase.view.activity;

import androidx.lifecycle.ViewModelProviders;
import android.content.Intent;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.snackbar.Snackbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.view.View;

import dp.com.nabbtabase.R;
import dp.com.nabbtabase.ViewModelFactory.OrderDeatiledViewModelFactory;
import dp.com.nabbtabase.databinding.ActivityOrderDetailedBinding;
import dp.com.nabbtabase.servise.model.pojo.OrderDetailed;
import dp.com.nabbtabase.utils.ConfigurationFile;
import dp.com.nabbtabase.utils.CustomUtils;
import dp.com.nabbtabase.view.adapter.ResetAdapter;
import dp.com.nabbtabase.viewmodel.ActionBarViewModel;
import dp.com.nabbtabase.viewmodel.OrderDetailedViewModel;

public class OrderDetailedActivity extends BaseActivity {


    ActivityOrderDetailedBinding binding;
    OrderDetailedViewModel viewModel;
    int orderId;
    ResetAdapter adapter;
    OrderDetailed orderDetailed;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        orderId = getIntent().getIntExtra(ConfigurationFile.IntentConstants.ORDER_ID, 0);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_order_detailed);
        binding.actionBar.setViewModel(new ActionBarViewModel(this, false, false, true));
        if (CustomUtils.getInstance().getAppLanguage(this).equals("ar")) {
            binding.actionBar.ivBack.setRotation(180);
        }
        CustomUtils.getInstance().showProgressDialog(this);
        viewModel = ViewModelProviders.of(this, new OrderDeatiledViewModelFactory(this.getApplication(), orderId)).get(OrderDetailedViewModel.class);
        adapter = new ResetAdapter();
        binding.rvOrders.setAdapter(adapter);
        binding.rvOrders.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        viewModel.getOrder().observe(this, orderDetailed -> {
            adapter.setResetProducts(orderDetailed.getProducts());
            this.orderDetailed = orderDetailed;
            setView();
        });
    }

    public void setView() {
        binding.tvDate.setText(getDate());
        binding.tvEstDate.setText(getEstDate());
        binding.tvOrderNumber.setText(getCode());
        binding.tvTotal.setText(getProductsTotal());
        binding.tvShippingCost.setText(getShippingCost());
        binding.tvAllPayed.setText(getTotal());
        binding.paymentMethod.setText(getPayMethod());
        setStatusImage();
    }

    public void setStatusImage() {
        switch (orderDetailed.getStatusCode()) {
            case 0: {
                binding.ivStatus.setImageResource(R.drawable.image_status0);
                binding.btCancel.setVisibility(View.VISIBLE);
                break;
            }
            case 1: {
                binding.ivStatus.setImageResource(R.drawable.image_status1);
                binding.btCancel.setVisibility(View.GONE);
                break;
            }
            case 2: {
                binding.ivStatus.setImageResource(R.drawable.image_status2);
                binding.btCancel.setVisibility(View.GONE);
                break;
            }
            case 3: {
                binding.ivStatus.setImageResource(R.drawable.image_status3);
                binding.btCancel.setVisibility(View.GONE);
                break;
            }
            case 4: {
                binding.ivStatus.setImageResource(R.drawable.image_status4);
                binding.btCancel.setVisibility(View.GONE);
                break;
            }
            case 5: {
                binding.ivStatus.setImageResource(R.drawable.image_status5);
                binding.btCancel.setVisibility(View.GONE);
                break;
            }
        }
        CustomUtils.getInstance().cancelDialog();
    }


    public String getDate() {
        return orderDetailed.getDate();
    }

    public String getEstDate() {
        return orderDetailed.getDeliveryDate() != null ? orderDetailed.getDeliveryDate() : "";
    }

    public String getCode() {
        return getString(R.string.order_number) + orderDetailed.getCode();
    }

    public String getTotal() {
        return getString(R.string.total) + orderDetailed.getTotal();
    }

    public String getShippingCost() {
        return getString(R.string.shipping_cost) + orderDetailed.getShippingCost();
    }

    public String getProductsTotal() {
        return getString(R.string.products_total) + orderDetailed.getProductsTotal();
    }

    public String getPayMethod() {
        return getString(R.string.payment_info) + orderDetailed.getPaymentMethod().getName();
    }

    public void deleteOrder(View view) {
        viewModel.getDeleteOrderCode().observe(this, integer -> {
            if (integer == ConfigurationFile.Constants.SUCCESS_CODE) {
                Snackbar.make(binding.clRoot, R.string.order_deleted_successfully, Snackbar.LENGTH_LONG).show();
                Intent intent = new Intent(this, OrderHistoryActivity.class);
                startActivity(intent);
                //this.finishAffinity();
            }
        });
    }
}
