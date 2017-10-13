package com.siem.siemusuarios.utils;

import android.Manifest;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by Lucas on 20/9/17.
 */

public class Constants {

    public static final String KEY_PERFIL = "KEY_PERFIL";

    /**
     * Number SIEM
     */
    public static final String NUMBER_SIEM = "541166036790";
    public static final String PACKAGE_WHATSAPP = "com.whatsapp";

    /**
     * Fonts
     */
    public static final String PRIMARY_FONT = "fonts/rounded_elegance.ttf";
    public static final String LOGO_FONT = "fonts/logo_font.ttf";

    /**
     * Date
     */
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy", Locale.US);

    /**
     * API Server
     */
    public static final int CODE_SERVER_OK = 200;
    public static final int CODE_BAD_REQUEST = 400;
    public static final int CODE_UNAUTHORIZED = 401;
    public static final int CODE_SERVER_ERROR = 500;
    public static final String API_MOTIVOS_PRECATEGORIZACION = "rules/motivospc/";
    public static final String API_MOTIVOS_AJUSTE = "rules/motivosajuste/";

    /**
     * PlaceAutocomplete API
     */
    public static final String CODE_ARGENTINA = "AR";
    public static final String FORMAT_NO_DIRECTION = "%1$s, %2$s";
    public static final DecimalFormat df = new DecimalFormat(".000");

    /**
     * Permissions
     */
    public static final String[] permissions = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };

    /**
     * ENUM AsincTask Geocoder
     */
    public enum ReturnAsincTask {
        OK_ADDRESS,
        NO_ADDRESS,
        IOEXCEPTION
    }
}
