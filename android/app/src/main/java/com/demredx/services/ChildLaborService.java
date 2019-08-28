package com.demredx.services;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import com.facebook.react.bridge.Callback;


public class ChildLaborService extends Service {

  private final String TAG = "ChildLaborService :";

  @Nullable
  @Override
  public IBinder onBind(Intent intent) {
    return null;
  }

  @Override
  public void onCreate() {
    super.onCreate();
    Log.e(TAG, "onCreate: ");
  }

  @Override
  public int onStartCommand(Intent intent, int flags, int startId) {
    Log.e(TAG, "onStartCommand: "+startId);
    work();
    return super.onStartCommand(intent, flags, startId);
  }

  @Override
  public void onDestroy() {
    Log.e(TAG, "onDestroy: ");
    super.onDestroy();
  }

  private void work(){
    Log.e(TAG, "work: Started.." );
    new Handler().postDelayed(new Runnable() {
      @Override
      public void run() {
        Log.e(TAG, "run: 30 sec");
        Intent jobStatus = new Intent();
        jobStatus.putExtra("status","Job done!");
//        sendBroadcast(jobStatus,"ServiceBroadcast");
        jobStatus.setAction("110");
        sendBroadcast(jobStatus);
        Log.e(TAG, "Broadcast....");
        stopSelf();
      }
    }, 15000);
  }
}
