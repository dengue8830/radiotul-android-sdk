package com.amla.radiotulsdk.company;

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
 * Created by dengue8830 on 2/3/17.
 */

public class CompanyAPI {
    private static final String TAG = CompanyAPI.class.getName();

    /**
     * Get the complete data of the company
     * @param callbacks
     */
    public static void getCompanyData(final RadiotulResponse.GetCompanyData callbacks){
        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                Constants.GET_COMPANY_DATA_API + "?id=" + RadiotulSdk.getInstance().getCompanyId(),
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            callbacks.onSuccess(ModelParser.toCompany(response));
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
