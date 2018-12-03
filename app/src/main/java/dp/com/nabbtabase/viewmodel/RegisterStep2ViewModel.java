package dp.com.nabbtabase.viewmodel;


import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import java.util.List;

import dp.com.nabbtabase.servise.model.pojo.Country;
import dp.com.nabbtabase.servise.model.request.RegisterRequest;
import dp.com.nabbtabase.view.callback.CallBackInterface;

public class RegisterStep2ViewModel extends AndroidViewModel {

    private RegisterRequest registerRequest;
    private CallBackInterface callBackInterface;
    private LiveData<List<Country>> countries;
    public ObservableField<String>phone;
    public ObservableField<String>address;
    public ObservableField<String>countryName;
    public ObservableField<String>cityName;



    public RegisterStep2ViewModel(@NonNull Application application) {
        super(application);
        initVariables();
    }


    public void initVariables(){
        phone=new ObservableField<>();
        address=new ObservableField<>();
        countryName=new ObservableField<>();
        cityName=new ObservableField<>();
    }
    public void setRegisterRequest(RegisterRequest registerRequest) {
        this.registerRequest = registerRequest;
    }

    public void setCallBackInterface(CallBackInterface callBackInterface) {
        this.callBackInterface = callBackInterface;
    }
}
