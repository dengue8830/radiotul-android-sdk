package com.amla.radiotulsdktestcase.volley;

import com.amla.radiotulsdktestcase.RadiotulSdk;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.HurlStack;

import org.apache.http.HttpResponse;

import java.io.IOException;
import java.util.Map;

/**
 * Created by dengue8830 on 1/31/17.
 */

public class RadiotulHurlStack extends HurlStack{
    @Override
    public HttpResponse performRequest(Request<?> request, Map<String, String> additionalHeaders)
            throws IOException, AuthFailureError {

        additionalHeaders.put("Authorization", "bearer "+ RadiotulSdk.getInstance().getToken());
        additionalHeaders.put("cliente_id", RadiotulSdk.getInstance().getClientId());
        additionalHeaders.put("paquete", RadiotulSdk.getInstance().getPackageName());

        return super.performRequest(request, additionalHeaders);
    }
}
