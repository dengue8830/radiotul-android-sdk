package com.amla.radiotulsdktestcase.audiencelistener;

import android.content.Context;
import android.util.Log;

import com.amla.radiotulsdktestcase.Constants;
import com.amla.radiotulsdktestcase.RadiotulSdk;
import com.amla.radiotulsdktestcase.RadiotulNotInitializedException;

import java.util.Timer;
import java.util.TimerTask;

import microsoft.aspnet.signalr.client.Action;
import microsoft.aspnet.signalr.client.ConnectionState;
import microsoft.aspnet.signalr.client.ErrorCallback;
import microsoft.aspnet.signalr.client.LogLevel;
import microsoft.aspnet.signalr.client.Logger;
import microsoft.aspnet.signalr.client.Platform;
import microsoft.aspnet.signalr.client.http.android.AndroidPlatformComponent;
import microsoft.aspnet.signalr.client.hubs.HubConnection;
import microsoft.aspnet.signalr.client.hubs.HubProxy;

/**
 * Created by dengue8830 on 1/28/17.
 */

public class AudienceListener {
    //15 secs BUGFIX: if it's too small opens a new connection before the last timer task comes with the "ok" to stop the timer
    private static final long RECONNECT_BY_FORCE_TIMEOUT = 15000;
    public static HubProxy mHubProxy;
    private static HubConnection mHubConnection;
    private static boolean initialized = false;
    private static Context mContext;
    //Bandera para saber si intentar reconectar al perder la conexion. El valor lo asigna play o stop
    private static boolean tryReconnect;
    //Bandera para desconectarse tras conectarse. Parte del bugfix de play/pause rapido. El valor lo asigna play o stop
    private static boolean abort;
    private static final String TAG = AudienceListener.class.getName();
    private static final String USER_TYPE_APP = "APP";
    private static long mRadioId;

    /**
     * Prepara la conexion
     */
    public static void init(String email, long idUsuario) throws RadiotulNotInitializedException{
        if(!RadiotulSdk.isInitialized())
            throw new RadiotulNotInitializedException("The sdk mudt be initialized first!");

        mContext = RadiotulSdk.getInstance().getContext();

        //No se inicializara mas de una vez. BUGFIX: Daba problemas en onCreate de PasajeroPrincipalFragment.java al volver a ese fragment en cada solicitud cancelada por chofer
        if (initialized)
            return;

        initialized = true;

        StringBuilder sbQueryString = new StringBuilder();
        sbQueryString
                .append("&email=")
                .append(email)
                .append("&idUsuario=")
                .append(idUsuario)
                .append("&tipoUsuario=")
                .append(USER_TYPE_APP)
                .append("&idEmpresa=")
                .append(RadiotulSdk.getInstance().getCompanyId())
                .append("&idRadio=")
                .append(mRadioId);

        //Configuramos para usar esta plataforma
        Platform.loadPlatformComponent(new AndroidPlatformComponent());

        //Creamos la conexion
        mHubConnection = new HubConnection(Constants.SERVER_URL, sbQueryString.toString(), true, new Logger() {
            @Override
            public void log(String message, LogLevel level) {
                //Log de all lo que pasa en signalr
                //Log.i(TAG, message);
            }
        });

        //Seteamos los listeners para la conexion
        initConnectionListeners();

        mHubProxy = mHubConnection.createHubProxy("JobHub");
    }

    private static void initConnectionListeners(){
        mHubConnection.error(new ErrorCallback() {
            @Override
            public void onError(Throwable error) {
                //error.printStackTrace();
                Log.e(TAG, error.getCause() + " - " + error.getMessage());
            }
        });

        //Como esta clase es para informar de una reproduccion, toda conexion implica incrementar ese contador
        //ya que la desconexion lo decrementara automaticamente en el server
        mHubConnection.connected(new Runnable() {
            @Override
            public void run() {
                Log.i(TAG, "CONNECTED");

                //Parte del bugfix de play/stop rapido
                if(abort)
                    stop();
            }
        });

        mHubConnection.closed(new Runnable() {
            @Override
            public void run() {
                Log.i(TAG, "DISCONNECTED");

                if (!tryReconnect)
                    return;

                reconnectByForce();
            }
        });

        mHubConnection.reconnecting(new Runnable() {
            @Override
            public void run() {
                Log.i(TAG, "RECONNECTING");
                tryReconnect = true;
            }
        });

        mHubConnection.reconnected(new Runnable() {
            @Override
            public void run() {
                Log.i(TAG, "RECONNECTED");
                tryReconnect = false;
            }
        });
    }

    private static void reconnectByForce() {
        final Timer timer = new Timer(false);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                //BUGFIX: al matar signalr y ya habiendo establecido el timer para reconexion falla en el siguiente intento
                //de reconexion porque mhubconnection es null
                if(!tryReconnect){
                    timer.cancel();
                    return;
                }

                if (isConnecting())
                    return;

                if (isConnected()) {
                    timer.cancel();
                    return;
                }

                Log.i(TAG, "RECONNECTING BY FORCE");
                mHubConnection.start().done(new Action<Void>() {
                    @Override
                    public void run(Void v) throws Exception {
                        Log.i(TAG, "NEW CONNECTION ESTABLISHED");
                        timer.cancel();
                    }
                });
            }
        }, RECONNECT_BY_FORCE_TIMEOUT);
    }

    /**
     * Detiene la comunicacion pero no destruye la configuracion, puede darse start() sin problemas
     */
    public static void stop() {
        if(!initialized)
            //TODO: Replace with AudienceListenerNotInitializedException but must be catcheable by runneable
            throw new RuntimeException("You must initialize the listener first!");

        tryReconnect = false;
        abort = true;

        /*
        * BUGFIX: Si se da play/stop rapido da un error por intentar dar stop cuando esta en estado connecting
        * La solucion es colocar abort = true; y evitar dar stop si no esta en estado conectado (eso lo tildaba y ya no se podia detener el hub)
        * y en el callback de connected leer la bandera abort, si es true es porque se dio stop.
        * Entonces al dar play/stop rapido el stop cambiara los flags y como esta en connecting no hara el stop (porque se fallaria y clavaria el hub)
        * y al entrar al callback de connected leera la bandera abort == true y llamara a stop y ahora si estara en estado connected y lo desconectara
        */
        if(isConnected())
            mHubConnection.stop();
    }

    /**
     * Inica la comunicacion, al conectarse incrementara la audiencia
     */
    public static void start(long radioId) {
        if(!initialized)
            //TODO: Replace with AudienceListenerNotInitializedException but must be catcheable by runneable
            throw new RuntimeException("You must initialize the listener first!");

        mRadioId = radioId;
        tryReconnect = true;
        abort = false;
        //Este metodo ya controla que no se pueda dar start varias veces seguidas o si ya esta conectado
        mHubConnection.start();
    }

    public static boolean isConnected() {
        //Nullsafe
        return mHubConnection != null && mHubConnection.getState().equals(ConnectionState.Connected);
    }

    public static boolean isConnecting() {
        //Nullsafe
        return mHubConnection != null && mHubConnection.getState().equals(ConnectionState.Connecting);
    }
}
