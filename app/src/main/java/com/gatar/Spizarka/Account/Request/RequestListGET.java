package com.gatar.Spizarka.Account.Request;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.Map;

/**
 * Class providing GET type request for List of T type objects, parsed from JSON. Used for:
 * <ul>
 *     <li> Get items list </li>
 *     <li> Get barcodes list </li>
 * </ul>
 */
public class RequestListGET<T> extends Request<T> {

    private final Gson gson = new Gson();
    private final Type type;
    private final Map<String, String> headers;
    private final Response.Listener<T> listener;

    public RequestListGET(String url, Map<String, String> headers, Type type, Response.Listener<T> listener, Response.ErrorListener errorListener) {
        super(Method.GET, url, errorListener);
        this.type = type;
        this.headers = headers;
        this.listener = listener;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return headers != null ? headers : super.getHeaders();
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse networkResponse) {

        try {
            String json = new String(networkResponse.data, HttpHeaderParser.parseCharset(networkResponse.headers));
            T parseObject = gson.fromJson(json, type);
            return Response.success(parseObject, HttpHeaderParser.parseCacheHeaders(networkResponse));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void deliverResponse(T t) {
        listener.onResponse(t);
    }
}