package dp.com.nabbtabase.view.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.View;

import dp.com.nabbtabase.ViewModelFactory.CreateOrderViewModelFactory;
import dp.com.nabbtabase.R;
import dp.com.nabbtabase.databinding.ActivityPaymentBinding;
import dp.com.nabbtabase.servise.model.request.CreateOrderRequest;
import dp.com.nabbtabase.utils.ConfigurationFile;
import dp.com.nabbtabase.utils.CustomUtils;
import dp.com.nabbtabase.view.viewholder.PaymentViewModel;
import dp.com.nabbtabase.viewmodel.ActionBarViewModel;

public class PaymentActivity extends BaseActivity {

    ActivityPaymentBinding binding;
    private int addressId;
    CreateOrderRequest request;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addressId=getIntent().getIntExtra(ConfigurationFile.IntentConstants.ADDRESS_ID,0);
        System.out.println("Code in pay ment : "+addressId);
        request=new CreateOrderRequest();
        request.setAddressId(addressId);
        request.setBillingAddressId(addressId);
        request.setPaymentId(1);
        binding=DataBindingUtil.setContentView(this,R.layout.activity_payment);
        binding.actionBar.setViewModel(new ActionBarViewModel(this,false,false,true));
        if(CustomUtils.getInstance().getAppLanguage(this).equals("ar")) {
            binding.actionBar.ivBack.setRotation(180);
        }
    }

    public void placeOrder(View view){
        System.out.println("enter next ");
        PaymentViewModel viewModel=ViewModelProviders.of(this,new CreateOrderViewModelFactory(this.getApplication(),request)).get(PaymentViewModel.class);
        viewModel.getCode().observe(this, integer -> {
            if(integer==ConfigurationFile.Constants.SUCCESS_CODE_SECOND){
                System.out.println("Code is : "+integer);
                Snackbar.make(binding.clRoot,"Order Placed Successfully",Snackbar.LENGTH_LONG).show();
                Intent intent=new Intent(this,OrderHistoryActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
