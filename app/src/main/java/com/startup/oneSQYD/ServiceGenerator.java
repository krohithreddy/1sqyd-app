package com.startup.oneSQYD;

import java.io.IOException;
import java.util.concurrent.Executors;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator  {

//    Sessionmanager FragmentSession;
//
//    FragmentSession = new ServiceGenerator(ServiceGenerator.this);
//    final HashMap<String, String> profile =  FragmentSession.getProfileDetails();

    private static final String ServerUrl = "http://192.168.0.100:3100/";

    private static OkHttpClient httpClient =
            new OkHttpClient();


    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(ServerUrl)
                    .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = builder.build();



    public static <S> S createService(
            Class<S> serviceClass) {
        return retrofit.create(serviceClass);
    }


    public static <S> S createServiceWithAuth(Class<S> serviceClass, final String Token) {


        Interceptor interceptor = new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                final Request request = chain.request().newBuilder()
                        .header("Authorization", "bearer " + Token)
                        .build();
                return chain.proceed(request);
            }
        };
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(interceptor);
        OkHttpClient client = httpClient.build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ServerUrl)
                .callbackExecutor(Executors.newSingleThreadExecutor())
                .build();
        return retrofit.create(serviceClass);
    }
}
