package com.knitandroid.greatandroidproject;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import androidx.core.app.ActivityCompat;

import static android.content.Context.LOCATION_SERVICE;



public class LocationGetter {
    public Context context;

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    private double lat = 50, lon = 50;
    public String address="";
    public LocationManager mLocationManager;
    final LocationListener mLocationListener =new LocationListener() {
        @Override
        public void onLocationChanged(final Location location) {
            lat = location.getLatitude();
            lon = location.getLongitude();
            address=getAddress(lat,lon);
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    };

    LocationGetter(Context context){
        this.context=context;
        mLocationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);

    }

    public String getLocation() {

        int TIME = 1000;
        int DISTANCE = 20;
        LocationManager locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
        android.location.Location location = null;

        try {
            boolean isGPSEnabled = locationManager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);
            if (!isGPSEnabled) {
            } else if (isGPSEnabled) {
                try {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, TIME, DISTANCE, mLocationListener);
                } catch (SecurityException e) {
                    Log.v("ck", "permission deny");
                }

                if (locationManager != null) {
                    if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        return null;
                    }
                    location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    if (location != null) {
                        lat = location.getLatitude();
                        lon = location.getLongitude();
                        return getAddress(lat,lon);
                    }

                }
            }

            boolean isNetworkEnabled = locationManager
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            if (isNetworkEnabled) {
                locationManager.requestLocationUpdates(
                        LocationManager.NETWORK_PROVIDER, TIME, DISTANCE,mLocationListener);
                if (locationManager != null) {
                    location = locationManager
                            .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    if (location != null) {
                        lat = location.getLatitude();
                        lon = location.getLongitude();
                        return getAddress(lat,lon);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getAddress(double latitude, double longitude) {
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(context, Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if(addresses!=null&&addresses.size()!=0)
                return addresses.get(0).getAddressLine(0);
            else return null;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}