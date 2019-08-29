package com.demredx.Network;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetrofitInterface {


  @GET("/api/unknown")
  Call<JSONObject> doGetListResources();

  //
//  @POST("/api/users")
//  Call<JSONObject> createUser(@Body JSONObject user);

//  r:"v2/offer/all",
  //                                      lang:'en',
  //                                      auth_key:"CIl_Nz4alKz-gFmLBk0dKD6N45sZby3R",
  //                                      city:'1088',

  @GET("/index.php?")
  Call<JSONObject> getTest(@Query("r") String func,@Query("auth_key") String auth_key, @Query("lang") String lang, @Query("city") String city_id);

//  @FormUrlEncoded
//  @POST("/api/users?")
//  Call<JSONObject> doCreateUserWithField(@Field("name") String name, @Field("job") String job);

}
