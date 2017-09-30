package com.siem.siemusuarios.model.api;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Lucas on 29/9/17.
 */

public class ResponseMotivos {

    @SerializedName("results")
    private HashMap<String, List<String>> mListMotivos;

    public HashMap<String, List<String>> getListMotivos() {
        return mListMotivos;
    }
}
