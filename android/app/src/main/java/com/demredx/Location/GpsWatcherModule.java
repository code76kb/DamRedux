package com.demredx.Location;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.LocationManager;
import android.util.Log;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class GpsWatcherModule extends ReactContextBaseJavaModule implements LifecycleEventListener {

  private final String TAG = "GpsWatcherModule:";

  Context mContext;
  private BroadcastReceiver mGpsSwitchStateReceiver = null;

  public GpsWatcherModule(@Nonnull ReactApplicationContext reactContext) {
    super(reactContext);
    mContext = reactContext;
  }

  @Nonnull
  @Override
  public String getName() {
    return "GPSWatcher";
  }

  @ReactMethod
  public void startGpsWatch(ReadableMap config, Callback callback){

    LocationManager locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
    boolean gpsStatus = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    Log.e(TAG, "onStartGpsWatch: GPS status :"+gpsStatus);
    callback.invoke(gpsStatus);
     mGpsSwitchStateReceiver = new BroadcastReceiver() {
       @Override
       public void onReceive(Context context, Intent intent) {
         Log.e(TAG, "onReceive: Provider Changes" );
         if (intent.getAction().matches("android.location.PROVIDERS_CHANGED")) {
            boolean gpsStatus = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            Log.e(TAG, "onReceive: GPS status :"+gpsStatus);
//            callback.invoke(gpsStatus);
           WritableMap params = Arguments.createMap();
           params.putBoolean("status", gpsStatus);
           sendEvent(getReactApplicationContext(),"GPsStatus",params);
         }

       }
     };
    getReactApplicationContext().registerReceiver(mGpsSwitchStateReceiver, new IntentFilter(LocationManager.PROVIDERS_CHANGED_ACTION));
  }

  private void sendEvent(ReactContext reactContext,String eventName,@Nullable WritableMap params) {
    reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit(eventName, params);
  }

  @Override
  public void onHostResume() {

  }

  @Override
  public void onHostPause() {
//    try {
//      if(mGpsSwitchStateReceiver!=null && mContext!=null)
////        mContext.unregisterReceiver(mGpsSwitchStateReceiver);
//    }catch (Exception e){
//      Log.e(TAG, "onHostPause: Brodcast unregister exception :"+e);
//    }
  }

  @Override
  public void onHostDestroy() {

  }
}
