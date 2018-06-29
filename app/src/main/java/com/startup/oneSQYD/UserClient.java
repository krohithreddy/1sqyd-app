package com.startup.oneSQYD;

import java.util.ArrayList;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
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

    public class OrderObject{
        String LandId,Email,Owner_email,Quantity,Land_unit_value,Cost_unit_value,Total_size,Percent_sold,Total_units,Owner_name,Phone_number,City;

        public OrderObject(String landId, String email, String owner_email, String quantity, String land_unit_value, String cost_unit_value, String total_size, String percent_sold, String total_units, String owner_name, String phone_number, String city) {
            LandId = landId;
            Email = email;
            Owner_email = owner_email;
            Quantity = quantity;
            Land_unit_value = land_unit_value;
            Cost_unit_value = cost_unit_value;
            Total_size = total_size;
            Percent_sold = percent_sold;
            Total_units = total_units;
            Owner_name = owner_name;
            Phone_number = phone_number;
            City = city;
        }

        public String getLandId() {
            return LandId;
        }

        public String getEmail() {
            return Email;
        }

        public String getOwner_email() {
            return Owner_email;
        }

        public String getQuantity() {
            return Quantity;
        }

        public String getLand_unit_value() {
            return Land_unit_value;
        }

        public String getCost_unit_value() {
            return Cost_unit_value;
        }

        public String getTotal_size() {
            return Total_size;
        }

        public String getPercent_sold() {
            return Percent_sold;
        }

        public String getTotal_units() {
            return Total_units;
        }

        public String getOwner_name() {
            return Owner_name;
        }

        public String getPhone_number() {
            return Phone_number;
        }

        public String getCity() {
            return City;
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


    @POST("orders")
    Call<ResponseBody> BuyLand(@HeaderMap Map<String,String> map, @Body OrderObject buylandobject);
}
