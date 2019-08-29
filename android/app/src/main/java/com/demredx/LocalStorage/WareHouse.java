package com.demredx.LocalStorage;

import android.content.Context;
import android.content.SharedPreferences;

public class WareHouse {

  private static final String PREFS_NAME = "DS_PREF";
  private static final String STATUS_LOG = "Status Log"; //its a JsonObject in string format

  public static void saveData(Context context,String data){
    SharedPreferences preferences = context.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = preferences.edit();
    editor.putString(STATUS_LOG,data);
    editor.commit();
  }

  public static String getSavedData(Context context){
     SharedPreferences preferences = context.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);
     return preferences.getString(STATUS_LOG,"{}");
  }

}
