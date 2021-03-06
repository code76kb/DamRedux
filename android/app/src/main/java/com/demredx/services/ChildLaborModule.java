package com.demredx.services;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;


import javax.annotation.Nonnull;

public class ChildLaborModule extends ReactContextBaseJavaModule implements  LifecycleEventListener {

  Context mContext;
  Intent serviceIntent;

  Callback jobDoneCallback;

  BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
    @Override
    public void onReceive(Context context, Intent intent) {
      Log.e(TAG, "onBroadCastReceive :................................... ");
      if(jobDoneCallback!=null)
          jobDoneCallback.invoke(intent.getStringExtra("currentJob"));
    }
  };

  private final String TAG = "ChildLaborModule :";

  public ChildLaborModule(@Nonnull ReactApplicationContext reactContext) {
    super(reactContext);
    mContext = reactContext;
    reactContext.addLifecycleEventListener(this);
//    IntentFilter intentFilter = new IntentFilter();
//    intentFilter.addAction("110");
//    mContext.registerReceiver(broadcastReceiver,intentFilter);
  }

  @Nonnull
  @Override
  public String getName() {
    return "ChildLabor";
  }


  @ReactMethod
  public void startWork(ReadableMap config, Callback successCallback, Callback failierCallback){
    Log.e(TAG, "startWork................. ");
    boolean serviceStatus = isMyServiceRunning(ChildLaborService.class);
    Log.e(TAG, "startWork: is Service Running:"+serviceStatus);

    if(serviceStatus)
      return;

    Activity currentActivity = getCurrentActivity();
    jobDoneCallback = successCallback;
    serviceIntent = new Intent(mContext,ChildLaborService.class);
    serviceIntent.putExtra("job_id",config.getString("job_id"));
    currentActivity.startService(serviceIntent);
  }

  // Check Running Services
  private boolean isMyServiceRunning(Class<?> serviceClass) {
    ActivityManager manager = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
    for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
      if (serviceClass.getName().equals(service.service.getClassName())) {
        return true;
      }
    }
    return false;
  }

  @ReactMethod
  public void stopWork(ReadableMap config, Callback successCallback, Callback failierCallback){
    Log.e(TAG, "stopWork.............");
    if(serviceIntent!=null)
      getCurrentActivity().stopService(serviceIntent);
    else {
      Log.e(TAG, "stopWork: service intent is null");
      getCurrentActivity().stopService(new Intent(mContext,ChildLaborService.class));
    }
  }


  @Override
  public void onHostResume() {
   Log.e(TAG, "onActivityCreated: Broadcast Receiver registered");
   //getCurrentActivity().registerReceiver(broadcastReceiver,new IntentFilter("ServiceBroadcast"));
    IntentFilter intentFilter = new IntentFilter();
    intentFilter.addAction("110");
    mContext.registerReceiver(broadcastReceiver,intentFilter);
  }

  @Override
  public void onHostPause() {
    //getCurrentActivity().unregisterReceiver(broadcastReceiver);
  }
  @Override
  public void onHostDestroy() {
    Log.e(TAG, "onActivityCreated: Broadcast Receiver un-registered");
//    if(broadcastReceiver!=null)
//    getCurrentActivity().unregisterReceiver(broadcastReceiver);
  }
}
