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
import java.lang.*;

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


    public void UploadFile(String Phonenumber,int LandValue,int TotalUnits){

        FragmentSession = new Sessionmanager(this.getApplicationContext());
        final HashMap<String, String> profile =  FragmentSession.getProfileDetails();

        // create upload service client
        UserClient service =
                ServiceGenerator.createServiceWithAuth(UserClient.class,profile.get(FragmentSession.Token));


        MultipartBody.Part LandImage = prepareFilePart("LandImage", landimagepath);
        MultipartBody.Part SurveyImage = prepareFilePart("SurveyImage", surveyimagepath);

        RequestBody Email = createPartFromString(profile.get(FragmentSession.personEmail));
        RequestBody OwnerName = createPartFromString(profile.get(FragmentSession.personDisplayName));
        RequestBody PhoneNumber = createPartFromString(Phonenumber);
//        RequestBody PhoneNumber = createPartFromString();


        // finally, execute the request
        Call<ResponseBody> call = service.uploadSellFormData("bearer "+profile.get(FragmentSession.Token),Email, OwnerName, PhoneNumber, LandValue, TotalUnits, LandImage, SurveyImage);
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_land_form);



        final EditText Phone_number = (EditText) findViewById(R.id.Phone_number);
        final EditText Expected_value = (EditText) findViewById(R.id.Land_value);
        final EditText Units = (EditText) findViewById(R.id.Units);

        LandImageButton = (Button) findViewById(R.id.Land_button);
        SurveyImageButton = (Button) findViewById(R.id.Survey_button);
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
                String Phonenumber = Phone_number.getText().toString();
                int Expectedvalue = Integer.valueOf(Expected_value.getText().toString());
                int NoofUnits = Integer.valueOf(Units.getText().toString());

                UploadFile(Phonenumber,Expectedvalue,NoofUnits);

            }
        });







    }

}
