package com.gatar.Spizarka.Account;

import com.gatar.Spizarka.Account.Dialog.AccountDialogNewPassword;

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
         * Show dialogbox for input old password and twice time new password.
         */
        void startNewPasswordDialog();

        /**
         * Change password for account.
         */
        void changePassword();

        /**
         * Set an error in case of input wrong account data: login, password or email.
         * @param error Enum parameter of error {@link AccountFieldValidation}
         */
        void setValidationError(AccountFieldValidation error);

        /**
         * Delete Account from phone and WebAPI.
         */
        void deleteAccount();
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
         * Check existence of Account in Preferences and initialize dialogbox for input old password and twice time new password.
         */
        void changePassword();

        /**
         * Proceeding new credentials to Model.
         * @param newCredentials {@link AccountDTO} object with new password and old login
         * @param oldPassword String with old password which will be used to get access to remote database.
         */
        void changePassword(AccountDTO newCredentials, String oldPassword);

        /**
         * Delete Account in database. Use credentials which are saved in Preferences.
         * @param password confirmed password value.
         */
        void deleteAccount(String password);
    }

    //---------Operations in Presenter used by Model-------------------
    interface RequiredPresenterOperations{

        void setAccountOnView(AccountDTO accountDTO);

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
         * @param databaseVersion - actual database from WebAPI used for show a toast
         */
        void handleLoginToAccountResponse(HttpStatus status, Long databaseVersion);

        /**
         * Handling WebAPI answer about trying to change password.
         * After positive changing, new credentials are saved in Preferences.
         * @param status OK - password has been changed, NOT_ACCEPTABLE - account doesn't exist, FORBIDDEN - password input by user is incorrect
         */
        void handleChangePasswordResponse(HttpStatus status);
    }

    //---------Operations in Model used by Presenter-------------------
    interface ModelOperations{

        /**
         * Trying to add new Account to WebAPI database. On base of response there are making next steps in Presenter.
         * @param accountDTO account data object
         */
        void addNewAccountToWebAPI(AccountDTO accountDTO);

        /**
         * Add account credentials and email to Preferences for future use.
         * @param accountDTO account data
         */
        void addAccountToPreferences(AccountDTO accountDTO);

        /**
         * Get account data from preferences and fill TextFields in case where there are not empty.
         */
        void setAccountFromPreferencesToView();

        /**
         * Get form Preferences database version and put it to WebAPI database.
         * If there is no version in Preferences, add both i WebAPI and preferences value 1.
         */
        void putDatabaseVersionToWebAPI();

        /**
         * Put into preferences database version.
         * @param databaseVersion version number to put in.
         */
        void putDatabaseVersionToPreferences(Long databaseVersion);

        /**
         * Tries to login to service getDatabaseVersion with account credentials. Check account avability by trying
         * to get database version from service using credentials input by user.
         * @param accountDTO account data
         */
        void loginToWebAPI(AccountDTO accountDTO);

        /**
         * Give to model information to remember user credentials. WebAPI SpizarkaServlet creates random 6-digit password
         * and send it to email which is in database.
         */
        void resetAccountPassword(String username);

        /**
         * Change password in remote database. Need confirmation of old password.
         * @param newCredentials {@link AccountDTO} object with actual login and email from Preferences and new password from {@link AccountDialogNewPassword}
         * @param oldPassword confirmation value of old password from {@link AccountDialogNewPassword}.
         */
        void changePassword(AccountDTO newCredentials, String oldPassword);

        /**
         * Completely delete Account from WebAPI database.
         * @param username login for account to delete
         * @param password password for account to delete
         */
        void deleteAccount(String username, String password);

    }
}
