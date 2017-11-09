package com.siem.siemusuarios.interfaces;


import com.siem.siemusuarios.model.api.ResponseGenerarAuxilio;
import com.siem.siemusuarios.model.api.ResponseMotivos;
import com.siem.siemusuarios.model.api.ResponseSuscribirse;
import com.siem.siemusuarios.utils.Constants;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

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

    @FormUrlEncoded
    @POST(Constants.API_SUSCRIBIRSE_AUXILIO)
    Call<ResponseSuscribirse> suscribirse(
            @Path(Constants.KEY_CODIGO_AUXILIO) String codigo_auxilio,
            @Field(Constants.KEY_CODIGO) String codigo);

    @DELETE(Constants.API_CANCELAR_SUSCRIPCION_AUXILIO)
    Call<Object> desuscribirse(
            @Path(Constants.KEY_CODIGO_AUXILIO) String codigo_auxilio,
            @Path(Constants.KEY_CODIGO_FCM) String codigo_fcm);

}
