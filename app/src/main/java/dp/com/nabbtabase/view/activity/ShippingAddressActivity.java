package dp.com.nabbtabase.view.activity;

import android.app.Dialog;
import androidx.lifecycle.ViewModelProviders;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import dp.com.nabbtabase.R;
import dp.com.nabbtabase.databinding.ActivityShippingAddressBinding;
import dp.com.nabbtabase.servise.model.pojo.Address;
import dp.com.nabbtabase.servise.model.pojo.City;
import dp.com.nabbtabase.servise.model.pojo.LoginRegisterContent;
import dp.com.nabbtabase.servise.model.request.ShippingAddressRequest;
import dp.com.nabbtabase.utils.ConfigurationFile;
import dp.com.nabbtabase.utils.CustomUtils;
import dp.com.nabbtabase.utils.ValidationUtils;
import dp.com.nabbtabase.view.adapter.PopupRecyclerAdapter;
import dp.com.nabbtabase.view.callback.CloseCountryDialogInterface;
import dp.com.nabbtabase.viewmodel.ActionBarViewModel;
import dp.com.nabbtabase.viewmodel.ShippingAddressViewModel;

public class ShippingAddressActivity extends AppCompatActivity implements CloseCountryDialogInterface {
    private List<City> cities;
    public PopupRecyclerAdapter adapter;
    private String select;
    private Dialog dialog;
    private boolean countrySelected;
    ShippingAddressViewModel viewModel;
    ActivityShippingAddressBinding binding;
    ShippingAddressRequest shippingAddressRequest;
    LoginRegisterContent data;
    Address address;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(ShippingAddressViewModel.class);
        observableViewModel(viewModel);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_shipping_address);
        initVariables();
        binding.actionBar.setViewModel(new ActionBarViewModel(this, false, false, true));
        if (CustomUtils.getInstance().getAppLanguage(this).equals("ar")) {
            binding.actionBar.ivBack.setRotation(180);
        }
    }

    public void initVariables() {
        countrySelected = false;
        cities = new ArrayList<>();
        shippingAddressRequest = new ShippingAddressRequest();
        adapter = new PopupRecyclerAdapter(this);
        data = CustomUtils.getInstance().getSaveUserObject(this);
        address = data.getAddress();
        System.out.println("address : " + address.toString());
        shippingAddressRequest.setCityId(address.getCity().getId());
        setDataToview();
    }

    public void setDataToview() {
        binding.etFirstName.setText(address != null ? address.getFirstName() : "");
        binding.etLastName.setText(address != null ? address.getLastName() : "");
        binding.etCountry.setText(address != null ? address.getCountry().getName() : "");
        binding.etCity.setText(address != null ? address.getCity().getName() : "");
        binding.etPhone.setText(address != null ? address.getPhone() : "");
        binding.etAddress.setText(address != null ? address.getAddress() : "");
    }

    public void country(View view) {
        select = "country";
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View filter = inflater.inflate(R.layout.country_dialog, null);
        builder.setView(filter);
        builder.setCancelable(true);
        dialog = builder.create();
        Window window = dialog.getWindow();
        window.setBackgroundDrawableResource(R.color.transparent);
        TextView title = filter.findViewById(R.id.tv_title);
        RecyclerView recyclerView = filter.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        title.setText("Country");
        //Log.i("Size in vm ",""+getCountries().getValue().size());
        recyclerView.setAdapter(adapter);
        dialog.show();
        // Log.i("Countries size vm",""+countries.getValue().size());

    }

    public void city(View view) {
        // Log.i("Countries size vm",""+countries.getValue().size());
        if (!countrySelected) {
            Log.i("select", "" + countrySelected);
            Snackbar.make(binding.clShippingAddressRoot, R.string.select_country, Snackbar.LENGTH_LONG).show();
            return;
        }
        select = "city";
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View filter = inflater.inflate(R.layout.country_dialog, null);
        builder.setView(filter);
        builder.setCancelable(true);
        dialog = builder.create();
        Window window = dialog.getWindow();
        window.setBackgroundDrawableResource(R.color.transparent);
        TextView title = filter.findViewById(R.id.tv_title);
        RecyclerView recyclerView = filter.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        title.setText("City");
        adapter.setCities(cities);
        recyclerView.setAdapter(adapter);
        dialog.show();
    }

    @Override
    public void onListItemClicked(int postion) {
        if (select.equals("country")) {
            address = new Address();
            binding.etCity.setText("");
            cities.addAll(viewModel.getCountries().getValue().get(postion).getCities());
            binding.etCountry.setText(viewModel.getCountries().getValue().get(postion).getName());
            address.setCountry(viewModel.getCountries().getValue().get(postion));
            countrySelected = true;
        } else if (select.equals("city")) {
            binding.etCity.setText(cities.get(postion).getName());
            shippingAddressRequest.setCityId(cities.get(postion).getId());
            address.setCity(cities.get(postion));
        }
        dialog.dismiss();
    }

    public void observableViewModel(ShippingAddressViewModel viewModel) {
        viewModel.getCountries().observe(this, countries -> {
            if (countries != null) {
                adapter.setCountries(countries);
            }
        });
//        viewModel.getCode().observe(this, integer -> {
//            System.out.println("observer : " + integer);
//            if ( integer == ConfigurationFile.Constants.SUCCESS_CODE_SECOND) {
//                data.setAddress(address);
//                CustomUtils.getInstance().saveDataToPrefs(data,ShippingAddressActivity.this);
//                Snackbar.make(binding.clShippingAddressRoot, R.string.data_updated_successfully, Snackbar.LENGTH_LONG).show();
//            }
//        });
    }

    public void done(View view) {
        System.out.println("done method :" + "flag");
        if (ValidationUtils.isEmpty(binding.etFirstName.getText().toString()) ||
                ValidationUtils.isEmpty(binding.etLastName.getText().toString()) ||
                ValidationUtils.isEmpty(binding.etCountry.getText().toString()) ||
                ValidationUtils.isEmpty(binding.etCity.getText().toString()) ||
                ValidationUtils.isEmpty(binding.etAddress.getText().toString()) ||
                ValidationUtils.isEmpty(binding.etPhone.getText().toString())) {
            System.out.println("done method :" + "data empty");
            Snackbar.make(binding.clShippingAddressRoot, R.string.fill_all_data_error_message, Snackbar.LENGTH_LONG).show();
        } else {
            shippingAddressRequest.setFirstName(binding.etFirstName.getText().toString());
            shippingAddressRequest.setLastName(binding.etLastName.getText().toString());
            shippingAddressRequest.setPhone(binding.etPhone.getText().toString());
            shippingAddressRequest.setAddress(binding.etAddress.getText().toString());

            address.setFirstName(binding.etFirstName.getText().toString());
            address.setLastName(binding.etLastName.getText().toString());
            address.setPhone(binding.etPhone.getText().toString());
            address.setAddress(binding.etAddress.getText().toString());
            System.out.println("done method :" + "data completed");
            viewModel.getCode(shippingAddressRequest).observe(this, integer -> {
                System.out.println("Code update is : " + integer);
                if (integer == ConfigurationFile.Constants.SUCCESS_CODE) {
                    Snackbar.make(binding.clShippingAddressRoot, R.string.shipping_address_update, Snackbar.LENGTH_LONG).show();
                    data.setAddress(address);
                    CustomUtils.getInstance().saveDataToPrefs(data, this);
                }
            });
        }
    }
}
