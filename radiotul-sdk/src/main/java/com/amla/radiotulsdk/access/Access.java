package com.amla.radiotulsdk.access;

import android.os.Build;
import android.support.annotation.NonNull;

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

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by dengue8830 on 2/2/17.
 */

public class Access {
    private static final String TAG = Access.class.getName();

    public static void signIn(@NonNull String email, @NonNull String pass, @NonNull final RadiotulResponse.SignIn signIn) {
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
                signIn.onRequestError();
                signIn.onError();
            }
        });

        RadiotulSdk.getInstance().startRequest(jsonDestacados);
    }

    public static void signUp(boolean socialLogin,
                              String socialId,
                              @NonNull String token,
                              @NonNull String firstName,
                              @NonNull String lastName,
                              String sex,
                              String dni,
                              String parsedBirthday,
                              String email,
                              String password,
                              String phone,
                              int phoneCompanyId,
                              final RadiotulResponse.SignUp signUpCallbacks){

        StringBuilder urlGET = new StringBuilder();

        try {
            urlGET.append(Constants.API_SIGN_UP)
                    .append("?idEmpresa=")
                    .append(RadiotulSdk.getInstance().getCompanyId())
                    .append("&loginSocial=")
                    .append(socialLogin)
                    .append("&idSocial=")
                    .append(socialId)//0, xxx
                    .append("&token=")
                    .append(token)//0, xxx
                    .append("&email=")
                    .append(email)
                    .append("&contrasenia=")
                    .append(password)//pass, nada
                    .append("&idTipoUsuario=")
                    .append(Constants.AUDIENCE_USER_TYPE)
                    .append("&numeroTelefono=")
                    .append(phone)//tel, nada
                    .append("&softwareVersion=")
                    .append(Build.VERSION.RELEASE)//TODO: esto lo puedo saber desde la lib? o lo tengo que pedir al inicializar el sdk?
                    .append("&operador=")
                    .append(phoneCompanyId)//phoneCompanyId, nada
                    .append("&nombre=")
                    .append(URLEncoder.encode(firstName, "UTF-8"))
                    .append("&apellido=")
                    .append(URLEncoder.encode(lastName, "UTF-8"))
                    .append("&fechaNacimiento=")
                    .append(parsedBirthday)
                    .append("&sexo=")
                    .append(sex)
                    .append("&dni=")
                    .append(dni);
        } catch (UnsupportedEncodingException e) {
            Log.e(TAG, e);
            signUpCallbacks.onUnexpectedError();
            signUpCallbacks.onError();
            return;
        }

        final JsonObjectRequest signupRequest = new JsonObjectRequest(Request.Method.GET,
                urlGET.toString(),
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("API_REGISTRAR_USUARIO", response.toString());

                        try {
                            if (response.getBoolean("usuarioExiste")){
                                signUpCallbacks.onEmailAlreadyExists();
                                return;//usario ya existe
                            }
                        } catch (JSONException e) {
                            Log.e(TAG, e);
                            signUpCallbacks.onUnexpectedError();
                            signUpCallbacks.onError();
                            return;
                        }

                        signUpCallbacks.onSuccess();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, error);
                signUpCallbacks.onRequestError();
                signUpCallbacks.onError();
            }
        });

        RadiotulSdk.getInstance().startRequest(signupRequest);
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
