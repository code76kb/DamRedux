package com.demredx;

import android.app.Application;
import android.util.Log;

import com.demredx.LocalStorage.WareHousePackage;
import com.demredx.Location.GpsWatcherPackage;
import com.demredx.services.ChildLaborPackage;
import com.facebook.react.PackageList;
import com.facebook.hermes.reactexecutor.HermesExecutorFactory;
import com.facebook.react.bridge.JavaScriptExecutorFactory;
import com.facebook.react.ReactApplication;
import com.facebook.react.ReactNativeHost;
import com.facebook.react.ReactPackage;
import com.facebook.soloader.SoLoader;
import com.demredx.ImagePickerPackage;

import java.util.List;

public class MainApplication extends Application implements ReactApplication {

  private final ReactNativeHost mReactNativeHost = new ReactNativeHost(this) {
    @Override
    public boolean getUseDeveloperSupport() {
      return BuildConfig.DEBUG;
    }

    @Override
    protected List<ReactPackage> getPackages() {
      @SuppressWarnings("UnnecessaryLocalVariable")
      List<ReactPackage> packages = new PackageList(this).getPackages();

       // Packages that cannot be autolinked yet can be added manually here, for example:
       packages.add(new ImagePickerPackage());//Img picker test
       packages.add(new ChildLaborPackage());//Background Services
       packages.add(new WareHousePackage());// React and android Comman localStorage manager
       packages.add(new GpsWatcherPackage());

      return packages;
    }

    @Override
    protected String getJSMainModuleName() {
      return "index";
    }
  };

  @Override
  public ReactNativeHost getReactNativeHost() {
    return mReactNativeHost;
  }

  @Override
  public void onCreate() {
    super.onCreate();
    SoLoader.init(this, /* native exopackage */ false);
  }
}
