package com.startup.oneSQYD;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;


public class TabSellFragment extends Fragment{
    private static final String TAG = "TabSellFragment";

    Sessionmanager FragmentSession;


    private Button LandImageButton;
    private Button SurveyImageButton;
    private Button UploadButton;
    private final int IMG_REQUEST = 1;
    private String uploadUrl = "http://192.168.0.100:3100/land";
    private Bitmap bitmap1, bitmap2;
    public Uri path1=null,path2=null;
    public int button;

    public void RefreshFragment(){
        final ImageView LandImage = (ImageView) getView().findViewById(R.id.LandimageView);
        LandImage.setImageDrawable(getResources().getDrawable(R.drawable.googleg_standard_color_18));
        final ImageView SurveyImage = (ImageView) getView().findViewById(R.id.SurveyImageView);
        SurveyImage.setImageDrawable(getResources().getDrawable(R.drawable.googleg_standard_color_18));
        path1=null;
        path2=null;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,@Nullable ViewGroup container,@Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmentsell_layout, container, false);
        LandImageButton = (Button) view.findViewById(R.id.LandImageButton);
        SurveyImageButton = (Button) view.findViewById(R.id.SurveyImageButton);
        UploadButton = (Button) view.findViewById(R.id.Upload);


        LandImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                button=0;
                SelectImage();
                Toast.makeText(getActivity(), "Testing Land Image", Toast.LENGTH_SHORT).show();
            }
        });


        SurveyImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                button=1;
                SelectImage();
                Toast.makeText(getActivity(), "Testing Survey Image", Toast.LENGTH_SHORT).show();
            }
        });

        UploadButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("StaticFieldLeak")
            @Override
            public void onClick(View view) {

                Intent SellLandIntent = new Intent(getActivity(), SellLandFormActivity.class);
                getActivity().startActivity(SellLandIntent);

//                new AsyncTask<URI,String,String>(){
//                    protected String doInBackground(URI... paths) {
//
//                        UploadFile();
//                        return null;
//                    }
//
//
//
//                    protected void onPostExecute(String result) {
//
//
//                        RefreshFragment();
//                    }
//                }.execute();

//                UploadFile(path1,path2);
//                Toast.makeText(getActivity(), "Upload Success", Toast.LENGTH_SHORT).show();
//
//                RefreshFragment();

            }
        });


        return view;

    }







    public void SelectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);
        System.out.println(path1);
        startActivityForResult(intent,1);


    }

    @Override
    public void onActivityResult(int requestcode, int resultcode, Intent data) {
        super.onActivityResult(requestcode, resultcode, data);
        System.out.println("in On activity 1");
        if (IMG_REQUEST == requestcode && resultcode == RESULT_OK && data != null) {
            if(button==0){
                path1 = data.getData();

                String realpath = getRealPathFromURIPath(path1);
                System.out.println(realpath);

                System.out.println("in On activity 2");
                System.out.println(path1);
                try {
                    bitmap1 = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), path1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
//                bitmap2 = bitmap1;
                final ImageView LandImage = (ImageView) getView().findViewById(R.id.LandimageView);

                LandImage.setImageBitmap(bitmap1);
//            Picasso.get().load(path).into(LandImage);
            }
            else if(button==1){
                path2 = data.getData();

                String realpath = getRealPathFromURIPath(path2);
                System.out.println(realpath);

                System.out.println("in On activity 3");
                System.out.println(path2);
                try {
                    bitmap2 = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), path2);
                } catch (IOException e) {
                    e.printStackTrace();
                }
//                bitmap2 = bitmap1;
                final ImageView SurveyImage = (ImageView) getView().findViewById(R.id.SurveyImageView);

                SurveyImage.setImageBitmap(bitmap2);
//            Picasso.get().load(path).into(LandImage);
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
                        MediaType.parse(getActivity().getContentResolver().getType(fileUri)),
                        file
                );

        // MultipartBody.Part is used to send also the actual file name
        return MultipartBody.Part.createFormData(partName, file.getName(), requestFile);
    }

    private String getRealPathFromURIPath(Uri contentURI) {

        String result;
        System.out.println(contentURI);
        Cursor cursor = getActivity().getContentResolver().query(contentURI, null, null, null, null);
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


    public void SetToast(int responsecode){
        if(responsecode == 201){
            Toast.makeText(getActivity(), "Upload Success", Toast.LENGTH_SHORT).show();
        }
        else
            Toast.makeText(getActivity(), "Upload Failed", Toast.LENGTH_SHORT).show();
    }


    public void UploadFile(){



        FragmentSession = new Sessionmanager(getActivity().getApplicationContext());
        final HashMap<String, String> profile =  FragmentSession.getProfileDetails();

//        getActivity().runOnUiThread(new Runnable() {
//            public void run() {
//                Toast.makeText(getActivity(), profile.get(FragmentSession.Token)+profile.get(FragmentSession.personEmail), Toast.LENGTH_SHORT).show();
//            }
//        });


        // create upload service client
        UserClient service =
                ServiceGenerator.createServiceWithAuth(UserClient.class,profile.get(FragmentSession.Token));


        MultipartBody.Part LandImage = prepareFilePart("LandImage", path1);

        MultipartBody.Part SurveyImage = prepareFilePart("SurveyImage", path2);

        RequestBody Email = createPartFromString(profile.get(FragmentSession.personEmail));

        RequestBody Aadhar = createPartFromString("9989889655");

        // finally, execute the request
        Call<ResponseBody> call = service.uploadMultipleFiles("bearer "+profile.get(FragmentSession.Token),Email, Aadhar, LandImage, SurveyImage);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, final Response<ResponseBody> response) {
                getActivity().runOnUiThread(new Runnable() {
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

