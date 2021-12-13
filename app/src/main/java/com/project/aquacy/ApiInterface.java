package com.project.aquacy;

import com.google.gson.JsonObject;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiInterface {

   /* @POST("/api/AC_EntityMasterAPI/CheckWhatsAppNo")
           //on below line we are creating a method to post our data.
    Call<WhatsAppObj> createPost(@Body WhatsAppObj dataModal);*/

    @POST("api/AC_EntityMasterAPI/CheckWhatsAppNo")
    //Call<WhatsAppObj> ApiName(String jsonBody);
    Call<String> ApiName(@Body JSONObject object);
}

