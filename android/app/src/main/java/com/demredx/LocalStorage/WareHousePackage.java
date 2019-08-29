package com.demredx.LocalStorage;

import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ViewManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nonnull;

public class WareHousePackage implements ReactPackage {
  @Nonnull
  @Override
  public List<NativeModule> createNativeModules(@Nonnull ReactApplicationContext reactContext) {
    List<NativeModule> nativeModuleList = new ArrayList<>();
    nativeModuleList.add(new WareHouseModule(reactContext));
    return nativeModuleList;
  }

  @Nonnull
  @Override
  public List<ViewManager> createViewManagers(@Nonnull ReactApplicationContext reactContext) {
    return Collections.emptyList();
  }

}
