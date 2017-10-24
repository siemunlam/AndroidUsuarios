package com.siem.siemusuarios.model.api;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Lucas on 21/10/17.
 */

public class ResponseSuscribirse {

    @SerializedName("status")
    private String mStatus;

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }
}
