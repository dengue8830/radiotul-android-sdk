package com.amla.radiotulsdk.message;

import com.amla.radiotulsdk.util.Log;
import com.amla.radiotulsdk.Constants;
import com.amla.radiotulsdk.RadiotulResponse;
import com.amla.radiotulsdk.RadiotulSdk;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dengue8830 on 2/3/17.
 */

public class MessageAPI {
    private static final String TAG = MessageAPI.class.getName();

    /**
     * Send a message to the show with the logged user as sender
     * @param showId the show id
     * @param message message to send
     * @param callbacks callbacks
     */
    public static void sendMessageToShow(final long showId, final String message, final RadiotulResponse.SimpleCallback callbacks) {
        StringRequest postRequest = new StringRequest(Request.Method.POST, Constants.SEND_MESSAGE_API,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        callbacks.onSuccess();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("ENVIAR_MENSAJE_ERROR", error);
                        callbacks.onRequestError();
                        callbacks.onError();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("idUsuario", "" + RadiotulSdk.getInstance().getUserSignedIn().getId());
                params.put("idEmpresa", "" + RadiotulSdk.getInstance().getCompanyId());
                params.put("idPrograma", "" + showId);
                params.put("idTipoMensaje", "" + Constants.COMMENT_TYPE_OTHER);
                params.put("comentario", message);

                return params;
            }
        };

        RadiotulSdk.getInstance().startRequest(postRequest);
    }

    public static void sendMessageToCompany(final String message, final long commentType, final RadiotulResponse.SimpleCallback callbacks){
        StringRequest request = new StringRequest(Request.Method.POST, Constants.SEND_MESSAGE_API,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        callbacks.onSuccess();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, error);
                        callbacks.onRequestError();
                        callbacks.onError();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("idUsuario", String.valueOf(RadiotulSdk.getInstance().getUserSignedIn().getId()));
                params.put("idEmpresa", String.valueOf(RadiotulSdk.getInstance().getCompanyId()));
                params.put("idPrograma", "-1");
                params.put("idTipoMensaje", String.valueOf(commentType));
                params.put("comentario", message);

                return params;
            }
        };

        RadiotulSdk.getInstance().startRequest(request);
    }
}
