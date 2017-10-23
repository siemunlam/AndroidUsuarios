package com.siem.siemusuarios.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.siem.siemusuarios.Application;

public class PreferencesHelper {

    private static final String KEY_FIREBASE_TOKEN = "KEY_FIREBASE_TOKEN";

    private static PreferencesHelper mInstance = null;
    private SharedPreferences mPreferences;

    private PreferencesHelper(Context context) {
        mPreferences = context.getSharedPreferences(Constants.NAME_SHAREDPREFERENCES, Context.MODE_PRIVATE);
    }

    public static PreferencesHelper getInstance(){
        if(mInstance == null){
            synchronized (PreferencesHelper.class) {
                if(mInstance == null){
                    mInstance = new PreferencesHelper(Application.getInstance());
                }
            }
        }
        return mInstance;
    }


    //KEY_FIREBASE_TOKEN
    public String getFirebaseToken(){
        return mPreferences.getString(KEY_FIREBASE_TOKEN, "");
    }

    public void setFirebaseToken(String token){
        mPreferences.edit().putString(KEY_FIREBASE_TOKEN, token).apply();
    }

    public void cleanFirebaseToken(){
        mPreferences.edit().remove(KEY_FIREBASE_TOKEN).apply();
    }

}
