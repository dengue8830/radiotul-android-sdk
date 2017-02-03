package com.amla.radiotulsdktestcase.event;

import com.amla.radiotulsdktestcase.Constants;
import com.amla.radiotulsdktestcase.RadiotulResponse;
import com.amla.radiotulsdktestcase.RadiotulSdk;
import com.amla.radiotulsdktestcase.util.Log;
import com.amla.radiotulsdktestcase.util.ModelParser;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dengue8830 on 2/2/17.
 *
 * Wrapper class to manage the API request and responses
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

    /**
     * SingUp the logged user to one event
     * @param eventId
     * @param callbacks
     */
    public static void signUpOnEvent(final int eventId, final RadiotulResponse.SimpleCallback callbacks) {
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

    /**
     * Gets the events that the logged user has won but he doesn't see yet
     * @param callbacks
     */
    public static void getMyNotSeenWonEvents(final RadiotulResponse.GetMyWonEventsNotSeen callbacks) {
        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                Constants.GET_MY_NOT_SEEN_WON_EVENTS + "?idPerfil=" + RadiotulSdk.getInstance().getUserLoggedIn().getProfileId() + "&idEmpresa=" + RadiotulSdk.getInstance().getCompanyId(),
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(final JSONObject response) {
                        try {
                            JSONArray jsonResult = response.getJSONArray("jsonResult");

                            if (jsonResult.length() == 0) {
                                callbacks.onSuccess(Collections.<Event>emptyList());
                                return;
                            }

                            List<Event> events = new ArrayList<>();

                            for (int j = 0; j < jsonResult.length(); j++) {
                                JSONObject item = (JSONObject) jsonResult.get(j);
                                Event event = new Event();
                                event.setName(item.getString("NombreEvento"));
                                event.setId(item.getInt("Id"));
                                events.add(event);
                            }
                            callbacks.onSuccess(events);
                        } catch (JSONException e) {
                            Log.e(TAG, e);
                            callbacks.onRequestError();
                            callbacks.onError();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, error);
                callbacks.onUnexpectedError();
                callbacks.onError();
            }
        });

        RadiotulSdk.getInstance().startRequest(request);
    }

    /**
     * Mark events won as seen. This method must be used when the logged user has seen the events
     * retreived from the api getMyNotSeenWonEvents
     * Only the event's id is needed but we request you the model list because is more easy if you send
     * the list retreived from the api metioned above
     * TODO: poner el link a el metodo getMyNotSeenWonEvents
     * @param events to mark as seen
     * @param callbacks
     */
    public static void markWonEventsAsViwed(List<Event> events, final RadiotulResponse.SimpleCallback callbacks){

        StringRequest request = new StringRequest(Request.Method.POST, Constants.MARK_WON_EVENTS_AS_VIWED+"?ids="+getStringIds(events),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        callbacks.onSuccess();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callbacks.onRequestError();
                        callbacks.onError();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                return params;
            }
        };

        RadiotulSdk.getInstance().startRequest(request);
    }

    /**
     * Private method to cast the events to the requested format
     * @param events
     * @return
     */
    private static String getStringIds(List<Event> events){
        String ids = "";

        for (Event event : events) {
            //adding the first comma to be removed later
            ids += ",";
            ids += event.getId();
        }
        ids.replaceFirst(",", "");

        return ids;
    }
}
