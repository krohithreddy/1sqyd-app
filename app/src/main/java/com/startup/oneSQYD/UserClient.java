package com.startup.oneSQYD;

import java.util.ArrayList;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;



public interface UserClient {



    @GET("land")
    Call<ArrayList<LandCard>> getAllLands(@Header("Authorization") String token);

    // previous code for single file uploads
    @Multipart
    @POST("upload")
    Call<ResponseBody> uploadFile(
            @Part("description") RequestBody description,
            @Part MultipartBody.Part file);

    // new code for multiple files
    @Multipart
    @POST("land")
    Call<ResponseBody> uploadMultipleFiles(
            @Header("Authorization") String token,
            @Part("Email") RequestBody Email,
            @Part("Aadhar") RequestBody Aadhar,
            @Part MultipartBody.Part LandImage,
            @Part MultipartBody.Part SurveyImage);
}
