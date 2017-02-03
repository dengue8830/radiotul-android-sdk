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

    /**
     * SignIn to RadioTul
     * @param email
     * @param password
     * @param signInCallbacks
     */
    public static void signIn(@NonNull String email, @NonNull String password, @NonNull final RadiotulResponse.SignIn signInCallbacks) {
        String url = Constants.API_SIGN_IN
                + "?idEmpresa="
                + RadiotulSdk.getInstance().getCompanyId()
                + "&loginSocial="
                + false
                + "&idSocial=-1"
                + "&email="
                + email
                + "&contrasenia="
                + password;

        final JsonObjectRequest jsonDestacados = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    RadiotulSdk.getInstance().setUserLoggedIn(decodeUser(response, false));
                    signInCallbacks.onSuccess(RadiotulSdk.getInstance().getUserLoggedIn());
                } catch (UserNotFoundException e) {
                    Log.e(TAG, e);
                    signInCallbacks.onUserNotFound();
                    return;
                } catch (JSONException e) {
                    Log.e(TAG, "Something went wrong parsing the response of the sdk's API. It's our fault, please contact us to fix it soporteradiotul@amla.com.ar");
                    signInCallbacks.onUnexpectedError();
                    signInCallbacks.onError();
                    return;
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, error);
                signInCallbacks.onRequestError();
                signInCallbacks.onError();
            }
        });

        RadiotulSdk.getInstance().startRequest(jsonDestacados);
    }

    /**
     * Login to RadioTul with the response data provided from facebook login
     *
     * @param email
     * @param signInCallbacks
     * @param firstName
     * @param lastName
     * @param socialId
     * @param socialToken
     * @param sex
     * @param dni
     * @param parsedBirthday
     * @param password
     * @param phone
     * @param phoneCompanyId
     */
    public static void signInWithFacebookLoginResponseData(@NonNull final String email,
                                                           @NonNull final String password,
                                                           @NonNull final RadiotulResponse.SignIn signInCallbacks,
                                                           @NonNull final String firstName,
                                                           @NonNull final String lastName,
                                                           final String socialId,
                                                           final String socialToken,
                                                           final String sex,
                                                           final String dni,
                                                           final String parsedBirthday,
                                                           final String phone,
                                                           final int phoneCompanyId) {
        //Primero nos intentamos loguear en radiotul
        String url = Constants.API_SIGN_IN
                + "?idEmpresa="
                + RadiotulSdk.getInstance().getCompanyId()
                + "&loginSocial="
                + true
                + "&idSocial="
                + socialId
                + "&email="
                + email
                + "&contrasenia="
                + password;

        final JsonObjectRequest jsonDestacados = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    RadiotulSdk.getInstance().setUserLoggedIn(decodeUser(response, false));
                    signInCallbacks.onSuccess(RadiotulSdk.getInstance().getUserLoggedIn());
                } catch (UserNotFoundException e) {
                    //Si no existe porque es la primera vez que loguamos con facebook hay que crear un user en radiotul y luego loguearlo
                    signUp(true, socialId, socialToken, firstName, lastName, sex, dni,
                            parsedBirthday, email, password, phone, phoneCompanyId,
                            new RadiotulResponse.SignUp() {
                                @Override
                                public void onSuccess() {
                                    signIn(email, password, signInCallbacks);
                                }

                                @Override
                                public void onEmailAlreadyExists() {
                                    //can't be
                                    Log.e(TAG, "huu? email already exists? impossible! Contact us to fix this");
                                    signInCallbacks.onUnexpectedError();
                                    signInCallbacks.onError();
                                }

                                @Override
                                public void onUnexpectedError() {
                                    signInCallbacks.onUnexpectedError();
                                    signInCallbacks.onError();
                                }

                                @Override
                                public void onRequestError() {
                                    signInCallbacks.onRequestError();
                                    signInCallbacks.onError();
                                }

                                @Override
                                public void onError() {
                                    signInCallbacks.onError();
                                }
                            });
                } catch (JSONException e) {
                    Log.e(TAG, "Something went wrong parsing the response of the sdk's API. It's our fault, please contact us to fix it soporteradiotul@amla.com.ar");
                    signInCallbacks.onUnexpectedError();
                    signInCallbacks.onError();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, error);
                signInCallbacks.onRequestError();
                signInCallbacks.onError();
            }
        });

        RadiotulSdk.getInstance().startRequest(jsonDestacados);
    }

    /**
     * Simple no-social noSocialsignUp to RadioTul
     * Use this if you are register the user from your own form
     * otherwise use signInWithFacebookLoginResponseData
     *
     * @param firstName
     * @param lastName
     * @param sex
     * @param dni
     * @param parsedBirthday
     * @param email
     * @param password
     * @param phone
     * @param phoneCompanyId
     * @param signUpCallbacks
     */
    public static void noSocialsignUp(@NonNull String firstName,
                                      @NonNull String lastName,
                                      @NonNull String sex,
                                      String dni,
                                      @NonNull String parsedBirthday,
                                      @NonNull String email,
                                      @NonNull String password,
                                      String phone,
                                      int phoneCompanyId,
                                      @NonNull final RadiotulResponse.SignUp signUpCallbacks){

        signUp(true, "0", "0", firstName, lastName, sex, dni, parsedBirthday, email, password, phone, phoneCompanyId, signUpCallbacks);
    }

    /**
     * Full SignUp method to RadioTul
     * TODO: check the date format
     *
     * @param isSocialLogin was logged in with a social network
     * @param socialId social network's id provided by their signIn
     * @param socialToken facebook token provided by his signIn
     * @param firstName
     * @param lastName
     * @param sex
     * @param dni
     * @param parsedBirthday fomrmat: yyyy-MM-dd
     * @param email
     * @param password
     * @param phone
     * @param phoneCompanyId
     * @param signUpCallbacks
     */
    private static void signUp(boolean isSocialLogin,
                               String socialId,
                               String socialToken,
                               @NonNull String firstName,
                               @NonNull String lastName,
                               @NonNull String sex,
                               String dni,
                               @NonNull String parsedBirthday,
                               @NonNull String email,
                               @NonNull String password,
                               String phone,
                               Integer phoneCompanyId,
                               @NonNull final RadiotulResponse.SignUp signUpCallbacks) {

        StringBuilder urlGET = new StringBuilder();

        try {
            urlGET.append(Constants.API_SIGN_UP)
                    .append("?idEmpresa=")
                    .append(RadiotulSdk.getInstance().getCompanyId())
                    .append("&loginSocial=")
                    .append(isSocialLogin)
                    .append("&idSocial=")
                    .append(socialId)
                    .append("&token=")
                    .append(socialToken)
                    .append("&email=")
                    .append(email)
                    .append("&contrasenia=")
                    .append(password)//pass, email
                    .append("&idTipoUsuario=")
                    .append(Constants.AUDIENCE_USER_TYPE)
                    .append("&numeroTelefono=")
                    .append(phone != null ? phone : "")
                    .append("&softwareVersion=")
                    .append(Build.VERSION.RELEASE)//TODO: esto lo puedo saber desde la lib? o lo tengo que pedir al inicializar el sdk?
                    .append("&operador=")
                    .append(phoneCompanyId != null ? phoneCompanyId : "")
                    .append("&nombre=")
                    .append(URLEncoder.encode(firstName, "UTF-8"))
                    .append("&apellido=")
                    .append(URLEncoder.encode(lastName, "UTF-8"))
                    .append("&fechaNacimiento=")
                    .append(parsedBirthday)
                    .append("&sexo=")
                    .append(sex)
                    .append("&dni=")
                    .append(dni != null ? dni : "");
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
                        try {
                            if (response.getBoolean("usuarioExiste")) {
                                signUpCallbacks.onEmailAlreadyExists();
                                return;
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

    private static User decodeUser(JSONObject data, boolean loginFacebook) throws JSONException, UserNotFoundException {
        String userDataType;

        if (loginFacebook) {
            userDataType = "usuarioSocial";
        } else {
            userDataType = "usuario";
        }

        if (!data.getBoolean(userDataType))
            throw new UserNotFoundException("email or pass was wrong");

        JSONObject userJson = data.getJSONObject("jsonResult");
        User user = new User();
        user.setUserId(userJson.getInt("Id"));
        user.setRadiotulSocialLoginId(userJson.getInt("IdLoginSocial"));
        user.setSocialId(userJson.getString("IdSocial"));
        user.setProfileId(Integer.parseInt(userJson.getString("IdPerfil")));
        user.setFirstName(userJson.getString("Nombre"));
        user.setLastName(userJson.getString("Apellido"));
        user.setEmail(userJson.getString("Email"));
        user.setSex(userJson.getString("Sexo"));
        user.setDni(userJson.getString("Dni"));
        user.setParsedBirthDay(userJson.getString("FechaNacimiento"));
        user.setPhoneCompany(userJson.getString("Operador"));
        user.setPhone(userJson.getString("NumeroTelefono"));
        user.setProfilePictureUrl(userJson.getString("UrlFotoPerfil"));

        return user;
    }
}
