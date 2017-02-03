package com.amla.radiotulsdk.events;

import com.amla.radiotulsdk.Constants;
import com.amla.radiotulsdk.RadiotulResponse;
import com.amla.radiotulsdk.RadiotulSdk;
import com.amla.radiotulsdk.util.Log;
import com.amla.radiotulsdk.util.ModelParser;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dengue8830 on 2/2/17.
 */

public class EventsAPI {
    public static final String TAG = EventsAPI.class.getName();

    /**
     * Just that, get the radio's events
     * @param radioId
     * @param callbacks
     */
    public static void getEvents(int radioId, final RadiotulResponse.GetEvents callbacks){
        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                Constants.GET_EVENTS_API + "?id=" + radioId + "&idPerfil=" + RadiotulSdk.getInstance().getUserLoggedIn().getProfileId(),
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            callbacks.onSuccess(ModelParser.toEvents(response.getJSONArray("resultEventos")));
                        } catch (JSONException e) {
                            Log.e(TAG, e);
                            callbacks.onUnexpectedError();
                            callbacks.onError();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, error);
                callbacks.onRequestError();
                callbacks.onError();
            }
        });

        RadiotulSdk.getInstance().startRequest(request);
    }

    private void signUpOnEvent(final int eventId, final RadiotulResponse.SignUpOnEvent callbacks) {
        StringRequest postRequest = new StringRequest(Request.Method.POST, Constants.SIGN_UP_ON_EVENT_API,
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
                params.put("idPerfil", RadiotulSdk.getInstance().getUserLoggedIn().getProfileId()+ "");
                params.put("idEvento", eventId + "");

                return params;
            }
        };

        RadiotulSdk.getInstance().startRequest(postRequest);
    }
}
