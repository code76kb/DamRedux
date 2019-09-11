package com.demredx.services;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.demredx.LocalStorage.WareHouse;
import com.demredx.Location.EagleEye;
import com.demredx.Network.RetrofitClient;
import com.demredx.Network.RetrofitInterface;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class ChildLaborService extends Service {

  private final String TAG = "ChildLaborService :";

  //Job status Code
  private final String NOT_RUN_YET = "0";
  private final String SUCCESSFULL = "1";
  private final String FAILED = "-1";
  private final String COUGHT_EXCEPTION = "-2";


  private String job_id = "-1";

  private Timer timer;



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
    if(intent!=null)
    job_id = intent.hasExtra("job_id")?intent.getStringExtra("job_id"):"-1";


    work();
    return super.onStartCommand(intent, flags, startId);
  }


  @Override
  public void onDestroy() {
    Log.e(TAG, "onDestroy: ");
    super.onDestroy();
    if(timer!=null)
      timer.cancel();
  }

  //Run here what ever you wanna run in background and forground.
  private void work(){
    Log.e(TAG, "work: job_id Started.. "+job_id);

    //init location test


    new Handler().postDelayed(new Runnable() {
      @Override
      public void run() {
        Log.e(TAG, "run: after 30 sec");

        //Perform Network Operation
//        testApiRun();

//        //get Location updates
//        locationServiceTest();

        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
          @Override
          public void run() {
            locationServiceTest();
          }
        },0,30000);

      }
    },00000);


  }

  private void testApiRun(){

      JsonObject jobStatusObj = (JsonObject) new JsonParser().parse(WareHouse.getSavedData(this));
      jobStatusObj.addProperty("job_id", job_id);
      jobStatusObj.addProperty("status", NOT_RUN_YET);


      try {

        Retrofit retrofitClient = RetrofitClient.getRetrofitClient();
        RetrofitInterface retrofitInterface = retrofitClient.create(RetrofitInterface.class);
        Call call = retrofitInterface.getTest("v2/offer/all", "CIl_Nz4alKz-gFmLBk0dKD6N45sZby3R", "en", "1088");

        call.enqueue(new Callback() {
          @Override
          public void onResponse(Call call, Response response) {
            JSONObject resObj = (JSONObject) response.body();
            Log.e(TAG, "onResponse: " + resObj.toString());
            jobStatusObj.addProperty("status", SUCCESSFULL);
            updateStatus("Api call Response", jobStatusObj);
          }

          @Override
          public void onFailure(Call call, Throwable t) {
            Log.e(TAG, "onFailure: ");
            jobStatusObj.addProperty("status", FAILED);
            updateStatus("Api call failure...", jobStatusObj);
          }
        });

      } catch (Exception e) {
        jobStatusObj.addProperty("status", COUGHT_EXCEPTION);
        updateStatus("Api call exception..", jobStatusObj);

      }

  }

  private void locationServiceTest(){
     Location location =  EagleEye.getLocation(this);
     if(location==null)
       Log.e(TAG, "locationServiceTest: "+ "Null Location");
     else
      Log.e(TAG, "locationServiceTest: "+ location.toString());
   }


  //Broadcast the job status and kill service
  private void updateStatus(String status, JsonObject jobStatusObj){

    JsonObject currentJobStatusObj = new JsonObject();
    currentJobStatusObj.addProperty("job_id",job_id);
    currentJobStatusObj.addProperty("status",String.valueOf(jobStatusObj.get("status")));

    Intent jobStatusIntent = new Intent();
    jobStatusIntent.setAction("110");
    jobStatusIntent.putExtra("currentJob",currentJobStatusObj.toString());

    sendBroadcast(jobStatusIntent);
    Log.e(TAG, "Broadcast....");

    //Update Job in WareHouse
    WareHouse.saveData(this,jobStatusObj.toString());

    stopSelf();
  }


}

