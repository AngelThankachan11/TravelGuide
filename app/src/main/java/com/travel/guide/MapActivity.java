package com.travel.guide;

import static com.travel.guide.utils.AppConstants.LOCATION_REFRESH_DISTANCE;
import static com.travel.guide.utils.AppConstants.LOCATION_REFRESH_TIME;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.travel.guide.databinding.ActivityMapBinding;
import com.travel.guide.fragments.HomeFragment;
import com.travel.guide.utils.GoogleProgressDialog;
import com.travel.guide.utils.PermissionCaller;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapActivity extends BaseActivity implements OnMapReadyCallback, GoogleMap.OnCameraMoveListener, GoogleMap.OnCameraIdleListener,
        GoogleMap.OnMarkerDragListener {

    ActivityMapBinding binding;
    Location currentLocation;
    FusedLocationProviderClient fusedLocationProviderClient;
    private static final int REQUEST_CODE = 101;
    GoogleMap mMap;
    Marker marker;
    protected LocationManager locationManager;
    private LocationListener mLocationListener;
    GoogleProgressDialog progressDialog;
    //MapPresenter presenter;

    String latitude, longitude, addressLine, city;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_map);
        //presenter = new MapPresenter();
        //presenter.setView(this);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        progressDialog = new GoogleProgressDialog(MapActivity.this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String location = binding.searchView.getQuery().toString();
                List<Address> addressList = null;

                if (location != null || location.equals("")) {

                    Geocoder geocoder = new Geocoder(MapActivity.this);
                    try {
                        addressList = geocoder.getFromLocationName(location, 1);
                        if (addressList != null) {
                            if (addressList.size() > 0) {
                                updateAddress(addressList.get(0).getLatitude(), addressList.get(0).getLongitude());
                            } else {
                                toast("Location not found");
                            }
                        } else {
                            toast("Location not found");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        mapFragment.getMapAsync(this);
        fetchLocation();
    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.txtConfirmLocation:
                Intent intent = new Intent(MapActivity.this, MainActivity.class);
                intent.putExtra("city",binding.txtAreaName.getText().toString());
                intent .putExtra("addressline",binding.txtAddressLine.getText().toString());
                startActivity(intent);
                //presenter.CallSaveAddress(this, latitude, longitude, pincode, addressLine, city, userData.getACCESS_TOKEN());
                break;
            default:
                break;
        }
    }

    private void fetchLocation() {
        String permissions[] = new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
        if (PermissionCaller.getInstance(this).isListOfPermission(permissions, REQUEST_CODE)) {

            locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
            progressDialog.showDialog();
            mLocationListener = new LocationListener() {
                @Override
                public void onLocationChanged(final Location location) {
                    if (location != null) {
                        progressDialog.dismiss();
                        setCurrentLocationOnMap(location, "locationManager");
                    }/* else {
                    ActivateFusedLocation();
                }*/
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {
                }

                @Override
                public void onProviderEnabled(String provider) {
                }

                @Override
                public void onProviderDisabled(String provider) {
                }
            };

            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, LOCATION_REFRESH_TIME,
                    LOCATION_REFRESH_DISTANCE, mLocationListener);
        }
    }

    private void ActivateFusedLocation() {
        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
            return;
        }

        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    currentLocation = location;
                    setCurrentLocationOnMap(currentLocation, "fusedManager");
                }
            }
        });
    }

    private void ClearPreviousView() {
        if (marker != null) {
            marker.remove();
            mMap.clear();
        }
    }

    private void setCurrentLocationOnMap(Location location, String from) {
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        ClearPreviousView();
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
        marker = mMap.addMarker(new MarkerOptions().position(latLng).title("Your address"));
        marker.setDraggable(true);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14));

        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(this, Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            fixUserAddress(addresses);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                binding.searchView.setQuery(addresses.get(0).getAddressLine(0), false);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        if (from.equalsIgnoreCase("locationManager")) {
            locationManager.removeUpdates(mLocationListener);
        }
    }

    private void updateAddress(double latitude, double longitude) {
        LatLng latLng = new LatLng(latitude, longitude);
        ClearPreviousView();
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
        marker = mMap.addMarker(new MarkerOptions().position(latLng).title("Your address"));
        marker.setDraggable(true);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14));

        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(this, Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1);
            fixUserAddress(addresses);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                binding.searchView.setQuery(addresses.get(0).getAddressLine(0), false);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        //mMap.setMyLocationEnabled(true);
        if (currentLocation!=null) {
            ClearPreviousView();
            LatLng latLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
            MarkerOptions markerOptions = new MarkerOptions().position(latLng).title("I am here!");
            mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14));
            marker = mMap.addMarker(markerOptions);
            marker.setDraggable(true);

            Geocoder geocoder;
            List<Address> addresses;
            geocoder = new Geocoder(this, Locale.getDefault());

            try {
                addresses = geocoder.getFromLocation(currentLocation.getLatitude(), currentLocation.getLongitude(), 1);
                fixUserAddress(addresses);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    binding.searchView.setQuery(addresses.get(0).getAddressLine(0), false);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        mMap.setOnMarkerDragListener(MapActivity.this);
    }

    private void fixUserAddress(List<Address> addresses){
        latitude = ""+addresses.get(0).getLatitude();
        longitude = ""+addresses.get(0).getLongitude();
        //pincode = addresses.get(0).getPostalCode();
        addressLine = addresses.get(0).getAddressLine(0);
        city = addresses.get(0).getSubAdminArea();

        binding.txtAreaName.setText(addresses.get(0).getSubAdminArea());
        binding.txtAddressLine.setText(addresses.get(0).getAddressLine(0));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    fetchLocation();
                }
                break;
        }
    }

   /* @Override
    public void onSaveAddressSucess(SaveAddressResBean item) {
        if (item.isStatus()){
            userData.SaveAddress(item.getAddressId(), addressLine, latitude, longitude);
            Intent intent = new Intent(MapActivity.this, HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        toast(item.getMessage());
    }*/

    @Override
    public void onCameraIdle() {

    }

    @Override
    public void onCameraMove() {

    }

    @Override
    public void onMarkerDrag(@NonNull Marker marker) {

    }

    @Override
    public void onMarkerDragEnd(@NonNull Marker marker) {
        updateAddress(marker.getPosition().latitude, marker.getPosition().longitude);
    }

    @Override
    public void onMarkerDragStart(@NonNull Marker marker) {

    }
}