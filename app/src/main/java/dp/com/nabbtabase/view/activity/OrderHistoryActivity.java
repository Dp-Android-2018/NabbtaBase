package dp.com.nabbtabase.view.activity;

import androidx.lifecycle.ViewModelProviders;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.view.View;

import dp.com.nabbtabase.R;
import dp.com.nabbtabase.databinding.ActivityOrderHistoryBinding;
import dp.com.nabbtabase.utils.CustomUtils;
import dp.com.nabbtabase.view.adapter.OrderHistoryAdapter;
import dp.com.nabbtabase.viewmodel.ActionBarViewModel;
import dp.com.nabbtabase.viewmodel.OrderHistoryViewModel;

public class OrderHistoryActivity extends BaseActivity {

    ActivityOrderHistoryBinding binding;
    OrderHistoryViewModel viewModel;
    OrderHistoryAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_order_history);
        CustomUtils.getInstance().showProgressDialog(this);
        viewModel = ViewModelProviders.of(this).get(OrderHistoryViewModel.class);
        adapter = new OrderHistoryAdapter();
        binding.actionBar.setViewModel(new ActionBarViewModel(this, false, false, true));
        if (CustomUtils.getInstance().getAppLanguage(this).equals("ar")) {
            binding.actionBar.ivBack.setRotation(180);
        }
        binding.rvOrders.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        binding.rvOrders.setAdapter(adapter);
        viewModel.getOrderHistory().observe(this, orderItems -> {
            if (orderItems != null) {
                if (orderItems.size() <= 0) {
                    binding.rvOrders.setVisibility(View.GONE);
                    binding.tvNoOrders.setVisibility(View.VISIBLE);
                } else {
                    binding.rvOrders.setVisibility(View.VISIBLE);
                    binding.tvNoOrders.setVisibility(View.GONE);
                }
                adapter.setHistoryItems(orderItems);
                CustomUtils.getInstance().cancelDialog();
            }
        });
    }

}
