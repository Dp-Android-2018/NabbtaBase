package dp.com.nabbtabase.viewmodel;

import android.app.Activity;
import android.app.Application;
import android.app.Dialog;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.content.Intent;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.databinding.ObservableList;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import dp.com.nabbtabase.R;
import dp.com.nabbtabase.servise.model.pojo.City;
import dp.com.nabbtabase.servise.model.pojo.Country;
import dp.com.nabbtabase.servise.model.request.ShippingAddressRequest;
import dp.com.nabbtabase.servise.model.response.ShippingAddressResponse;
import dp.com.nabbtabase.servise.repository.CountryRepository;
import dp.com.nabbtabase.servise.repository.ShippingAddressRepository;
import dp.com.nabbtabase.utils.ConfigurationFile;
import dp.com.nabbtabase.utils.ValidationUtils;
import dp.com.nabbtabase.view.adapter.PopupRecyclerAdapter;
import dp.com.nabbtabase.view.callback.CallBackInterface;
import dp.com.nabbtabase.view.callback.CloseCountryDialogInterface;
import retrofit2.Response;

public class CreateShippingAddressViewModel extends AndroidViewModel implements CloseCountryDialogInterface {

    private final LiveData<List<Country>> countries;
    Application application;
    private ObservableList<City> cities;
    public PopupRecyclerAdapter adapter;
    private String select;
    private Dialog dialog;
    private boolean countrySelected;
    ShippingAddressRequest shippingAddressRequest;
    public ObservableField<String> cityName;
    public ObservableField<String> countryName;
    public ObservableField<String> firstName;
    public ObservableField<String> lastName;
    public ObservableField<String> phone;
    public ObservableField<String> address;
    CallBackInterface callBackInterface;
    Context context;
    LiveData<Response<ShippingAddressResponse>> response;

    public CreateShippingAddressViewModel(@NonNull Application application, Context context, CallBackInterface callBackInterface) {
        super(application);
        this.application = application;
        this.context = context;
        this.callBackInterface = callBackInterface;
        countries = CountryRepository.getInstance().getCountries(application);
        initVariables();
    }

    public LiveData<List<Country>> getCountries() {
        return countries;
    }

    public void initVariables() {
        countrySelected = false;
        cities = new ObservableArrayList<>();
        shippingAddressRequest = new ShippingAddressRequest();
        adapter = new PopupRecyclerAdapter(this);
        countryName = new ObservableField<>();
        cityName = new ObservableField<>();
        firstName = new ObservableField<>();
        lastName = new ObservableField<>();
        phone = new ObservableField<>();
        address = new ObservableField<>();
    }

    public void  getShippingAddressCode() {
//        System.out.println("done method :" + "flag");
//        if (ValidationUtils.isEmpty(firstName.get()) ||
//                ValidationUtils.isEmpty(lastName.get()) ||
//                ValidationUtils.isEmpty(countryName.get()) ||
//                ValidationUtils.isEmpty(cityName.get()) ||
//                ValidationUtils.isEmpty(address.get()) ||
//                ValidationUtils.isEmpty(phone.get())) {
//            System.out.println("done method :" + "data empty");
//            callBackInterface.updateUi(ConfigurationFile.Constants.FILL_ALL_DATA_ERROR_CODE);
//            //Snackbar.make(binding.clRoot, R.string.fill_all_data_error_message, Snackbar.LENGTH_LONG).show();
        //} else {
            shippingAddressRequest.setFirstName(firstName.get());
            shippingAddressRequest.setLastName(lastName.get());
            shippingAddressRequest.setPhone(phone.get());
            shippingAddressRequest.setAddress(address.get());
            response=ShippingAddressRepository.getInstance().shippingAddress(application,shippingAddressRequest);
       // }
    }

    public LiveData<Response<ShippingAddressResponse>> getResponse() {
        return response;
    }

    public void country(View view) {
        select = "country";
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View filter = inflater.inflate(R.layout.country_dialog, null);
        builder.setView(filter);
        builder.setCancelable(true);
        dialog = builder.create();
        Window window = dialog.getWindow();
        window.setBackgroundDrawableResource(R.color.transparent);
        TextView title = filter.findViewById(R.id.tv_title);
        RecyclerView recyclerView = filter.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        title.setText("Country");
        //Log.i("Size in vm ",""+getCountries().getValue().size());
        adapter.setCountries(getCountries().getValue());
        recyclerView.setAdapter(adapter);
        dialog.show();
        // Log.i("Countries size vm",""+countries.getValue().size());

    }

    public void city(View view) {
        // Log.i("Countries size vm",""+countries.getValue().size());
        if (!countrySelected) {
            Log.i("select", "" + countrySelected);
            callBackInterface.errorMessage("select country first");
            //Snackbar.make(binding.clRoot, "select country first", Snackbar.LENGTH_LONG).show();
            return;
        }
        select = "city";
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View filter = inflater.inflate(R.layout.country_dialog, null);
        builder.setView(filter);
        builder.setCancelable(true);
        dialog = builder.create();
        Window window = dialog.getWindow();
        window.setBackgroundDrawableResource(R.color.transparent);
        TextView title = filter.findViewById(R.id.tv_title);
        RecyclerView recyclerView = filter.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        title.setText("City");
        adapter.setCities(cities);
        recyclerView.setAdapter(adapter);
        dialog.show();
    }

    @Override
    public void onListItemClicked(int postion) {
        if (select.equals("country")) {
            cityName.set("");
            cities.clear();
            cities.addAll(getCountries().getValue().get(postion).getCities());
            countryName.set(getCountries().getValue().get(postion).getName());
            countrySelected = true;
        } else if (select.equals("city")) {
            cityName.set(cities.get(postion).getName());
            shippingAddressRequest.setCityId(cities.get(postion).getId());
        }
        dialog.dismiss();
    }
}
