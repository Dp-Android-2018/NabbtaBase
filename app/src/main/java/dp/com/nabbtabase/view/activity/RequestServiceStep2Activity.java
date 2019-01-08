package dp.com.nabbtabase.view.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableField;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;

import dp.com.nabbtabase.R;
import dp.com.nabbtabase.ServiceRequestViewModelFactory;
import dp.com.nabbtabase.databinding.ActivityRequestServiceStep2Binding;
import dp.com.nabbtabase.servise.model.request.ServiceRequest;
import dp.com.nabbtabase.utils.ConfigurationFile;
import dp.com.nabbtabase.utils.CustomUtils;
import dp.com.nabbtabase.utils.ValidationUtils;
import dp.com.nabbtabase.viewmodel.RequestServiceStep2ViewModel;

public class RequestServiceStep2Activity extends AppCompatActivity {

    private ActivityRequestServiceStep2Binding binding;
    private ServiceRequest mServiceRequest;
    private RequestServiceStep2ViewModel viewModel;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mServiceRequest=getIntent().getParcelableExtra(ConfigurationFile.IntentConstants.SERVICE_REQUEST_1STEP_DATA);
        binding =DataBindingUtil.setContentView(this, R.layout.activity_request_service_step2);
    }

    public void getLocation(View view){
        CustomUtils.getInstance().startPlacePicker(RequestServiceStep2Activity.this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data!=null&&requestCode==ConfigurationFile.Constants.PLACE_PICKER_REQUEST){
            if(resultCode==RESULT_OK) {
                Place place = PlacePicker.getPlace(this, data);
                LatLng latLng = place.getLatLng();
                mServiceRequest.setLang(latLng.longitude);
                mServiceRequest.setLat(latLng.latitude);
                binding.tvAddress.setText(place.getAddress());
            }
        }
    }

    public void request(View view){
        if(ValidationUtils.isEmpty(binding.etFirstName.getText().toString())||
           ValidationUtils.isEmpty(binding.etLastName.getText().toString())||
           ValidationUtils.isEmpty(binding.etEmail.getText().toString())||
                ValidationUtils.isEmpty(binding.tvAddress.getText().toString())||
                ValidationUtils.isEmpty(binding.etPhone.getText().toString())){
            Snackbar.make(binding.clRoot,R.string.fill_all_data_error_message,Snackbar.LENGTH_LONG).show();
        }else {
            mServiceRequest.setFirstName(binding.etFirstName.getText().toString());
            mServiceRequest.setLastName(binding.etLastName.getText().toString());
            mServiceRequest.setEmail(binding.etEmail.getText().toString());
            mServiceRequest.setPhone(binding.etPhone.getText().toString());
            viewModel=ViewModelProviders.of(this,new ServiceRequestViewModelFactory(this.getApplication(),mServiceRequest)).get(RequestServiceStep2ViewModel.class);
            observeViewModel(viewModel);
        }
    }

    public void observeViewModel(RequestServiceStep2ViewModel viewModel){
        viewModel.getCode().observe(this, integer -> {
            if(integer==ConfigurationFile.Constants.SUCCESS_CODE_SECOND){
                Intent intent=new Intent(this,RequestServiceStep3Activity.class);
                startActivity(intent);
                finishAffinity();
            }
        });
    }
}
