package com.amla.radiotulsdk.event;

import com.amla.radiotulsdk.util.Log;
import com.amla.radiotulsdk.Constants;
import com.amla.radiotulsdk.RadiotulResponse;
import com.amla.radiotulsdk.RadiotulSdk;
import com.amla.radiotulsdk.util.ModelParser;
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
    public static void getEvents(long radioId, final RadiotulResponse.GetEvents callbacks){
        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                Constants.GET_EVENTS_API + "?id=" + radioId + "&idPerfil=" + RadiotulSdk.getInstance().getUserSignedIn().getProfileId(),
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
    public static void signUpOnEvent(final long eventId, final RadiotulResponse.SimpleCallback callbacks) {
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
                params.put("idPerfil", RadiotulSdk.getInstance().getUserSignedIn().getProfileId()+ "");
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
    public static void getMyNotSeenWonEvents(final RadiotulResponse.GetEvents callbacks) {
        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                Constants.GET_MY_NOT_SEEN_WON_EVENTS + "?idPerfil=" + RadiotulSdk.getInstance().getUserSignedIn().getProfileId() + "&idEmpresa=" + RadiotulSdk.getInstance().getCompanyId(),
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
                                //Se envio con un formato diferente, no puede usarse el que esta en ModelParser
                                JSONObject item = (JSONObject) jsonResult.get(j);
                                Event event = new Event();
                                event.setName(item.getString("NombreEvento"));
                                event.setId(item.getLong("Id"));
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

        StringRequest request = new StringRequest(Request.Method.POST, Constants.MARK_MY_WON_EVENTS_AS_VIWED +"?ids="+getStringIds(events),
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

    /**
     * Get the prizes of the event
     * @param eventId
     * @param callbacks
     */
    public static void getEventPrizes(long eventId, final RadiotulResponse.GetEventPrizes callbacks){
        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                Constants.GET_EVENT_PRIZES+"?idEvento="+eventId,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            callbacks.onSuccess(ModelParser.getPrizes(response.getJSONArray("result")));
                        }catch (JSONException e){
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
                }
        );

        RadiotulSdk.getInstance().startRequest(request);
    }

    /**
     * Gets the events that the user has won
     * @param callbacks
     */
    public static void getMyWonEvents(final RadiotulResponse.GetEvents callbacks){
        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                Constants.GET_MY_WON_EVENTS + "?idPerfil=" + RadiotulSdk.getInstance().getUserSignedIn().getProfileId() + "&idEmpresa=" + RadiotulSdk.getInstance().getCompanyId(),
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            callbacks.onSuccess(ModelParser.toEvents(response.getJSONArray("result")));
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
