package com.amla.radiotulsdk;

import android.content.Context;
import android.support.annotation.NonNull;

import com.amla.radiotulsdk.access.User;
import com.amla.radiotulsdk.volley.RadiotulHurlStack;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by dengue8830 on 1/31/17.
 */

public class RadiotulSdk {
    public static final String TAG = RadiotulSdk.class.getName();
    public static boolean LOG_ENABLED = true;
    private Context mContext;
    private String mToken, mPackageName, mClientId;
    private long mCompanyId;
    private RequestQueue mRequestQueue;
    private static RadiotulSdk mInstance;
    private User userSignedIn;

    /**
     * Main method of the sdk. Implements singleton pattern
     * @param context
     * @param clientId
     * @param token
     */
    public static void initialize(@NonNull Context context, @NonNull String clientId, @NonNull String token, long companyId, boolean logEnabled){
        if(mInstance == null)
            mInstance = new RadiotulSdk(context, context.getPackageName(), clientId, token, companyId, logEnabled);
    }

    /**
     * The only way of get the instance of the sdk
     * @return
     */
    public static RadiotulSdk getInstance(){
        return mInstance;
    }

    /**
     * Helper to know if the sdk is initialized
     * @return
     */
    public static boolean isInitialized(){
        return mInstance != null;
    }

    /**
     * We prevent to instanciate the class out of it
     */
    private RadiotulSdk(Context context, String packageName, String clientId, String token, long companyId, boolean logEnabled){
        mContext = context;
        mToken = token;
        mClientId = clientId;
        mPackageName = packageName;
        mCompanyId = companyId;
        LOG_ENABLED = logEnabled;
    }

    /**
     * The request queue implements singleton too
     * @return the request queue
     */
    private RequestQueue getRequestQueue() {
        if (mRequestQueue == null)
            mRequestQueue = Volley.newRequestQueue(mContext.getApplicationContext(), new RadiotulHurlStack());

        return mRequestQueue;
    }

    /**
     * Use this method to start a request
     * @param req the request
     * @param <T> type of request
     */
    public <T> void startRequest(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void enableLog(){
        LOG_ENABLED = true;
    }

    public void disableLog(){
        LOG_ENABLED = false;
    }

    public Context getContext() {
        return mContext;
    }

    public String getToken() {
        return mToken;
    }

    public String getPackageName() {
        return mPackageName;
    }

    public String getClientId() {
        return mClientId;
    }

    public long getCompanyId() {
        return mCompanyId;
    }

    public User getUserSignedIn() {
        return userSignedIn;
    }

    //TODO: no cualquiera deberia poder setear el usuario logueado, solo el metodo login
    public void setUserSignedIn(User userSignedIn) {
        this.userSignedIn = userSignedIn;
    }
}
