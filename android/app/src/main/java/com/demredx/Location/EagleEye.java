package com.demredx.Location;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.util.Log;



public class EagleEye  {

  private static final String TAG = "EagleEye :";

  private static final long INTERVAL = 1000 * 10;
  private static final long FASTEST_INTERVAL = 1000 * 5;
  private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 50;

  private Context mContex;

  private static LocationManager locationManager;
  private static boolean isGPSEnabled = false;
  private static boolean isNetworkEnabled = false;

  private static Location location;


  public static Location getLocation(Context mContex) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      if (mContex.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && mContex.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        Log.e(TAG, "getLocation: Did not have Location permissions");
        return null;
      }
    }

    try {
      locationManager = (LocationManager) mContex
        .getSystemService(Context.LOCATION_SERVICE);

      // getting GPS status
      isGPSEnabled = locationManager
        .isProviderEnabled(LocationManager.GPS_PROVIDER);

      Log.v("isGPSEnabled", "=" + isGPSEnabled);

      // getting network status
      isNetworkEnabled = locationManager
        .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

      Log.v("isNetworkEnabled", "=" + isNetworkEnabled);

      if (isGPSEnabled == false && isNetworkEnabled == false) {
        // no network provider is enabled
      } else {
        // this.canGetLocation = true;
        if (isNetworkEnabled) {
          location = null;
          Log.d("Network", "Network");
          if (locationManager != null) {
            location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
          }
        }
        // if GPS Enabled get lat/long using GPS Services
        if (isGPSEnabled) {
          location=null;
          if (location == null) {
            Log.d("GPS Enabled", "GPS Enabled");
            if (locationManager != null) {
              location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            }
          }
        }
      }

    } catch (Exception e) {
      e.printStackTrace();
    }

    return location;
  }


}//Class


