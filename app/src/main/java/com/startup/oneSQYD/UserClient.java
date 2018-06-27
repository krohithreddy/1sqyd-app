package com.startup.oneSQYD;

import java.util.ArrayList;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;




public interface UserClient {


    public class SignInJson{
        String Email;

        public SignInJson(String email) {
            Email = email;
        }
    }

    public class ResponseSignInJson{
        String message;
        String token;

        public ResponseSignInJson(String message, String token) {
            this.message = message;
            this.token = token;
        }

        public String getMessage() {
            return message;
        }

        public String getToken() {
            return token;
        }
    }

    @GET("buy")
    Call<ResponseBody> getAllBuy(@Header("Authorization") String token);

    @GET("land")
    Call<ArrayList<LandCard>> getAllLands(@Header("Authorization") String token);

    @POST("user/login")
    Call<ResponseSignInJson> UserSignIn(@Body SignInJson body);

    // previous code for single file uploads
    @Multipart
    @POST("upload")
    Call<ResponseBody> uploadFile(
            @Part("description") RequestBody description,
            @Part MultipartBody.Part file);

    // new code for multiple files
    @Multipart
    @POST("land")
    Call<ResponseBody> uploadSellFormData(
            @Header("Authorization") String token,
            @Part("Email") RequestBody Emailbody,
            @Part("Owner_name") RequestBody Owner_name_body,
            @Part("Phone_number") RequestBody Phone_number_body,
            @Part("City") RequestBody Citybody,
            @Part("Total_size") RequestBody Total_size_body,
            @Part("Total_units") RequestBody Total_units_body,
            @Part("Percent_sold") RequestBody Percent_sold_body,
            @Part("Land_unit_value") RequestBody Land_unit_value_body,
            @Part("Cost_unit_value") RequestBody Cost_unit_value_body,
            @Part MultipartBody.Part LandImage,
            @Part MultipartBody.Part SurveyImage);
}
