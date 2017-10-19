package com.siem.siemusuarios.interfaces;


import com.siem.siemusuarios.model.api.ResponseGenerarAuxilio;
import com.siem.siemusuarios.model.api.ResponseMotivos;
import com.siem.siemusuarios.utils.Constants;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ServerApi {

    @FormUrlEncoded
    @POST(Constants.API_GENERAR_AUXILIO)
    Call<ResponseGenerarAuxilio> generarAuxilio(
            @Field(Constants.KEY_CONTACTO) String contacto,
            @Field(Constants.KEY_UBICACION) String ubicacion,
            @Field(Constants.KEY_REFERENCIA) String referencia,
            @Field(Constants.KEY_LATITUD) String latitud,
            @Field(Constants.KEY_LONGITUD) String longitud,
            @Field(Constants.KEY_MOTIVO) String motivo,
            @Field(Constants.KEY_ORIGEN) String origen,
            @Field(Constants.KEY_NOMBRE) String nombre,
            @Field(Constants.KEY_SEXO) String sexo,
            @Field(Constants.KEY_OBSERVACIONES) String observaciones);

    @GET(Constants.API_MOTIVOS_PRECATEGORIZACION)
    Call<ResponseMotivos> getMotivosPrecategorizacion();

    @GET(Constants.API_MOTIVOS_AJUSTE)
    Call<ResponseMotivos> getMotivosAjuste();

}
