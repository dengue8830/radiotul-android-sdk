package com.amla.radiotulsdktestcase.message;

import com.amla.radiotulsdktestcase.Constants;
import com.amla.radiotulsdktestcase.RadiotulResponse;
import com.amla.radiotulsdktestcase.RadiotulSdk;
import com.amla.radiotulsdktestcase.events.CommentType;
import com.amla.radiotulsdktestcase.util.Log;
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
    private void sendMessageToShow(final long showId, final String message, final RadiotulResponse.SimpleCallback callbacks) {
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
                params.put("idUsuario", "" + RadiotulSdk.getInstance().getUserLoggedIn().getId());
                params.put("idEmpresa", "" + RadiotulSdk.getInstance().getCompanyId());
                params.put("idPrograma", "" + showId);
                params.put("idTipoMensaje", "" + CommentType.ID_OTRO);
                params.put("comentario", message);

                return params;
            }
        };

        RadiotulSdk.getInstance().startRequest(postRequest);
    }
}
