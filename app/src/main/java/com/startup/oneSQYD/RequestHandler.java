package com.startup.oneSQYD;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.common.api.Response;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class RequestHandler {

    Context _context;

    public String RequestType;

    public String RequestURL;

    public JSONObject JSONdata;

    public String ResponseString;

    public RequestHandler(Context context, String RequestType, String RequestURL, JSONObject JSONdata){
        this._context = context;
        this.RequestType = RequestType;
        this.RequestURL = RequestURL;
        this.JSONdata = JSONdata;
        this.ResponseString = null;
    }

//    private final String[] ResultString = new String[1];






    public String SendPostRequest() {


        new AsyncTask<String, Void, String>()
        {
            protected void onPreExecute () {}

            protected String doInBackground (String...arg0){

            try {

                URL url = new URL(_context.getString(R.string.ServerURL) + RequestURL); // here is your URL path


                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                conn.setReadTimeout(15000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod(RequestType);
                conn.setDoInput(true);
                conn.setDoOutput(true);

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(String.valueOf(JSONdata));

                writer.flush();
                writer.close();
                os.close();

                int responseCode = conn.getResponseCode();

                System.out.println("Response code upon connection : " + responseCode);

                if (responseCode == HttpsURLConnection.HTTP_OK) {

                    BufferedReader in = new BufferedReader(new
                            InputStreamReader(
                            conn.getInputStream()));



                    StringBuffer sb = new StringBuffer("");
                    String line = "";

                    while ((line = in.readLine()) != null) {

                        sb.append(line);
                        break;
                    }

                    in.close();
//                    System.out.println(sb.toString());
                    JSONObject responsejson = new JSONObject(sb.toString());
                    System.out.println(responsejson);
                    ResponseString = sb.toString();
                    return sb.toString();


                } else {
                    return new String(String.valueOf(responseCode));
                }
            } catch (Exception e) {
                return new String("Exception: " + e.getMessage());
            }

        }

            @Override
            protected void onPostExecute (String result){
//                ResultString[0] = result;
                Toast.makeText(_context, result+"Connection Login successful",
                        Toast.LENGTH_LONG).show();
                System.out.println("Result : "+result);
                if(result.equals("201")){
//                    Toast.makeText(_context, "Connection Login successful",
//                            Toast.LENGTH_LONG).show();
                }

        }

        }.execute();
//        System.out.println("Result String : "+ResultString[0]);
    return ResponseString;

    }

//    public String getPostDataString(JSONObject params) throws Exception {
//
//        StringBuilder result = new StringBuilder();
//        boolean first = true;
//
//        Iterator<String> itr = params.keys();
//
//        while(itr.hasNext()){
//
//            String key= itr.next();
//            Object value = params.get(key);
//
//            if (first)
//                first = false;
//            else
//                result.append("&");
//
//            result.append(URLEncoder.encode(key, "UTF-8"));
//            result.append("=");
//            result.append(URLEncoder.encode(value.toString(), "UTF-8"));
//
//        }
//        return result.toString();
//    }
}


