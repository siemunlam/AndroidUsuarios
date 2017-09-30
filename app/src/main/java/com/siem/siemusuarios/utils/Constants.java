package com.siem.siemusuarios.utils;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by Lucas on 20/9/17.
 */

public class Constants {

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

}
