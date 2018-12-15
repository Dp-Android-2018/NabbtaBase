package dp.com.nabbtabase.viewmodel;


import android.app.Activity;
import android.app.Application;
import android.app.Dialog;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import dp.com.nabbtabase.servise.model.request.RegisterRequest;
import dp.com.nabbtabase.servise.repository.CountryRepository;
import dp.com.nabbtabase.servise.repository.RegisterRepository;
import dp.com.nabbtabase.utils.ConfigurationFile;
import dp.com.nabbtabase.utils.NetWorkConnection;
import dp.com.nabbtabase.utils.ValidationUtils;
import dp.com.nabbtabase.view.adapter.PopupRecyclerAdapter;
import dp.com.nabbtabase.view.callback.CallBackInterface;
import dp.com.nabbtabase.view.callback.CloseCountryDialogInterface;

public class RegisterStep2ViewModel extends AndroidViewModel  implements CloseCountryDialogInterface {

    private RegisterRequest registerRequest;
    private CallBackInterface callBackInterface;
    private final LiveData<List<Country>> countries;
    private List<City>cities;
    private Context context;
    public PopupRecyclerAdapter adapter;
    private String select;
    private Dialog dialog;
   private Application application;
    private boolean countrySelected;
    public ObservableField<String>phone;
    public ObservableField<String> addressName;
    public ObservableField<String>countryName;
    public ObservableField<String>cityName;


    public RegisterStep2ViewModel(@NonNull Application application) {
        super(application);
        this.application=application;
        countries=CountryRepository.getInstance().getCountries(application);
        initVariables();
    }


    public void initVariables(){
        phone=new ObservableField<>();
        addressName =new ObservableField<>();
        countryName=new ObservableField<>();
        cityName=new ObservableField<>();
        cities=new ArrayList<>();
        countrySelected=false;
        adapter=new PopupRecyclerAdapter(this);
    }
    public void country(View view){
        if(NetWorkConnection.isConnectingToInternet(context)){
            select="country";
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            LayoutInflater inflater =((Activity)context).getLayoutInflater();
            View filter = inflater.inflate(R.layout.country_dialog, null);
            builder.setView(filter);
            builder.setCancelable(true);
            dialog=builder.create();
            Window window=dialog.getWindow();
            window.setBackgroundDrawableResource(R.color.transparent);
            TextView title=filter.findViewById(R.id.tv_title);
            RecyclerView recyclerView=filter.findViewById(R.id.recyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
            title.setText("Country");
            //Log.i("Size in vm ",""+getCountries().getValue().size());
            adapter.setCountries(countries.getValue());
            recyclerView.setAdapter(adapter);
            dialog.show();
        }else {
            callBackInterface.updateUi(ConfigurationFile.Constants.NO_INTERNET_CONNECTION_CODE);
        }
       // Log.i("Countries size vm",""+countries.getValue().size());

    }

    public void city(View view){
        // Log.i("Countries size vm",""+countries.getValue().size());
        if(!countrySelected){
            Log.i("select",""+countrySelected);
            callBackInterface.updateUi(ConfigurationFile.Constants.SELECT_COUNTRY);
            return;
        }
        select="city";
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater =((Activity)context).getLayoutInflater();
        View filter = inflater.inflate(R.layout.country_dialog, null);
        builder.setView(filter);
        builder.setCancelable(true);
        dialog=builder.create();
        Window window=dialog.getWindow();
        window.setBackgroundDrawableResource(R.color.transparent);
        TextView title=filter.findViewById(R.id.tv_title);
        RecyclerView recyclerView=filter.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
        title.setText("City");
        adapter.setCities(cities);
        recyclerView.setAdapter(adapter);
        dialog.show();
    }

    public void signUp(View view){
        if(ValidationUtils.isEmpty(countryName.get())||
           ValidationUtils.isEmpty(cityName.get())||
           ValidationUtils.isEmpty(phone.get())||
           ValidationUtils.isEmpty(addressName.get())){
            callBackInterface.updateUi(ConfigurationFile.Constants.FILL_ALL_DATA_ERROR_CODE);
        }else{
            registerRequest.setAddress(addressName.get());
            registerRequest.setPhone(phone.get());
            RegisterRepository.getInstance().register(application,registerRequest);
        }
    }

    public void setRegisterRequest(RegisterRequest registerRequest) {
        this.registerRequest = registerRequest;
    }

    public void setCallBackInterface(CallBackInterface callBackInterface, Context context) {
        this.callBackInterface = callBackInterface;
        this.context=context;
    }

    @Override
    public void onListItemClicked(int postion) {
        if(select.equals("country"))
        {
            cityName.set("");
            cities.addAll(countries.getValue().get(postion).getCities());
            countryName.set(countries.getValue().get(postion).getName());
            countrySelected=true;
        }else if(select.equals("city")){
            cityName.set(cities.get(postion).getName());
            registerRequest.setCityId(cities.get(postion).getId());
        }
        dialog.dismiss();
    }

    public LiveData<List<Country>> getCountries() {
        return countries;
    }
}
