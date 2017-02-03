package com.amla.radiotulsdktestcase;

/**
 * Created by dengue8830 on 1/31/17.
 */

public class Constants {
    public static final String SERVER_URL = BuildConfig.SERVER_URL_VALUE;
    public static final String BASE_MOBILE_API_URL = SERVER_URL+"/Apis/ApiMobile";
    public static final String SIGN_IN_API = BASE_MOBILE_API_URL + "/IniciarSesion";
    public static final String SIGN_UP_API = BASE_MOBILE_API_URL + "/RegistrarUsuario";
    public static final String GET_EVENTS_API = BASE_MOBILE_API_URL + "/GetEventos";
    public static final String SIGN_UP_ON_EVENT_API = BASE_MOBILE_API_URL + "/ParticiparEvento";
    public static final int AUDIENCE_USER_TYPE = 3;
    public static final int EVENT_TYPE_EVENTUAL = 1;
    public static final int EVENT_TYPE_GAME = 2;
}