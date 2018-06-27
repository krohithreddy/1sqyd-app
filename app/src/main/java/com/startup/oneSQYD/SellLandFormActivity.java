package com.startup.oneSQYD;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SellLandFormActivity extends AppCompatActivity {


    String ButtonPressed = "";
    Uri landimagepath,surveyimagepath;
    private final int IMG_REQUEST = 1;
    Button LandImageButton;
    Button SurveyImageButton;
    Button PanImageButton;
    Button AadharImageButton;
    Sessionmanager FragmentSession;

    private String getRealPathFromURIPath(Uri contentURI) {

        String result;
        System.out.println(contentURI);
        Cursor cursor = this.getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            System.out.println(cursor);
            int idx = cursor.getColumnIndex(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;

    }



    public void SelectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);
        startActivityForResult(intent,1);


    }

    @Override
    public void onActivityResult(int requestcode, int resultcode, Intent data) {
        super.onActivityResult(requestcode, resultcode, data);
        if (IMG_REQUEST == requestcode && resultcode == RESULT_OK && data != null) {
            if(ButtonPressed=="land"){
                landimagepath = data.getData();
                LandImageButton.setText("LandImage Selected");


            }
            else if(ButtonPressed=="survey"){
                surveyimagepath = data.getData();
                SurveyImageButton.setText("SurveyImage Selected");

            }

        }
    }


    @NonNull
    private RequestBody createPartFromString(String descriptionString) {
        return RequestBody.create(
                okhttp3.MultipartBody.FORM, descriptionString);
    }

    @NonNull
    private MultipartBody.Part prepareFilePart(String partName, Uri fileUri) {

        File file = new File(getRealPathFromURIPath(fileUri));

        // create RequestBody instance from file
        RequestBody requestFile =
                RequestBody.create(
                        MediaType.parse(this.getContentResolver().getType(fileUri)),
                        file
                );

        // MultipartBody.Part is used to send also the actual file name
        return MultipartBody.Part.createFormData(partName, file.getName(), requestFile);
    }


    public void SetToast(int responsecode){
        if(responsecode == 201){
            Toast.makeText(this, "Upload Success", Toast.LENGTH_SHORT).show();
            Intent mainActivity = new Intent(this,MainActivity.class);
            startActivity(mainActivity);
        }
        else
            Toast.makeText(this, "Upload Failed", Toast.LENGTH_SHORT).show();
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_land_form);


        final EditText Survey_number = (EditText) findViewById(R.id.Survey_number);
        final EditText Village = (EditText) findViewById(R.id.Village);
        final EditText Mandal = (EditText) findViewById(R.id.Mandal);
        final EditText Division = (EditText) findViewById(R.id.Division);
        final EditText City = (EditText) findViewById(R.id.City);
        final EditText District = (EditText) findViewById(R.id.District);
        final EditText State = (EditText) findViewById(R.id.State);

        final EditText Total_size = (EditText) findViewById(R.id.Total_size);
        final EditText Width = (EditText) findViewById(R.id.Width);
        final EditText Length = (EditText) findViewById(R.id.Length);
        final EditText Percent_sold = (EditText) findViewById(R.id.Percent_sold);

        final EditText Full_name = (EditText) findViewById(R.id.Full_name);
        final EditText Aadhar_number = (EditText) findViewById(R.id.Aadhar_number);
        final EditText Phone_number = (EditText) findViewById(R.id.Phone_number);

        final EditText Total_units = (EditText) findViewById(R.id.Total_units);
        Total_units.setSelected(false);
        final EditText Cost_unit_value = (EditText) findViewById(R.id.Cost_unit_value);
        Cost_unit_value.setSelected(false);
        final EditText Land_unit_value = (EditText) findViewById(R.id.Land_unit_value);
        final EditText Total_land_value = (EditText) findViewById(R.id.Total_land_value);

        LandImageButton = (Button) findViewById(R.id.Land_button);
        SurveyImageButton = (Button) findViewById(R.id.Survey_button);
        PanImageButton = (Button) findViewById(R.id.Pan_button);
        AadharImageButton = (Button) findViewById(R.id.Aadhar_button);
        Button submit = (Button) findViewById(R.id.submit);


        LandImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ButtonPressed = "land";
                SelectImage();

            }
        });

        SurveyImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ButtonPressed = "survey";
                SelectImage();

            }
        });





        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String Full_name_string = Full_name.getText().toString();
                final String Phone_number_string = Phone_number.getText().toString();
                final String City_string = City.getText().toString();

                final float Total_size_int = Float.valueOf(Total_size.getText().toString());
                final float Percent_sold_float = Float.valueOf(Percent_sold.getText().toString());


                final int Cost_unit_int = Integer.valueOf(Cost_unit_value.getText().toString());

                final int Total_units_int = Integer.valueOf(Total_units.getText().toString());
                final float Land_unit_float = (float)((Total_size_int*(Percent_sold_float/100.0))/Total_units_int);
                final int Total_cost_int = Total_units_int*Cost_unit_int;
                Land_unit_value.setText(String.valueOf(Land_unit_float));
                Total_land_value.setText(String.valueOf(Total_cost_int));



//                float Land_unit_float = Float.valueOf(Land_unit_value.getText().toString());

                UploadFile(Full_name_string,
                        Phone_number_string,
                        City_string,
                        Total_size_int,
                        Total_units_int,
                        Percent_sold_float,
                        Land_unit_float,
                        Cost_unit_int);

            }
        });







    }

    public void UploadFile(String full_name_string, String phone_number_string, String city_string, float total_size_int, int total_units_int, float percent_sold_float, float land_unit_float, int cost_unit_int) {
        FragmentSession = new Sessionmanager(this.getApplicationContext());
        final HashMap<String, String> profile =  FragmentSession.getProfileDetails();

        // create upload service client
        UserClient service =
                ServiceGenerator.createServiceWithAuth(UserClient.class,profile.get(FragmentSession.Token));


        MultipartBody.Part LandImage = prepareFilePart("LandImage", landimagepath);
        MultipartBody.Part SurveyImage = prepareFilePart("SurveyImage", surveyimagepath);

        RequestBody Emailbody = createPartFromString(profile.get(FragmentSession.personEmail));
        RequestBody Owner_name_body = createPartFromString(full_name_string);
        RequestBody Phone_number_body = createPartFromString(phone_number_string);
        RequestBody Citybody = createPartFromString(city_string);
        RequestBody Total_size_body = createPartFromString(String.valueOf(total_size_int));
        RequestBody Total_units_body = createPartFromString(String.valueOf(total_units_int));
        RequestBody Percent_sold_body = createPartFromString(String.valueOf(percent_sold_float));
        RequestBody Land_unit_body = createPartFromString(String.valueOf(land_unit_float));
        RequestBody Cost_unit_body = createPartFromString(String.valueOf(cost_unit_int));

//        FragmentSession.SignIn(FragmentSession.personEmail);

        // finally, execute the request
        Call<ResponseBody> call = service.uploadSellFormData("bearer "+profile.get(FragmentSession.Token),Emailbody, Owner_name_body, Phone_number_body,Citybody, Total_size_body, Total_units_body,Percent_sold_body,Land_unit_body,Cost_unit_body, LandImage, SurveyImage);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, final Response<ResponseBody> response) {
                runOnUiThread(new Runnable() {
                    public void run() {
                        SetToast(response.code());
                    }
                });

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("Upload error:", t.getMessage());
            }
        });
    }

}
