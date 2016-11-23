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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.gatar.Spizarka.Account.Request.RequestGET;
import com.gatar.Spizarka.Account.Request.RequestStringPOST;
import com.gatar.Spizarka.Application.MyApp;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.support.Base64;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Collections;
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

    private final String DATABASE_VERSION_PREFERENCES = "com.gatar.Spizarka.DB_VERSION";
    private final String USERNAME_PREFERENCES = "com.gatar.Spizarka.USERNAME";
    private final String PASSWORD_PREFERENCES = "com.gatar.Spizarka.P_A_SS_WOR_D";
    private final String EMAIL_PREFERENCES = "com.gatar.Spizarka.EMAIL";

    private final String DOMAIN_PATH = "http://spizarkaservlet.eu-west-1.elasticbeanstalk.com/";
    private final String ADD_ACCOUNT_PATH = "addNewAccount";
    private final String GET_DB_VERSION_PATH = "/getDataVersion";
    private final String PUT_DB_VERSION_PATH = "/putDataVersion";
    private final String ACCOUNT_REMIND_PATH = "/rememberAccountData";
    private final String CHANGE_PASSWORD_PATH = "/changePassword";
    private final String DELETE_ACCOUNT_PATH = "/delete";

    private Gson gson;

    public AccountModel(AccountPresenter presenter) {
        mPresenter = presenter;
        gson = new Gson();

        MyApp.getAppComponent().inject(this);
    }

    public void addAccountToPreferences(AccountDTO account){
        preferencesEditor.putString(USERNAME_PREFERENCES,account.getUsername());
        preferencesEditor.putString(PASSWORD_PREFERENCES,account.getPassword());
        preferencesEditor.putString(EMAIL_PREFERENCES,account.getEmail() == null ? "" : account.getEmail());
        preferencesEditor.commit();
    }

    public void setAccountFromPreferencesToView(){
        AccountDTO thisAccount = new AccountDTO();
        thisAccount.setUsername(preferences.getString(USERNAME_PREFERENCES,""));
        thisAccount.setPassword("");
        thisAccount.setEmail(preferences.getString(EMAIL_PREFERENCES,""));
        mPresenter.setAccountOnView(thisAccount);
    }

    @Override
    public void addNewAccountToWebAPI(AccountDTO account) {
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
    public void loginToWebAPI(AccountDTO account) {
        final String URI = DOMAIN_PATH + account.getUsername() + GET_DB_VERSION_PATH;

        Response.Listener<Long> responseListener = new Response.Listener<Long>() {
            @Override
            public void onResponse(Long response) {
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

        RequestGET<Long> loginToAccount = new RequestGET<>(
                URI,
                Long.TYPE,
                buildAuthHeaders(account).toSingleValueMap(),
                responseListener,
                errorListener
        );
        mRequestQueue.add(loginToAccount);
    }


    @Override
    public void putDatabaseVersionToWebAPI() {
        final AccountDTO accountDTO = getCredentialsFromPreferences();
        final Long dbVersion = getDatabaseVersionFromPreferences();
        final String URI = DOMAIN_PATH + accountDTO.getUsername() + PUT_DB_VERSION_PATH;

        final String postData = String.format("\"%d\"",dbVersion);
        final Map<String,String> headers = buildAuthHeaders(accountDTO).toSingleValueMap();

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
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

        RequestStringPOST sendDatabaseVersion = new RequestStringPOST(URI,headers,postData,responseListener,errorListener);
        mRequestQueue.add(sendDatabaseVersion);
    }

    @Override
    public void resetAccountPassword(String username) {
        final String URI = DOMAIN_PATH + username + ACCOUNT_REMIND_PATH;

        Response.Listener<Void> responseListener = new Response.Listener<Void>() {
            @Override
            public void onResponse(Void response) {
                mPresenter.reportFromModel("Dane i hasło zostały wysłane na  email");
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mPresenter.reportFromModel("Błąd! Brak możliwości wysłania nowego hasła");
            }
        };

        try {
            RequestGET<Void> requestNewPassword = new RequestGET<>(URI, Void.TYPE, Collections.EMPTY_MAP, responseListener, errorListener);
            mRequestQueue.add(requestNewPassword);
        }catch (Exception e){
            Log.d("ResetPassword ", e.toString());
        }
    }

    @Override
    public void changePassword(AccountDTO newCredentials, String oldPassword) {
        final String URI = DOMAIN_PATH + newCredentials.getUsername() + CHANGE_PASSWORD_PATH;

        final String postData = newCredentials.getPassword();
        final Map<String,String> headers = buildAuthHeaders(newCredentials.getUsername(),oldPassword).toSingleValueMap();

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                mPresenter.handleChangePasswordResponse(HttpStatus.OK);
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mPresenter.handleChangePasswordResponse(HttpStatus.valueOf(error.networkResponse.statusCode));
                Log.d("ChangePassword ","Error: " + error.toString());
            }
        };

        RequestStringPOST changePassword = new RequestStringPOST(URI,headers,postData,responseListener,errorListener);
        mRequestQueue.add(changePassword);
    }

    @Override
    public void deleteAccount(String username, String password) {
        final String URI = DOMAIN_PATH + username + DELETE_ACCOUNT_PATH;
        final Map<String,String> headers = buildAuthHeaders(username,password).toSingleValueMap();

        Response.Listener<Void> responseListener = new Response.Listener<Void>() {
            @Override
            public void onResponse(Void response) {
                mPresenter.reportFromModel("Konto zostało usunięte!");

                //Reset Account in Preferences
                AccountDTO emptyAccount = new AccountDTO();
                emptyAccount.setUsername("");
                emptyAccount.setPassword("");
                emptyAccount.setEmail("");
                addAccountToPreferences(emptyAccount);
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mPresenter.reportFromModel("Konto nie zostało usunięte, złe hasło.");
            }
        };

        try {
            RequestGET<Void> deleteAccount = new RequestGET<>(URI, Void.TYPE, headers, responseListener, errorListener);
            mRequestQueue.add(deleteAccount);
        }catch (Exception e){
            Log.d("DeleteAccount ", e.toString());
        }
    }

    @Override
    public void putDatabaseVersionToPreferences(Long databaseVersion) {
        preferencesEditor.putLong(DATABASE_VERSION_PREFERENCES,databaseVersion);
        preferencesEditor.commit();
    }

    private HttpHeaders buildAuthHeaders(AccountDTO credentials){
        String plainCredentials=credentials.getUsername()+":"+credentials.getPassword();

        String base64Credentials = Base64.encodeBytes(plainCredentials.getBytes());

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + base64Credentials);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        return headers;
    }

    private HttpHeaders buildAuthHeaders(String username, String password){
        String plainCredentials=username+":"+password;

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
