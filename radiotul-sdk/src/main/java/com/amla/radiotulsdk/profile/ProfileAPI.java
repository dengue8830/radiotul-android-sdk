package com.amla.radiotulsdk.profile;

import com.amla.radiotulsdk.util.Log;
import com.amla.radiotulsdk.Constants;
import com.amla.radiotulsdk.RadiotulResponse;
import com.amla.radiotulsdk.RadiotulSdk;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

/**
 * Created by dengue8830 on 2/3/17.
 */

public class ProfileAPI {
    public static final String TAG = ProfileAPI.class.getName();

    /**
     * Update the logged user's profile data
     * @param firstName
     * @param lastName
     * @param sex
     * @param dni
     * @param phoneCompanyId
     * @param phone
     * @param parsedBirthDay format yyyy-MM-dd example: 2000-04-25
     * @param callbacks
     */
    public static void updateProfile(String firstName, String lastName, String sex, String dni,
                                     long phoneCompanyId, String phone, String parsedBirthDay, final RadiotulResponse.SimpleCallback callbacks){
        String urlApiEditar = Constants.EDIT_PROFILE_API
                + "?idPerfil=" + RadiotulSdk.getInstance().getUserSignedIn().getProfileId()
                + "&nombre=" + firstName
                + "&apellido=" + lastName
                + "&sexo=" + sex
                + "&dni=" + dni
                + "&operador=" + phoneCompanyId
                + "&telefono=" + phone
                + "&FechaNacimiento=" + parsedBirthDay;

        StringRequest postRequest = new StringRequest(Request.Method.POST, urlApiEditar,
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
        );

        RadiotulSdk.getInstance().startRequest(postRequest);
    }
}
