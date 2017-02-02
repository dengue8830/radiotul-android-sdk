package com.amla.radiotulsdk.access;

import com.amla.radiotulsdk.Constants;
import com.amla.radiotulsdk.RadiotulResponse;
import com.amla.radiotulsdk.RadiotulSdk;
import com.amla.radiotulsdk.util.Log;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by dengue8830 on 2/2/17.
 */

public class Access {
    private static final String TAG = Access.class.getName();

    public static void signIn(String email, String pass, final RadiotulResponse.SignIn signIn) {
        String url = Constants.API_SIGN_IN
                + "?idEmpresa="
                + RadiotulSdk.getInstance().getCompanyId()
                + "&loginSocial="
                + false
                + "&idSocial=-1"
                + "&email="
                + email
                + "&contrasenia="
                + pass;

        final JsonObjectRequest jsonDestacados = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                User user;

                try {
                    user = decodeUser(response, false);
                } catch (JSONException e) {
                    Log.e(TAG, "Something went wrong parsing the response of the sdk's API. It's our fault, please contact us to fix it soporteradiotul@amla.com.ar");
                    signIn.onUnexpectedError();
                    signIn.onError();
                    return;
                } catch (SignInWorngDataException e){
                    Log.e(TAG, e);
                    signIn.onWrongData();
                    return;
                }

                signIn.onSuccess(user);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, error);
                signIn.onConnectionError();
                signIn.onError();
            }
        });

        RadiotulSdk.getInstance().startRequest(jsonDestacados);
    }

    private static User decodeUser(JSONObject data, boolean loginFacebook) throws JSONException, SignInWorngDataException {
        String userDataType;

        if (loginFacebook) {
            userDataType = "usuarioSocial";
        } else {
            userDataType = "usuario";
        }

        if(!data.getBoolean(userDataType))
            throw new SignInWorngDataException("email or pass was wrong");

        JSONObject usuarioJson = data.getJSONObject("jsonResult");
        User user = new User();
        user.setUserId(usuarioJson.getInt("Id"));
        user.setSocialLoginId(usuarioJson.getInt("IdLoginSocial"));
        user.setSocialId(usuarioJson.getString("IdSocial"));
        user.setProfileId(Integer.parseInt(usuarioJson.getString("IdPerfil")));
        user.setFirstName(usuarioJson.getString("Nombre"));
        user.setLastName(usuarioJson.getString("Apellido"));
        user.setEmail(usuarioJson.getString("Email"));
        user.setSex(usuarioJson.getString("Sexo"));
        user.setDni(usuarioJson.getString("Dni"));
        user.setParsedBirthDay(usuarioJson.getString("FechaNacimiento"));
        user.setPhoneCompany(usuarioJson.getString("Operador"));
        user.setPhone(usuarioJson.getString("NumeroTelefono"));
        user.setProfilePictureUrl(usuarioJson.getString("UrlFotoPerfil"));

        return  user;
    }
}
