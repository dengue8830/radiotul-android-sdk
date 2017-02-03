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
    public static final String SEND_MESSAGE_API = BASE_MOBILE_API_URL + "/CrearNuevoMensaje";
    public static final String GET_MY_NOT_SEEN_WON_EVENTS = BASE_MOBILE_API_URL + "/GetEventosGanadosNoVistos";
    public static final String MARK_MY_WON_EVENTS_AS_VIWED = BASE_MOBILE_API_URL + "/EventosGanadosVistos";
    public static final String GET_WEEK_SHOW_SCHEDULE_API = BASE_MOBILE_API_URL + "/GetProgramas";
    public static final String GET_EVENT_PRIZES = BASE_MOBILE_API_URL + "/GetPremiosEventos";
    public static final String GET_MY_WON_EVENTS = BASE_MOBILE_API_URL + "/GetEventosGanados";
    public static final int AUDIENCE_USER_TYPE = 3;
    public static final int EVENT_TYPE_EVENTUAL = 1;
    public static final int EVENT_TYPE_GAME = 2;
    public static final int COMMENT_TYPE_GOOD = 1;
    public static final int COMMENT_TYPE_BAD = 2;
    public static final int COMMENT_TYPE_OTHER = 3;
}