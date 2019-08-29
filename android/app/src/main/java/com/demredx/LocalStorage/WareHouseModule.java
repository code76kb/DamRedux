package com.demredx.LocalStorage;

import android.content.Context;

import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;

import javax.annotation.Nonnull;

public class WareHouseModule extends ReactContextBaseJavaModule {

  Context mContext;

  public WareHouseModule(@Nonnull ReactApplicationContext reactContext) {
    super(reactContext);
    mContext = reactContext;
  }

  @Nonnull
  @Override
  public String getName() {
    return "WareHouse";
  }

  @ReactMethod
  public void getSaveData(ReadableMap config, Callback successCallback, Callback failureCallback ){
    String statusData = WareHouse.getSavedData(mContext);
    successCallback.invoke(statusData);
  }

  @ReactMethod
  public void saveData(ReadableMap config){
    String dataToBeSaved = config.getString("data");
    WareHouse.saveData(mContext,dataToBeSaved);
  }

}
