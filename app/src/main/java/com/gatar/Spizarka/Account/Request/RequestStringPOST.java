package com.gatar.Spizarka.Account.Request;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Created by bargat on 2016-11-22.
 */

public class RequestStringPOST extends Request<String>{
    private final String postData;
    private final Gson gson = new Gson();
    private final Map<String, String> headers;
    private final Response.Listener<String> listener;

    /**
     * @param url URL of the request to make
     * @param headers Map of request headers
     * @param listener Listener for successful handle response
     * @param errorListener Listener for unsuccessful response
     */
    public RequestStringPOST(String url, Map<String, String> headers, String postData,
                      Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(Request.Method.POST, url, errorListener);
        this.headers = headers;
        this.listener = listener;
        this.postData = postData;

        headers.put("Content-Type","application/json");
    }


    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return headers != null ? headers : super.getHeaders();
    }

    @Override
    protected void deliverResponse(String response) {
        listener.onResponse(response);
    }

    @Override
    public byte[] getBody(){

        byte[] body = new byte[0];
        try {
            body = postData.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            Log.e("PutDbVersion", "Unable to gets bytes from JSON", e.fillInStackTrace());
        }
        return body;
    }


    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        String responseString = "";
        if (response != null) {
            responseString = String.valueOf(response.statusCode);
        }
        return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
    }
}
