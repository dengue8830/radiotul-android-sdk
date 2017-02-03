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

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by dengue8830 on 2/2/17.
 */

public class EventsAPI {
    public static final String TAG = EventsAPI.class.getName();

    public static void getEvents(int radioId, final RadiotulResponse.Events callbacks){
        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                Constants.API_GET_EVENTS + "?id=" + radioId + "&idPerfil=" + RadiotulSdk.getInstance().getUserLoggedIn().getProfileId(),
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
}
