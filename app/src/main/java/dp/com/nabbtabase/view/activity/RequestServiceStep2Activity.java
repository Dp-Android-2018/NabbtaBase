package dp.com.nabbtabase.view.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.material.snackbar.Snackbar;

import dp.com.nabbtabase.R;
import dp.com.nabbtabase.ViewModelFactory.ServiceRequestViewModelFactory;
import dp.com.nabbtabase.databinding.ActivityRequestServiceStep2Binding;
import dp.com.nabbtabase.servise.model.request.ServiceRequest;
import dp.com.nabbtabase.utils.ConfigurationFile;
import dp.com.nabbtabase.utils.CustomUtils;
import dp.com.nabbtabase.utils.ValidationUtils;
import dp.com.nabbtabase.view.callback.CallBackInterface;
import dp.com.nabbtabase.viewmodel.ActionBarViewModel;
import dp.com.nabbtabase.viewmodel.RequestServiceStep2ViewModel;

import static dp.com.nabbtabase.utils.ConfigurationFile.Constants.ERROR_DIALOG_REQUEST;
import static dp.com.nabbtabase.utils.ConfigurationFile.Constants.PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION;
import static dp.com.nabbtabase.utils.ConfigurationFile.Constants.PERMISSIONS_REQUEST_ENABLE_GPS;
import static dp.com.nabbtabase.utils.ConfigurationFile.IntentConstants.SELECTED_ADDRESS;
import static dp.com.nabbtabase.utils.ConfigurationFile.IntentConstants.SELECTED_LANG;
import static dp.com.nabbtabase.utils.ConfigurationFile.IntentConstants.SELECTED_LAT;
import static dp.com.nabbtabase.utils.ConfigurationFile.IntentConstants.START_PLACE_PICKER;

public class RequestServiceStep2Activity extends BaseActivity implements CallBackInterface {

    private ActivityRequestServiceStep2Binding binding;
    private ServiceRequest mServiceRequest;
    private RequestServiceStep2ViewModel viewModel;
    private boolean mLocationPermissionGranted = false;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mServiceRequest = (ServiceRequest) getIntent().getSerializableExtra(ConfigurationFile.IntentConstants.SERVICE_REQUEST_1STEP_DATA);
        System.out.println(" services id :" + mServiceRequest.getServiceId());
        binding = DataBindingUtil.setContentView(this, R.layout.activity_request_service_step2);
        binding.actionBar.setViewModel(new ActionBarViewModel(this, false, false, true));
        if (CustomUtils.getInstance().getAppLanguage(this).equals("ar")) {
            binding.actionBar.ivBack.setRotation(180);
        }
        viewModel = ViewModelProviders.of(this, new ServiceRequestViewModelFactory(this.getApplication(), mServiceRequest, this)).get(RequestServiceStep2ViewModel.class);
    }

    public void getLocation(View view) {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivityForResult(intent, START_PLACE_PICKER);
        //CustomUtils.getInstance().startPlacePicker(RequestServiceStep2Activity.this);
    }

    private void getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                }
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (checkMapServices()) {
            if (mLocationPermissionGranted) {

            } else {
                getLocationPermission();
            }
        }
    }

    private boolean checkMapServices() {
        if (isServicesOK()) {
            if (isMapsEnabled()) {
                return true;
            }
        }
        return false;
    }

    public boolean isServicesOK() {
        Log.d(TAG, "isServicesOK: checking google services version");

        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(RequestServiceStep2Activity.this);

        if (available == ConnectionResult.SUCCESS) {
            //everything is fine and the user can make map requests
            Log.d(TAG, "isServicesOK: Google Play Services is working");
            return true;
        } else if (GoogleApiAvailability.getInstance().isUserResolvableError(available)) {
            //an error occured but we can resolve it
            Log.d(TAG, "isServicesOK: an error occured but we can fix it");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(RequestServiceStep2Activity.this, available, ERROR_DIALOG_REQUEST);
            dialog.show();
        } else {
            Toast.makeText(this, "You can't make map requests", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    public boolean isMapsEnabled() {
        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();
            return false;
        }
        return true;
    }

    private void buildAlertMessageNoGps() {
        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setMessage("This application requires GPS to work properly, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", (dialog, id) -> {
                    Intent enableGpsIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivityForResult(enableGpsIntent, PERMISSIONS_REQUEST_ENABLE_GPS);
                });
        final android.app.AlertDialog alert = builder.create();
        alert.show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (requestCode == START_PLACE_PICKER) {
                if (resultCode == RESULT_OK) {
                    mServiceRequest.setLat(data.getDoubleExtra(SELECTED_LAT, 0));
                    mServiceRequest.setLang(data.getDoubleExtra(SELECTED_LANG, 0));
                    binding.tvAddress.setText(data.getStringExtra(SELECTED_ADDRESS));
                    //binding.etPickUpLocation.setText(request.getPickupAddress());
                }
            }
        }
    }

    public void request(View view) {
        System.out.println("next clicked is : " + "clicked");
        if (ValidationUtils.isEmpty(binding.etFirstName.getText().toString()) ||
                ValidationUtils.isEmpty(binding.etLastName.getText().toString()) ||
                ValidationUtils.isEmpty(binding.etEmail.getText().toString()) ||
                ValidationUtils.isEmpty(binding.tvAddress.getText().toString()) ||
                ValidationUtils.isEmpty(binding.etPhone.getText().toString())) {
            Snackbar.make(binding.clRoot, R.string.fill_all_data_error_message, Snackbar.LENGTH_LONG).show();
        } else {
            mServiceRequest.setFirstName(binding.etFirstName.getText().toString());
            mServiceRequest.setLastName(binding.etLastName.getText().toString());
            mServiceRequest.setEmail(binding.etEmail.getText().toString());
            mServiceRequest.setPhone(binding.etPhone.getText().toString());
            System.out.println("data request is : " + mServiceRequest.toString());
            observeViewModel(mServiceRequest);
            CustomUtils.getInstance().showProgressDialog(this);
        }

    }

    public void observeViewModel(ServiceRequest serviceRequest) {
        viewModel.getCode(serviceRequest).observe(this, integer -> {
            System.out.println("code is : " + integer);
            if (integer == ConfigurationFile.Constants.SUCCESS_CODE_SECOND) {
                Intent intent = new Intent(this, RequestServiceStep3Activity.class);
                startActivity(intent);
                finishAffinity();
            } else if (integer == ConfigurationFile.Constants.INVALED_EMAIL_PASSWORD) {
                CustomUtils.getInstance().logout(this);
//            } else if (integer == ConfigurationFile.Constants.INVALED_DATA_CODE) {
//                Snackbar.make(binding.clRoot, R.string.fill_all_data_error_message, Snackbar.LENGTH_LONG).show();
            }
        });
    }


    @Override
    public void updateUi(int code) {

    }

    @Override
    public void errorMessage(String error) {
        CustomUtils.getInstance().cancelDialog();
        Snackbar.make(binding.clRoot, error, Snackbar.LENGTH_LONG).show();
    }
}
