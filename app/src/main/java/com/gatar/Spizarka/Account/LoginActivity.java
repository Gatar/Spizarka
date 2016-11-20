package com.gatar.Spizarka.Account;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;


import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gatar.Spizarka.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * A login screen that offers login via username/password and provide account options like remember or reset account..
 */
public class LoginActivity extends AppCompatActivity implements AccountMVP.RequiredViewOperations {


    private AccountMVP.PresenterOperations mPresenter;

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private EditText mLoginView;
    private View mProgressView;
    private View mLoginFormView;

    //Focus view for errors
    private View focusView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mPresenter = new AccountPresenter(this);

        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);
        mLoginView = (EditText) findViewById(R.id.login);

        // Set account data from preferences (if exists)
        mPresenter.tryGetAccountFromPreferences();

        setButtonListeners();

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
    }


    @Override
    public void fillTextFields(AccountDTO account) {
        mLoginView.setText(account.getUsername());
        mPasswordView.setText(account.getPassword());
        mEmailView.setText(account.getEmail());
    }

    public void showToast(String message) {
        Toast.makeText(this.getBaseContext(),message,Toast.LENGTH_SHORT).show();
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    public void attemptLogin() {

        // Reset errors.
        mEmailView.setError(null);
        mLoginView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();
        String login = mLoginView.getText().toString();

        // Check validity of inputed fields
        if (    validLogin(login) &&
                validPassword(password) &&
                validEmailAdress(email)) {

            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            mPresenter.postAccountData(login, password, email);
        } else {

            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        }
    }



    @Override
    public void rememberAccountDataByEmail() {

        mLoginView.setError(null);
        String login = mLoginView.getText().toString();

        //TODO Finish this functionality
        if(TextUtils.isEmpty(login)){
            mLoginView.setError(getString(R.string.need_login));
            mLoginView.requestFocus();
        }


    }

    @Override
    public void changePassword() {

    }



    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    private void setButtonListeners(){
        Button mRememberAccountButton = (Button) findViewById(R.id.remember_account_button);
        Button mSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        Button mChangePasswordButton = (Button) findViewById(R.id.change_password_button);

        mSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mRememberAccountButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                rememberAccountDataByEmail();
            }
        });

        mChangePasswordButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                changePassword();
            }
        });
    }

    private boolean validPassword(String password){
        if(password.length() < 4){
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            return false;
        }
        return true;
    }

    private boolean validLogin(String login){
        if(login.length() < 4){
            mLoginView.setError(getString(R.string.error_invalid_login));
            focusView = mLoginView;
            return false;
        }
        return true;
    }

    private boolean validEmailAdress(String email){
        String emailPattern = "[\\S]+@{1}[\\S]+[.]{1}[\\S]+";
        Pattern pattern = Pattern.compile(emailPattern);
        Matcher matcher = pattern.matcher(email);

        if (!matcher.matches()) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            return false;
        }
        return true;
    }
}

