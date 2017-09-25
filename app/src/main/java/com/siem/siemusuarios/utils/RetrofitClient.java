package com.siem.siemusuarios.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.siem.siemusuarios.BuildConfig;
import com.siem.siemusuarios.interfaces.ServerApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static ServerApi retrofitServerApi = null;

    private static Gson getGson(){
        return new GsonBuilder()
                .setLenient()
                .create();
    }

    public static ServerApi getServerClient() {
        if (retrofitServerApi == null) {
            Gson gson = getGson();

            retrofitServerApi = new Retrofit.Builder()
                    .baseUrl(BuildConfig.BASE_API_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build()
                    .create(ServerApi.class);
        }
        return retrofitServerApi;
    }
}