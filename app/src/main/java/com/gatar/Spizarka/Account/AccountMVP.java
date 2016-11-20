package com.gatar.Spizarka.Account;

import org.springframework.http.HttpStatus;

/**
 * MVP pattern interface for Account.
 */
public interface AccountMVP {
    //---------Operations in Views used by Presenter-------------------
    interface RequiredViewOperations{
        /**
         * Show Toast message.
         */
        void showToast(String message);

        /**
         * Show progress spinner.
         * @param show true: show spinner, false: hide spinner
         */
        void showProgress(boolean show);

        /**
         * Fill the Text Fields by data values if case when those data exists.
         * @param account AccountDTO with data from preferences
         */
        void fillTextFields(AccountDTO account);

        /**
         * Perform login procedure with check correctness of text fields: login, password, email.
         */
        void attemptLogin();

        /**
         * Perform send an email with account data and new, random password on email connected with account.
         */
        void rememberAccountDataByEmail();

        /**
         * Change password for account.
         */
        void changePassword();
    }

    //---------Operations in Presenter used by Views-------------------
    interface PresenterOperations{

        /**
         * Tries to get Account data from preferences by Model.
         */
        void tryGetAccountFromPreferences();

        /**
         * Create {@link AccountDTO} object and give it to model, to send it to WebAPI.
         * @param username login value
         * @param password password value
         * @param email email address
         */
        void postAccountData(String username, String password, String email);

        /**
         * Give to model information to remember user credentials. WebAPI SpizarkaServlet creates random 6-digit password
         * and send it to email which is in database.
         * @param username login value
         */
        void rememberUserData(String username);

        /**
         * Change password.
         */
        void changePassword();

        /**
         * Delete Account in database. Use credentials which are saved in Preferences.
         */
        void deleteAccount();
    }

    //---------Operations in Presenter used by Model-------------------
    interface RequiredPresenterOperations{

        void setAccountDataOnView(AccountDTO accountDTO);

        /**
         * Passing massage to make Toast in View.
         * @param report message body
         */
        void reportFromModel(String report);

        /**
         * Handling WebAPI answer about creating new account.
         * @param status CREATED - account added successful, NOT_ACCEPTABLE - account alerady exist, other - something goes wrong
         */
        void handleNewAccountResponse(HttpStatus status);

        /**
         * Handling WebAPI answer about trying to log with account credential to getDataVersion service.
         * @param status OK - credentials are correct, other - credentials are not correct
         */
        void handleLoginToAccountResponse(HttpStatus status, Integer databaseVersion);
    }

    //---------Operations in Model used by Presenter-------------------
    interface ModelOperations{

        /**
         * Trying to add new Account to WebAPI database. On base of response there are making next steps in Presenter.
         * @param accountDTO account data object
         */
        void addNewAccount(AccountDTO accountDTO);

        /**
         * Add account credentials and email to Preferences for future use.
         * @param accountDTO account data
         */
        void addAccountToPreferences(AccountDTO accountDTO);

        /**
         * Get form Preferences database version and put it to WebAPI database.
         * If there is no version in Preferences, add both i WebAPI and preferences value 1.
         */
        void putDatabaseVersion();


        /**
         * Tries to login to service getDatabaseVersion with account credentials. Check account avability by trying
         * to get database version from service using credentials input by user.
         * @param accountDTO account data
         */
        void loginToAccount(AccountDTO accountDTO);

        /**
         * Get account data from preferences and fill TextFields in case where there are not empty.
         */
        void getAccountFromPreferences();

        /**
         * Give to model information to remember user credentials. WebAPI SpizarkaServlet creates random 6-digit password
         * and send it to email which is in database.
         */
        void rememberUserData();

        /**
         * Change password.
         */
        void changePassword();

    }
}
