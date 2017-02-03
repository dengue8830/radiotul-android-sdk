package com.amla.radiotulsdktestcase.show;

import com.amla.radiotulsdktestcase.Constants;
import com.amla.radiotulsdktestcase.RadiotulResponse;
import com.amla.radiotulsdktestcase.RadiotulSdk;
import com.amla.radiotulsdktestcase.event.Show;
import com.amla.radiotulsdktestcase.util.Log;
import com.amla.radiotulsdktestcase.util.ModelParser;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dengue8830 on 2/3/17.
 *
 * Wrapper class to manage the API request and responses
 */

public class ShowAPI {
    private static final String TAG = ShowAPI.class.getName();

    /**
     * Get the radio's show schedule of all the week
     * @param radioId Radio of wich we will get the shows
     * @param callbacks The callbacks
     */
    public static void getWeekShowSchedule(long radioId, final RadiotulResponse.GetWeekShowSchedule callbacks) {
        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                Constants.GET_WEEK_SHOW_SCHEDULE_API + "?id=" + radioId + "&idUsuario=" + RadiotulSdk.getInstance().getUserLoggedIn().getId(),
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray rawSchedule = response.length() < 1 ? new JSONArray() : response.getJSONArray("Semana");
                            List<List> schedule = new ArrayList<>();

                            //Recorremos los dias segun la posicion de array
                            for (int day = 0; day < 7; day++) {
                                JSONObject joDiaDeProgramacion = (JSONObject) rawSchedule.get(day);
                                JSONArray rawShowsOfOneDay = new JSONArray(joDiaDeProgramacion.getString("Programa"));
                                List<Show> showsOfOneDay = new ArrayList<>();

                                //Recorremos los programas de un dia para armar la lista del dia
                                for (int i = 0; i < rawShowsOfOneDay.length(); i++) {
                                    JSONObject item = (JSONObject) rawShowsOfOneDay.get(i);
                                    showsOfOneDay.add(ModelParser.toShow(item));
                                }
                                schedule.add(showsOfOneDay);
                                callbacks.onSuccess(schedule);
                            }
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
                }
        );

        RadiotulSdk.getInstance().startRequest(request);
    }
}
