package dp.com.nabbtabase.view.activity;

import androidx.lifecycle.ViewModelProviders;
import android.content.Intent;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.snackbar.Snackbar;
import android.view.View;
import android.widget.CompoundButton;

import dp.com.nabbtabase.R;
import dp.com.nabbtabase.databinding.ActivityDeliveryOptionsBinding;
import dp.com.nabbtabase.servise.model.pojo.LoginRegisterContent;
import dp.com.nabbtabase.utils.ConfigurationFile;
import dp.com.nabbtabase.utils.CustomUtils;
import dp.com.nabbtabase.utils.ValidationUtils;
import dp.com.nabbtabase.view.callback.CallBackInterface;
import dp.com.nabbtabase.viewmodel.ActionBarViewModel;
import dp.com.nabbtabase.viewmodel.CreateShippingAddressFactory;
import dp.com.nabbtabase.viewmodel.CreateShippingAddressViewModel;

public class DeliveryOptionsActivity extends BaseActivity implements CallBackInterface {

    ActivityDeliveryOptionsBinding binding;
    CreateShippingAddressViewModel viewModel;
    LoginRegisterContent data;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        data = CustomUtils.getInstance().getSaveUserObject(this);
        viewModel = ViewModelProviders.of(this, new CreateShippingAddressFactory(this.getApplication(), DeliveryOptionsActivity.this, this)).get(CreateShippingAddressViewModel.class);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_delivery_options);
        binding.layout.setViewModel(viewModel);
        binding.actionBar.setViewModel(new ActionBarViewModel(this, false, false, true));
        if (CustomUtils.getInstance().getAppLanguage(this).equals("ar")) {
            binding.actionBar.ivBack.setRotation(180);
        }
        observViewModel(viewModel);
        setDataToView();

    }

    public void observViewModel(CreateShippingAddressViewModel viewModel) {
        viewModel.getCountries().observe(this, countries -> {
            if (countries != null) {
                viewModel.adapter.setCountries(countries);
            }
        });
    }

    @Override
    public void updateUi(int code) {
        switch (code) {
            case ConfigurationFile.Constants.FILL_ALL_DATA_ERROR_CODE:
                errorMessage(getResources().getString(R.string.fill_all_data_error_message));
        }
    }

    @Override
    public void errorMessage(String error) {
        Snackbar.make(binding.clRoot, error, Snackbar.LENGTH_LONG).show();
    }

    public String getName() {
        return data.getAddress() != null ? data.getAddress().getFirstName() + " " + data.getAddress().getLastName() : "";
    }

    public String getAddress() {
        return data.getAddress() != null ? data.getAddress().getAddress() : "";
    }

    public String getCity() {
        return data.getAddress() != null ? data.getAddress().getCountry().getName() + "-" +
                data.getAddress().getCity().getName() : "";
    }

    public String getPhone() {
        return data.getAddress() != null ? data.getAddress().getPhone() : "";
    }

    public void setDataToView() {
        binding.tvName.setText(getName());
        binding.tvAddress.setText(getAddress());
        binding.tvCityCountry.setText(getCity());
        binding.tvPhone.setText(getPhone());
        System.out.println("my address id : " + data.getAddress().getId());
        binding.layout.getRoot().setVisibility(View.GONE);
        binding.radioGroup.check(binding.rbShipToMyAddress.getId());
        binding.rbShipToAnotherAddress.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    binding.layout.getRoot().setVisibility(View.VISIBLE);
                } else {
                    binding.layout.getRoot().setVisibility(View.GONE);
                }
            }
        });
    }

    public void next(View view) {
        Intent intent = new Intent(DeliveryOptionsActivity.this, PaymentActivity.class);
        if (binding.rbShipToAnotherAddress.isChecked()) {
            if (ValidationUtils.isEmpty(viewModel.firstName.get()) ||
                    ValidationUtils.isEmpty(viewModel.lastName.get()) ||
                    ValidationUtils.isEmpty(viewModel.countryName.get()) ||
                    ValidationUtils.isEmpty(viewModel.cityName.get()) ||
                    ValidationUtils.isEmpty(viewModel.address.get()) ||
                    ValidationUtils.isEmpty(viewModel.phone.get())) {
                errorMessage(getResources().getString(R.string.fill_all_data_error_message));
                return;
                //Snackbar.make(binding.clRoot, R.string.fill_all_data_error_message, Snackbar.LENGTH_LONG).show();
            } else {
                viewModel.getShippingAddressCode();
            }
            viewModel.getResponse().observe(this, shippingAddressResponseResponse -> {
                System.out.println("code is : " + shippingAddressResponseResponse.code());
                if (shippingAddressResponseResponse.code() == ConfigurationFile.Constants.SUCCESS_CODE_SECOND) {
                    System.out.println("Code in delivery option : " + shippingAddressResponseResponse.body().getId());
                    intent.putExtra(ConfigurationFile.IntentConstants.ADDRESS_ID, shippingAddressResponseResponse.body().getId());
                    startActivity(intent);
                } else {
                    errorMessage(getString(R.string.invaled_data_entered));
                }
            });
        } else if (binding.rbShipToMyAddress.isChecked()) {
            if (data.getAddress() != null) {
                System.out.println("my address id : " + data.getAddress().getId());
                intent.putExtra(ConfigurationFile.IntentConstants.ADDRESS_ID, data.getAddress().getId());
                startActivity(intent);
            } else {
                errorMessage("you don't have address");
            }
        }
    }
}
