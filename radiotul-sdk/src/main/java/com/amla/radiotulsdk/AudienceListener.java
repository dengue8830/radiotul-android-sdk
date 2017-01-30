package com.amla.radiotulsdk;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

/**
 * Created by dengue8830 on 1/28/17.
 */

public class AudienceListener {
    public static void prueba(){
        Log.i("probando sdk", "si porbaodno");
        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                "https://radiotul.com/Apis/ApiMobile/GetEventos?id=30&idPerfil=87",
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("probando sdk", response.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("probando sdk", error.getMessage()+"");
            }
        });
    }
}
