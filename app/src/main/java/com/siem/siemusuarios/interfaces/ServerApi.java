package com.siem.siemusuarios.interfaces;


import com.siem.siemusuarios.utils.Constants;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ServerApi {

    @GET(Constants.API_MOTIVOS_PRECATEGORIZACION)
    Call<Object> getMotivosPrecategorizacion();

}
