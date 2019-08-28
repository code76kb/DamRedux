package com.demredx;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;

import javax.annotation.Nonnull;

public class ImagePickerModule extends ReactContextBaseJavaModule implements ActivityEventListener {

  public ImagePickerModule(@Nonnull ReactApplicationContext reactContext) {
    super(reactContext);
    reactContext.addActivityEventListener(this);
  }

  @Nonnull
  @Override
  public String getName() {
    return "ImagePickerModule";
  }

  private final int PICK_IMAGE = 1;
  private Callback pickerSuccessCallback;
  private Callback pickerFailiureCallback;

  @ReactMethod
  public void openPickerDialog(ReadableMap config, Callback successCallback, Callback cancelCallback){

    Activity currentActivity = getCurrentActivity();
    if(currentActivity == null){
      cancelCallback.invoke("Activity Does't exist..");
      return;
    }
    else {

      pickerSuccessCallback = successCallback;
      pickerFailiureCallback = cancelCallback;

      try {

        final Intent galleryIntent = new Intent();
        galleryIntent.setType("image/");
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);

        final Intent chooserIntent = Intent.createChooser(galleryIntent,"Pick Image");

        currentActivity.startActivityForResult(chooserIntent,PICK_IMAGE);


      }catch (Exception e){
        cancelCallback.invoke(e);
      }

    }
  }

  @Override
  public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {
    if(requestCode == PICK_IMAGE){
      if(resultCode==Activity.RESULT_CANCELED)
        pickerFailiureCallback.invoke("Canceled...");
      else if(resultCode == Activity.RESULT_OK) {
        Uri uri = data.getData();
        if(uri == null)
          pickerFailiureCallback.invoke("No image found!!!");
        else {
          try {
            pickerSuccessCallback.invoke(uri.toString());
          }catch (Exception e){
            pickerFailiureCallback.invoke(e);
          }
        }
      }
    }
  }

  @Override
  public void onNewIntent(Intent intent) {

  }
}
