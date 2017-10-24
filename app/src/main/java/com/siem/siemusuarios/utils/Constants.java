package com.siem.siemusuarios.utils;

import android.Manifest;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by Lucas on 20/9/17.
 */

public class Constants {

    public static final String FORMAT_NOMBRE_APELLIDO = "%1$s %2$s";
    public static final int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1010;
    public static final int KEY_AUXILIO_GENERADO = 1050;

    /**
     * Key saveInstanceState
     */
    public static final String KEY_PERFIL = "KEY_PERFIL";
    public static final String KEY_AUXILIO = "KEY_AUXILIO";
    public static final String KEY_DIRECCION = "KEY_DIRECCION";
    public static final String KEY_LAT = "KEY_LATITUD";
    public static final String KEY_LNG = "KEY_LONGITUD";
    public static final String KEY_MOTIVOS = "KEY_MOTIVOS";
    public static final String KEY_SELECCIONAR_CONTACTO_STRATEGY = "KEY_SELECCIONAR_CONTACTO_STRATEGY";

    /**
     * Number SIEM
     */
    public static final String NUMBER_SIEM = "541166036790";
    public static final String PACKAGE_WHATSAPP = "com.whatsapp";

    /**
     * Fonts
     */
    public static final String PRIMARY_FONT = "fonts/sanfrancisco.otf";
    public static final String PRIMARY_FONT_BOLD = "fonts/sanfrancisco_bold.otf";
    public static final String LOGO_FONT = "fonts/logo_font.ttf";

    /**
     * Date
     */
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy", Locale.US);

    /**
     * API Server
     */
    public static final int CODE_SERVER_OK = 200;
    public static final int CODE_SERVER_OK_201 = 201;
    public static final int CODE_BAD_REQUEST = 400;
    public static final int CODE_UNAUTHORIZED = 401;
    public static final int CODE_SERVER_ERROR = 500;
    public static final String API_GENERAR_AUXILIO = "auxilios/";
    public static final String API_MOTIVOS_PRECATEGORIZACION = "rules/motivospc/";
    public static final String API_MOTIVOS_AJUSTE = "rules/motivosajuste/";
    public static final String API_SUSCRIBIRSE_AUXILIO = "auxilios/{code}/suscribirse/";

    public static final String KEY_CONTACTO = "contacto";
    public static final String KEY_UBICACION = "ubicacion";
    public static final String KEY_REFERENCIA = "ubicacion_especifica";
    public static final String KEY_LATITUD = "latitud_gps";
    public static final String KEY_LONGITUD = "longitud_gps";
    public static final String KEY_MOTIVO = "motivo";
    public static final String KEY_ORIGEN = "origen";
    public static final String KEY_NOMBRE = "nombre";
    public static final String KEY_SEXO = "sexo";
    public static final String KEY_OBSERVACIONES = "observaciones";
    public static final String KEY_CODIGO = "codigo";
    public static final String KEY_CODE = "code";

    /**
     * Push Notifications Keys
     */
    public static final String KEY_STATUS = "status";
    public static final String KEY_TIMESTAMP = "timestamp";
    public static final String KEY_ESTIMACION = "estimacion";

    /**
     * PlaceAutocomplete API
     */
    public static final String CODE_ARGENTINA = "AR";
    public static final String FORMAT_NO_DIRECTION = "%1$s, %2$s";
    public static final DecimalFormat df = new DecimalFormat(".000");

    /**
     * Permissions
     */
    public static final String[] locations_permissions = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };
    public static final String[] contacts_permissions = {
            Manifest.permission.READ_CONTACTS
    };

    /**
     * Shared preferences constants
     */
    static final String NAME_SHAREDPREFERENCES = "SIEM_USUARIOS_PREFERENCES";

    /**
     * ENUM AsincTask Geocoder
     */
    public enum ReturnAsincTask {
        OK_ADDRESS,
        NO_ADDRESS,
        IOEXCEPTION
    }
}
