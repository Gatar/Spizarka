package com.gatar.Spizarka.Database;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.gatar.Spizarka.Account.AccountDTO;
import com.gatar.Spizarka.Account.Request.RequestListGET;
import com.gatar.Spizarka.Account.Request.RequestGET;
import com.gatar.Spizarka.Account.Request.RequestStringPOST;
import com.gatar.Spizarka.Database.Objects.BarcodeDTO;
import com.gatar.Spizarka.Database.Objects.Item;
import com.gatar.Spizarka.Database.Objects.ItemDTO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.support.Base64;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Map;

/**
 * Manager for service cloud database by REST.
 */
public class RemoteDatabaseDAOImpl implements RemoteDatabaseDAO{


    private final SharedPreferences preferences;
    private final RequestQueue mRequestQueue;
    private DatabaseSynchronizerRemoteOperations databaseSynchronizer;
    private Gson gson;

    RemoteDatabaseDAOImpl(Context context, SharedPreferences preferences) {
        gson = new Gson();
        this.preferences = preferences;
        mRequestQueue = Volley.newRequestQueue(context);
    }

    public void setDatabaseSynchronizer(DatabaseSynchronizerRemoteOperations databaseSynchronizer) {
        this.databaseSynchronizer = databaseSynchronizer;
    }

    @Override
    public void saveItem(Item item) {
        final AccountDTO credentials = getCredentialsFromPreferences();
        final String URI = DOMAIN_PATH + credentials.getUsername() + SAVE_ITEM;
        final JSONObject itemJSON;
        final ItemDTO itemDTO = item.toItemDTO();

        Response.Listener<JSONObject> responseListener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        };

        try{
            itemJSON  = new JSONObject(gson.toJson(itemDTO, ItemDTO.class));

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URI, itemJSON,responseListener,errorListener){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    return buildAuthHeaders(credentials).toSingleValueMap();
                }

            };
            mRequestQueue.add(jsonObjectRequest);
        }catch(JSONException e){
            Log.d("SaveItemJSON","Error: " + e.getMessage());
        }catch(Exception e){
            Log.d("SaveItemException","Error: " + e.getMessage());
        }
    }

    @Override
    public void saveBarcode(BarcodeDTO barcodeDTO) {
        final AccountDTO credentials = getCredentialsFromPreferences();
        final String URI = DOMAIN_PATH + credentials.getUsername() + SAVE_BARCODE;
        final JSONObject barcodeJSON;

        Response.Listener<JSONObject> responseListener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        };

        try{
            barcodeJSON  = new JSONObject(gson.toJson(barcodeDTO, BarcodeDTO.class));

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URI, barcodeJSON,responseListener,errorListener){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    return buildAuthHeaders(credentials).toSingleValueMap();
                }

            };
            mRequestQueue.add(jsonObjectRequest);
        }catch(JSONException e){
            Log.d("SaveItemJSON","Error: " + e.getMessage());
        }catch(Exception e){
            Log.d("SaveItemException","Error: " + e.getMessage());
        }
    }

    @Override
    public void putDatabaseVersion() {
        final AccountDTO credentials = getCredentialsFromPreferences();
        final Long dbVersion = getDatabaseVersionFromPreferences();
        final String URI = DOMAIN_PATH + credentials.getUsername() + PUT_DB_VERSION_PATH;

        final String postData = String.format("\"%d\"",dbVersion);
        final Map<String,String> headers = buildAuthHeaders(credentials).toSingleValueMap();

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        };

        RequestStringPOST sendDatabaseVersion = new RequestStringPOST(URI,headers,postData,responseListener,errorListener);
        mRequestQueue.add(sendDatabaseVersion);
    }

    @Override
    public void getDatabaseVersion() {
        final AccountDTO credentials = getCredentialsFromPreferences();
        final String URI = DOMAIN_PATH + credentials.getUsername() + GET_DB_VERSION_PATH;
        final Map<String,String> headers = buildAuthHeaders(credentials).toSingleValueMap();

        Response.Listener<Long> responseListener = new Response.Listener<Long>() {
            @Override
            public void onResponse(Long response) {
                databaseSynchronizer.checkDatabaseVersions(response);
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Get db version: ","Error: " + error.toString());
            }
        };

        RequestGET<Long> getDbVersion = new RequestGET<>(
                URI,
                Long.TYPE,
                headers,
                responseListener,
                errorListener
        );
        mRequestQueue.add(getDbVersion);
    }

    @Override
    public void getAllItems() {
        final AccountDTO credentials = getCredentialsFromPreferences();
        final String URI = DOMAIN_PATH + credentials.getUsername() + GET_ALL_ITEMS;
        final Map<String,String> headers = buildAuthHeaders(credentials).toSingleValueMap();
        final Type type = new TypeToken<LinkedList<ItemDTO>>() {}.getType();

        Response.Listener<LinkedList<ItemDTO>> responseListener = new Response.Listener<LinkedList<ItemDTO>>() {
            @Override
            public void onResponse(LinkedList<ItemDTO> response) {
                databaseSynchronizer.saveDownloadedItems(response);
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Get db version: ","Error: " + error.toString());
            }
        };

        RequestListGET<LinkedList<ItemDTO>> getAllItems = new RequestListGET<>(
                URI,
                headers,
                type,
                responseListener,
                errorListener
        );

        mRequestQueue.add(getAllItems);
    }

    @Override
    public void getAllBarcodes() {
        final AccountDTO credentials = getCredentialsFromPreferences();
        final String URI = DOMAIN_PATH + credentials.getUsername() + GET_ALL_BARCODES;
        final Map<String,String> headers = buildAuthHeaders(credentials).toSingleValueMap();
        final Type type = new TypeToken<LinkedList<BarcodeDTO>>() {}.getType();

        Response.Listener<LinkedList<BarcodeDTO>> responseListener = new Response.Listener<LinkedList<BarcodeDTO>>() {
            @Override
            public void onResponse(LinkedList<BarcodeDTO> response) {
                databaseSynchronizer.saveDownloadedBarcodes(response);
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Get db version: ","Error: " + error.toString());
            }
        };

        RequestListGET<LinkedList<BarcodeDTO>> getAllItems = new RequestListGET<>(
                URI,
                headers,
                type,
                responseListener,
                errorListener
        );

        mRequestQueue.add(getAllItems);
    }

    private HttpHeaders buildAuthHeaders(AccountDTO credentials){
        String plainCredentials=credentials.getUsername()+":"+credentials.getPassword();

        String base64Credentials = Base64.encodeBytes(plainCredentials.getBytes());

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + base64Credentials);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        return headers;
    }

    private AccountDTO getCredentialsFromPreferences(){
        AccountDTO credentials = new AccountDTO();
        credentials.setUsername(preferences.getString(USERNAME_PREFERENCES,""));
        credentials.setPassword(preferences.getString(PASSWORD_PREFERENCES,""));
        return credentials;
    }

    private Long getDatabaseVersionFromPreferences(){
        return preferences.getLong(DATABASE_VERSION_PREFERENCES,-1);
    }
}


