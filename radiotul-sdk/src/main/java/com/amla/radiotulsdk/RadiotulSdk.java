package com.amla.radiotulsdk;

import android.content.Context;
import android.support.annotation.NonNull;

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
    private long mCompanyId, mRadioId;
    private RequestQueue mRequestQueue;
    private static RadiotulSdk mInstance;

    /**
     * Main method of the sdk. Implements singleton pattern
     * @param context
     * @param clientId
     * @param token
     */
    public void initialize(@NonNull Context context, @NonNull String clientId, @NonNull String token, long companyId, long radioId, boolean logEnabled){
        if(mInstance == null)
            mInstance = new RadiotulSdk(context, context.getApplicationContext().getPackageName(), clientId, token, companyId, radioId, logEnabled);
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
    private RadiotulSdk(Context context, String packageName, String clientId, String token, long companyId, long radioId, boolean logEnabled){
        mContext = context;
        mToken = token;
        mClientId = clientId;
        mPackageName = packageName;
        mCompanyId = companyId;
        mRadioId = radioId;
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

    public long getRadioId() {
        return mRadioId;
    }

    public long getCompanyId() {
        return mCompanyId;
    }
}
