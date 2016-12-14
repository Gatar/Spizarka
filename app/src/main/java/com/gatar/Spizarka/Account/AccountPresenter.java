package com.gatar.Spizarka.Account;

import org.springframework.http.HttpStatus;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Presenter layer for Login activity.
 */

public class AccountPresenter implements AccountMVP.RequiredPresenterOperations, AccountMVP.PresenterOperations{

    private AccountMVP.RequiredViewOperations mView;
    private AccountMVP.ModelOperations mModel;

    private AccountDTO accountValidated;

    public AccountPresenter(AccountMVP.RequiredViewOperations mView) {
        this.mView = mView;
        mModel = new AccountModel(this);
    }


    @Override
    public void tryGetAccountFromPreferences() {
        mModel.setAccountFromPreferencesToView();
    }

    @Override
    public void setAccountOnView(AccountDTO accountFromPreferences) {
        if(!accountFromPreferences.getUsername().equals("")){
            mView.fillTextFields(accountFromPreferences);
            accountValidated = accountFromPreferences;
        }
            else mView.showToast("Brak konta w telefonie.");
    }

    @Override
    public void postAccountData(String username, String password, String email) {

        if(!mModel.isConnectedWithInternet()) return;

        if(validLogin(username) && validPassword(password)) {
            mView.showProgress(true);
            createAccountReference(username, password, email);
            mModel.addAccountToPreferences(accountValidated);
            mModel.loginToWebAPI(accountValidated);
        }
    }

    @Override
    public void rememberUserData(String username) {
        if(!mModel.isConnectedWithInternet()) return;
        if(validLogin(username)) mModel.resetAccountPassword(username);
    }

    @Override
    public void changePassword() {
        if(!mModel.isConnectedWithInternet()) return;
        if(!accountValidated.getUsername().equals("")) mView.startNewPasswordDialog();
            else mView.showToast("Brak konta w telefonie. Wpierw zaloguj się.");
    }

    @Override
    public void changePassword(AccountDTO newCredentials, String oldPassword) {

        if(!mModel.isConnectedWithInternet()) return;

        newCredentials.setUsername(accountValidated.getUsername());
        newCredentials.setEmail(accountValidated.getEmail());
        accountValidated = newCredentials;
        mView.showProgress(true);
        mModel.changePassword(newCredentials, oldPassword);
    }

    @Override
    public void deleteAccount(String password) {
        if(!mModel.isConnectedWithInternet()) return;
        mModel.deleteAccount(accountValidated.getUsername(),password);
    }

    @Override
    public void handleNewAccountResponse(HttpStatus status) {
        mView.showProgress(false);
        switch (status){
            case CREATED:
                mModel.addAccountToPreferences(accountValidated);
                mModel.putDatabaseVersionToPreferences(1L);
                mModel.putDatabaseVersionToWebAPI();
                mView.showToast("Utworzono nowe konto!");
                break;

            case NOT_ACCEPTABLE:
                mView.showToast("Podano nieprawidłowe hasło.");
                break;

            default:
                mView.showToast("Błąd połączenia!");
                break;
        }

    }

    @Override
    public void handleLoginToAccountResponse(HttpStatus status, Long databaseVersion) {
        mView.showProgress(false);
        switch (status){
            case OK:
                mModel.putDatabaseVersionToPreferences(databaseVersion);
                mView.showToast("Prawidłowe dane logowania. Wersja bazy: " + databaseVersion);
                break;

            default:
                if(validEmailAdress(accountValidated.getEmail())){
                    mModel.addNewAccountToWebAPI(accountValidated);
                } else mView.showProgress(false);
                break;
        }
    }

    @Override
    public void handleChangePasswordResponse(HttpStatus status) {
        mView.showProgress(false);
        switch (status){
            case OK:
                mModel.addAccountToPreferences(accountValidated);
                mView.showToast("Hasło zostało zmienione");
                break;
            case NOT_ACCEPTABLE:
                mView.showToast("Konto o podanym loginie nie istnieje!");
                break;
            case FORBIDDEN:
                mView.showToast("Podano nieprawidłowe hasło.");
                break;
            default:
                mView.showToast("Error " + status.toString());
                break;
        }
    }

    @Override
    public void reportFromModel(String report) {
        mView.showToast(report);
    }

    private boolean validPassword(String password){
        if(password.length() < 4){
            mView.setValidationError(AccountFieldValidation.WRONG_PASSWORD);
            return false;
        }
        return true;
    }

    private boolean validLogin(String login){
        if(login.length() < 4){
            mView.setValidationError(AccountFieldValidation.WRONG_LOGIN);
            return false;
        }
        return true;
    }

    private boolean validEmailAdress(String email){
        String emailPattern = "[\\S]+@{1}[\\S]+[.]{1}[\\S]+";
        Pattern pattern = Pattern.compile(emailPattern);
        Matcher matcher = pattern.matcher(email);

        if (!matcher.matches()) {
            mView.setValidationError(AccountFieldValidation.WRONG_EMAIL);
            return false;
        }
        return true;
    }

    private void createAccountReference(String username, String password, String email){
        accountValidated = new AccountDTO();
        accountValidated.setUsername(username);
        accountValidated.setPassword(password);
        accountValidated.setEmail(email);
    }
}
