package com.gatar.Spizarka.Account;

import android.content.SharedPreferences;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.gatar.Spizarka.Account.Request.RequestGET;
import com.gatar.Spizarka.Application.MyApp;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.support.Base64;

import java.util.Arrays;
import java.util.Map;

import javax.inject.Inject;

/**
 * Model layer for Account manager.
 */
public class AccountModel implements AccountMVP.ModelOperations{

    @Inject
    SharedPreferences preferences;

    @Inject
    SharedPreferences.Editor preferencesEditor;

    @Inject
    RequestQueue mRequestQueue;

    AccountMVP.RequiredPresenterOperations mPresenter;

    public final String DATABASE_VERSION_PREFERENCES = "com.gatar.Spizarka.DB_VERSION";

    private final String DOMAIN_PATH = "http://spizarkaservlet.eu-west-1.elasticbeanstalk.com/";
    private final String ADD_ACCOUNT_PATH = "addNewAccount";
    private final String GET_DB_VERSION_PATH = "/getDataVersion";
    private final String PUT_DB_VERSION_PATH = "/putDataVersion";
    private final String ACCOUNT_REMIND_PATH = "/rememberAccountData";
    private final String CHANGE_PASSWORD_PATH = "/changePassword";
    private final String DELETE_ACCOUNT_PATH = "/delete";

    private HttpHeaders httpHeaders;
    private Gson gson;

    public AccountModel(AccountPresenter presenter) {
        mPresenter = presenter;
        gson = new Gson();

        MyApp.getAppComponent().inject(this);
    }

    public void addAccountToPreferences(AccountDTO account){
        preferencesEditor.putString("username",account.getUsername());
        preferencesEditor.putString("email",account.getEmail());
        preferencesEditor.putString("password",account.getPassword());
        preferencesEditor.commit();
    }

    public void getAccountFromPreferences(){
        AccountDTO thisAccount = new AccountDTO();
        thisAccount.setUsername(preferences.getString("username",""));
        thisAccount.setPassword(preferences.getString("password",""));
        thisAccount.setEmail(preferences.getString("email",""));
        mPresenter.setAccountDataOnView(thisAccount);
    }

    @Override
    public void addNewAccount(AccountDTO account) {
        final String URI = DOMAIN_PATH + ADD_ACCOUNT_PATH;
        final AccountDTO accountDTO = account;
        final JSONObject accountJSON;

        Response.Listener<JSONObject> responseListener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                mPresenter.handleNewAccountResponse(HttpStatus.CREATED);
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mPresenter.handleNewAccountResponse(HttpStatus.valueOf(error.networkResponse.statusCode));
            }
        };

        try{
            accountJSON  = new JSONObject(gson.toJson(accountDTO, AccountDTO.class));

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URI, accountJSON,responseListener,errorListener){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    return buildAuthHeaders(accountDTO).toSingleValueMap();
                }

                @Override
                protected Response<JSONObject> parseNetworkResponse(NetworkResponse response){
                    if(response.data.length == 0) return Response.success(accountJSON, HttpHeaderParser.parseCacheHeaders(response));
                    else super.parseNetworkResponse(response);
                    return null;
                }
            };
            mRequestQueue.add(jsonObjectRequest);
        }catch(JSONException e){
            Log.d("NewAccountJSON","Error: " + e.getMessage());
        }catch(Exception e){
            Log.d("NewAccountException","Error: " + e.getMessage());
        }
    }

    @Override
    public void loginToAccount(AccountDTO account) {
        final String URI = DOMAIN_PATH + account.getUsername() + GET_DB_VERSION_PATH;

        Response.Listener<Integer> responseListener = new Response.Listener<Integer>() {
            @Override
            public void onResponse(Integer response) {
                mPresenter.handleLoginToAccountResponse(HttpStatus.OK,response);
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mPresenter.handleLoginToAccountResponse(HttpStatus.valueOf(error.networkResponse.statusCode), null);
                Log.d("LoginToAccount: ","Error: " + error.toString());

            }
        };

        RequestGET<Integer> loginToAccount = new RequestGET<>(
                URI,
                Integer.TYPE,
                buildAuthHeaders(account).toSingleValueMap(),
                responseListener,
                errorListener
        );
        mRequestQueue.add(loginToAccount);
    }


    @Override
    public void putDatabaseVersion() {
        final AccountDTO accountDTO = getCredentialsFromPreferences();
        final String URI = DOMAIN_PATH + accountDTO.getUsername() + PUT_DB_VERSION_PATH;
        final Integer dbVersion = getDatabaseVersionFromPreferences();
        final JSONArray dbVersionJSON;

        Response.Listener<JSONArray> responseListener = new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                mPresenter.reportFromModel("Dodano wersję do zdalnej bazy: " + dbVersion);

            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mPresenter.reportFromModel("Nie dodano wersji do zdalnej bazy: " + dbVersion);
                Log.d("PutDatabaseVersion ","Error: " + error.toString());
            }
        };

        try{
            dbVersionJSON  = new JSONArray().put(dbVersion);

            JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(Request.Method.POST, URI,dbVersionJSON,responseListener,errorListener){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    return buildAuthHeaders(accountDTO).toSingleValueMap();
                }

            };
            mRequestQueue.add(jsonObjectRequest);
        }catch(Exception e){
            Log.d("PutDatabaseVExcep ","Error: " + e.getMessage());
        }
    }

    @Override
    public void rememberUserData() {
        final AccountDTO accountDTO = getCredentialsFromPreferences();
        final String URI = DOMAIN_PATH + accountDTO.getUsername() + ACCOUNT_REMIND_PATH;

        Response.Listener<JSONObject> responseListener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                mPresenter.reportFromModel("Dane i hasło zostały wysłane na  email");

            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mPresenter.reportFromModel("Błąd! Brak możliwości wysłania nowego hasła");
            }
        };

        JsonObjectRequest requestNewPassword = new JsonObjectRequest(Request.Method.GET, URI,null,responseListener,errorListener);
        mRequestQueue.add(requestNewPassword);
    }

    @Override
    public void changePassword() {

    }

    private HttpHeaders buildAuthHeaders(AccountDTO credentials){
        String plainCredentials=credentials.getUsername()+":"+credentials.getPassword();

        //Below may arise error
        String base64Credentials = Base64.encodeBytes(plainCredentials.getBytes());

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + base64Credentials);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        return headers;
    }

    private AccountDTO getCredentialsFromPreferences(){
        AccountDTO credentials = new AccountDTO();
        credentials.setUsername(preferences.getString("username",""));
        credentials.setPassword(preferences.getString("password",""));
        return credentials;
    }

    private Integer getDatabaseVersionFromPreferences(){
        return preferences.getInt(DATABASE_VERSION_PREFERENCES,1);
    }

}
