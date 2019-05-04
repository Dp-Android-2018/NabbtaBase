package dp.com.nabbtabase.view.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.AutocompletePrediction;
import com.google.android.libraries.places.api.model.AutocompleteSessionToken;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.RectangularBounds;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.firebase.firestore.GeoPoint;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import dp.com.nabbtabase.R;

import static dp.com.nabbtabase.utils.ConfigurationFile.IntentConstants.SELECTED_ADDRESS;
import static dp.com.nabbtabase.utils.ConfigurationFile.IntentConstants.SELECTED_LANG;
import static dp.com.nabbtabase.utils.ConfigurationFile.IntentConstants.SELECTED_LAT;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationClient;
    double lat;
    double lang;
    String selectedAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        ImageView currentLocation = findViewById(R.id.iv_get_current_location);
        Button selectThisLocation = findViewById(R.id.bt_select_this_location);
        currentLocation.setOnClickListener(v -> getLastKnownLocation());
        selectThisLocation.setOnClickListener(v -> returndata());
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        search();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        getLastKnownLocation();
        autoComplete();
    }

    private void getLastKnownLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mFusedLocationClient.getLastLocation().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Location location = task.getResult();
                GeoPoint geoPoint = new GeoPoint(location.getLatitude(), location.getLongitude());
                //Log.d(TAG, "onComplete: latitude: " + geoPoint.getLatitude());
                //Log.d(TAG, "onComplete: longitude: " + geoPoint.getLongitude());
                lang = geoPoint.getLongitude();
                lat = geoPoint.getLatitude();
                setCameraView();
                Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                try {
                    List<Address> addresses = geocoder.getFromLocation(lat, lang, 1);
                    Address obj = addresses.get(0);

                    selectedAddress = obj.getAddressLine(0);
                    selectedAddress += "\n" + obj.getCountryName();
                    //selectedAddress += "\n" + obj.getCountryCode();
                    selectedAddress += "\n" + obj.getAdminArea();
                    //selectedAddress += "\n" + obj.getPostalCode();
                    //selectedAddress += "\n" + obj.getSubAdminArea();
                    selectedAddress += "\n" + obj.getLocality();
                    //selectedAddress += "\n" + obj.getSubThoroughfare();

                    System.out.println("full address : " + selectedAddress);
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("error is : "+e.getMessage());
                }
            }
        });

    }

    public void returndata() {
        Intent resultIntent = new Intent();
// TODO Add extras or a data URI to this intent as appropriate.
        resultIntent.putExtra(SELECTED_ADDRESS, selectedAddress);
        resultIntent.putExtra(SELECTED_LAT, lat);
        resultIntent.putExtra(SELECTED_LANG, lang);
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }

    public void autoComplete() {
        Places.initialize(getApplicationContext(), getString(R.string.google_maps_key));
// Create a new Places client instance.
        PlacesClient placesClient = Places.createClient(this);
        AutocompleteSessionToken token = AutocompleteSessionToken.newInstance();
        // Create a RectangularBounds object.
        RectangularBounds bounds = RectangularBounds.newInstance(
                new LatLng(-33.880490, 151.184363),
                new LatLng(-33.858754, 151.229596));
        // Use the builder to create a FindAutocompletePredictionsRequest.
        FindAutocompletePredictionsRequest request = FindAutocompletePredictionsRequest.builder()
// Call either setLocationBias() OR setLocationRestriction().
                .setLocationBias(bounds)
                //.setLocationRestriction(bounds)
                .setCountry("au")
                // .setTypeFilter(AutocompleteFilter.TypeFilter.ADDRESS)
                .setSessionToken(token)
                //.setQuery(query)
                .build();

        placesClient.findAutocompletePredictions(request).addOnSuccessListener((response) -> {
            for (AutocompletePrediction prediction : response.getAutocompletePredictions()) {
                Log.i("place:", prediction.getPlaceId());
                Log.i("place :", prediction.getPrimaryText(null).toString());
            }
        }).addOnFailureListener((exception) -> {
            if (exception instanceof ApiException) {
                ApiException apiException = (ApiException) exception;
                Log.e("place ", "Place not found: " + apiException.getStatusCode());
            }
        });
    }

    public void search() {
        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);

// Specify the types of place data to return.
        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG, Place.Field.ADDRESS));

// Set up a PlaceSelectionListener to handle the response.
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
//                System.out.println("place id : "+place.getAddress());
//                System.out.println("place name : "+place.getName());
//                System.out.println("Place address : "+place.getAddress());
//                System.out.println("place lat lang : "+place.getLatLng());
                lat = place.getLatLng().latitude;
                lang = place.getLatLng().longitude;
                selectedAddress = place.getName();
                setCameraView();
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                System.out.println("An error occurred: " + status);
            }
        });
    }

    public void setCameraView() {
        mMap.clear();
        mMap.addMarker(new MarkerOptions().position(new LatLng(lat, lang)));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lang), 17.0f));
    }
}
