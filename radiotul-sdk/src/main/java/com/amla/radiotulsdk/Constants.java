package com.amla.radiotulsdk;

/**
 * Created by dengue8830 on 1/31/17.
 */

public class Constants {
    public static final String SERVER_URL = "https://radiotul.com";
    public static final String BASE_MOBILE_API_URL = SERVER_URL+"/Apis/ApiMobile";
    public static final String API_SIGN_IN = BASE_MOBILE_API_URL + "/IniciarSesion";
    public static final String API_SIGN_UP = BASE_MOBILE_API_URL + "/RegistrarUsuario";
    public static final String API_GET_EVENTS = BASE_MOBILE_API_URL + "/GetEventos";
    public static final int AUDIENCE_USER_TYPE = 3;
    public static final int EVENT_TYPE_EVENTUAL = 1;
    public static final int EVENT_TYPE_GAME = 2;
}