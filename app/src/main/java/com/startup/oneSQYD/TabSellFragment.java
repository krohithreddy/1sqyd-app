package com.startup.oneSQYD;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.CursorLoader;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static android.app.Activity.RESULT_OK;


public class TabSellFragment extends Fragment {
    private static final String TAG = "TabSellFragment";

    private Button LandImage;
    private Button SurveyImage;
    private Button Upload;
    private final int IMG_REQUEST = 1;
    private String uploadUrl = "http://192.168.0.103:3100/land";
    private Bitmap bitmap1, bitmap2;
    public Uri path;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmentsell_layout, container, false);
        LandImage = (Button) view.findViewById(R.id.LandImage);
        SurveyImage = (Button) view.findViewById(R.id.SurveyImage);
        Upload = (Button) view.findViewById(R.id.Upload);


        LandImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SelectImage();
                Toast.makeText(getActivity(), "Testing Land Image", Toast.LENGTH_SHORT).show();
            }
        });


        Upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SendImageRequest();
//                Map<String, String> params = new HashMap<String, String>(2);
//                params.put("Email", "allirohan9@gmail.com");
//                params.put("Aadhar", "9989889655");
//
//                String result = multipartRequest(uploadUrl, params, String.valueOf(path), "image", "image/jpeg");
//                Toast.makeText(getActivity(), result+"TESTING BUTTON CLICK 2", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    public void SelectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMG_REQUEST);

    }

    @Override
    public void onActivityResult(int requestcode, int resultcode, Intent data) {
        super.onActivityResult(requestcode, resultcode, data);
        System.out.println("in On activity 1");
        if (IMG_REQUEST == requestcode && resultcode == RESULT_OK && data != null) {
            path = data.getData();

            System.out.println("in On activity 2");
            System.out.println(path);
            try {
                bitmap1 = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), path);
            } catch (IOException e) {
                e.printStackTrace();
            }
            bitmap2 = bitmap1;
            final ImageView LandImage = (ImageView) getView().findViewById(R.id.LandimageView);

            LandImage.setImageBitmap(bitmap1);
//            Picasso.get().load(path).into(LandImage);

        }
    }

    private void SendImageRequest() {

        //our custom volley request
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, uploadUrl,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        try {
                            JSONObject obj = new JSONObject(new String(response.data));
                            System.out.println(obj);
                            Toast.makeText(getActivity(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {

            /*
             * If you want to add more parameters with the image
             * you can do it here
             * here we have only one parameter with the image
             * which is tags
             * */
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Email", "allirohan9@gmail.com");
                params.put("Aadhar", "9989889655");
                return params;
            }


            /*
             * Here we are passing image by renaming it with a unique name
             * */
            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
//                long imagename = System.currentTimeMillis();
                System.out.println(bitmap1);
                params.put("LandImage", new DataPart("LandImage1" + ".jpg", ImageToFileData(bitmap1)));
                params.put("SurveyImage", new DataPart("SurveyImage1" + ".jpg", ImageToFileData(bitmap1)));
                System.out.println("Done putting");
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap();
                headers.put("Authorization", "bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6ImFsbGlyb2hhbjlAZ21haWwuY29tIiwidXNlcklkIjoiNWIxYWI3NTYyNTk3NjkyMjI1NTFhNzM3IiwiaWF0IjoxNTI4NzQ1MjIyLCJleHAiOjE1Mjg3NDg4MjJ9.zcovf0jiWqrl1-_ugJgZECmO6-AlXIJhlziNL7Jxfx4");
                return headers;
            }
        };

        //adding the request to volley
        Volley.newRequestQueue(getActivity()).add(volleyMultipartRequest);


    }

    private byte[] ImageToFileData(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);

        return byteArrayOutputStream.toByteArray();
    }

}


//    public String multipartRequest(String urlTo, Map<String, String> parmas, String filepath, String filefield, String fileMimeType) {
//        HttpURLConnection connection = null;
//        DataOutputStream outputStream = null;
//        InputStream inputStream = null;
//
//        System.out.println(urlTo);
//        System.out.println(filepath);
////        System.out.println(filefield);
//
//
//
//        String twoHyphens = "--";
//        String boundary = "*****" + Long.toString(System.currentTimeMillis()) + "*****";
//        String lineEnd = "\r\n";
//
//        String result = "";
//
//        int bytesRead, bytesAvailable, bufferSize;
//        byte[] buffer;
//        int maxBufferSize = 5 * 1024 * 1024;
//
//        filepath = getRealPathFromURI(getContext(), Uri.parse(filepath));
//
//        System.out.println(filepath);
//
//        String[] q = filepath.split("/");
//        int idx = q.length - 1;
//
//        try {
//            File file = new File(filepath);
//            FileInputStream fileInputStream = new FileInputStream(file);
//
//            URL url = new URL(urlTo);
//            connection = (HttpURLConnection) url.openConnection();
//
//            connection.setDoInput(true);
//            connection.setDoOutput(true);
//            connection.setUseCaches(false);
//
//            connection.setRequestMethod("POST");
//            connection.setRequestProperty("Connection", "Keep-Alive");
////            connection.setRequestProperty("User-Agent", "Android Multipart HTTP Client 1.0");
//            connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
//
//            outputStream = new DataOutputStream(connection.getOutputStream());
//            outputStream.writeBytes(twoHyphens + boundary + lineEnd);
//            outputStream.writeBytes("Content-Disposition: form-data; name=\"" + filefield + "\"; filename=\"" + q[idx] + "\"" + lineEnd);
//            outputStream.writeBytes("Content-Type: " + fileMimeType + lineEnd);
//            outputStream.writeBytes("Content-Transfer-Encoding: binary" + lineEnd);
//
//            outputStream.writeBytes(lineEnd);
//
//            bytesAvailable = fileInputStream.available();
//            bufferSize = Math.min(bytesAvailable, maxBufferSize);
//            buffer = new byte[bufferSize];
//
//            bytesRead = fileInputStream.read(buffer, 0, bufferSize);
//            while (bytesRead > 0) {
//                outputStream.write(buffer, 0, bufferSize);
//                bytesAvailable = fileInputStream.available();
//                bufferSize = Math.min(bytesAvailable, maxBufferSize);
//                bytesRead = fileInputStream.read(buffer, 0, bufferSize);
//            }
//
//            outputStream.writeBytes(lineEnd);
//
//            // Upload POST Data
//            Iterator<String> keys = parmas.keySet().iterator();
//            while (keys.hasNext()) {
//                String key = keys.next();
//                String value = parmas.get(key);
//
//                outputStream.writeBytes(twoHyphens + boundary + lineEnd);
//                outputStream.writeBytes("Content-Disposition: form-data; name=\"" + key + "\"" + lineEnd);
//                outputStream.writeBytes("Content-Type: text/plain" + lineEnd);
//                outputStream.writeBytes(lineEnd);
//                outputStream.writeBytes(value);
//                outputStream.writeBytes(lineEnd);
//            }
//
//            outputStream.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
//
//
////            if (200 != connection.getResponseCode()) {
////                throw new CustomException("Failed to upload code:" + connection.getResponseCode() + " " + connection.getResponseMessage());
////            }
//
//            inputStream = connection.getInputStream();
//
//            result = this.convertStreamToString(inputStream);
//
//            fileInputStream.close();
//            inputStream.close();
//            outputStream.flush();
//            outputStream.close();
//
//            return result;
//        } catch (Exception e) {
//            System.out.println(e);
////            throw new CustomException(e);
//        }
//        return result;
//    }
//
//    private String convertStreamToString(InputStream is) {
//        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
//        StringBuilder sb = new StringBuilder();
//
//        String line = null;
//        try {
//            while ((line = reader.readLine()) != null) {
//                sb.append(line);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                is.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        return sb.toString();
//    }
//
//
//    public String getRealPathFromURI(Context context, Uri contentUri) {
//        Cursor cursor = null;
//        try {
//            String[] proj = { MediaStore.Images.Media.DATA };
//            cursor = context.getContentResolver().query(contentUri,  proj, null, null, null);
//            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//            cursor.moveToFirst();
//            return cursor.getString(column_index);
//        } catch (Exception e) {
//            Log.e(TAG, "getRealPathFromURI Exception : " + e.toString());
//            return "";
//        } finally {
//            if (cursor != null) {
//                cursor.close();
//            }
//        }
//    }
//}