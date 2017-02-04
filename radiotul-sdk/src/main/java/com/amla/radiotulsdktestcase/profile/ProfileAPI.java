package com.amla.radiotulsdktestcase.profile;

import com.amla.radiotulsdktestcase.Constants;
import com.amla.radiotulsdktestcase.RadiotulResponse;
import com.amla.radiotulsdktestcase.RadiotulSdk;
import com.amla.radiotulsdktestcase.util.Log;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

/**
 * Created by dengue8830 on 2/3/17.
 */

public class ProfileAPI {
    public static final String TAG = ProfileAPI.class.getName();

    public static void updateProfile(String firstName, String lastName, String sex, String dni,
                                     long phoneCompanyId, String phone, String parsedBirthDay, final RadiotulResponse.SimpleCallback callbacks){
        String urlApiEditar = Constants.EDIT_PROFILE_API
                + "?idPerfil=" + RadiotulSdk.getInstance().getUserLoggedIn().getProfileId()
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
