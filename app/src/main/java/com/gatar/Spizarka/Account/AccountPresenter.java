package com.gatar.Spizarka.Account;

import org.springframework.http.HttpStatus;

/**
 * Presenter layer for Login activity.
 */

public class AccountPresenter implements AccountMVP.RequiredPresenterOperations, AccountMVP.PresenterOperations{

    private AccountMVP.RequiredViewOperations mView;
    private AccountMVP.ModelOperations mModel;

    private AccountDTO account;

    public AccountPresenter(AccountMVP.RequiredViewOperations mView) {
        this.mView = mView;
        mModel = new AccountModel(this);
    }

    @Override
    public void tryGetAccountFromPreferences() {
        mModel.getAccountFromPreferences();
    }

    @Override
    public void setAccountDataOnView(AccountDTO accountFromPreferences) {
        if(!accountFromPreferences.getUsername().equals("")){
            mView.fillTextFields(accountFromPreferences);
            account = accountFromPreferences;
        }
            else mView.showToast("Brak konta w telefonie.");
    }

    @Override
    public void postAccountData(String username, String password, String email) {
        createAccountReference(username,password,email);
        mModel.addNewAccount(account);
    }

    @Override
    public void rememberUserData(String username) {

    }

    @Override
    public void changePassword() {

    }

    @Override
    public void deleteAccount() {

    }

    @Override
    public void reportFromModel(String report) {
        mView.showToast(report);
    }

    @Override
    public void handleNewAccountResponse(HttpStatus status) {
        switch (status){
            case CREATED:
                mModel.addAccountToPreferences(account);
                mModel.putDatabaseVersion();
                mView.showProgress(false);
                mView.showToast("Utworzono nowe konto!");
                break;

            case NOT_ACCEPTABLE:
                mModel.loginToAccount(account);
                break;

            default:
                mView.showProgress(false);
                mView.showToast("Błąd połączenia!");
                break;
        }

    }

    @Override
    public void handleLoginToAccountResponse(HttpStatus status, Integer databaseVersion) {
        switch (status){
            case OK:
                mModel.addAccountToPreferences(account);
                mView.showProgress(false);
                mView.showToast("Prawidłowe dane logowania. Wersja bazy: " + databaseVersion);
                break;

            default:
                mView.showProgress(false);
                mView.showToast("Nieprawidłowe dane logowania");
                break;
        }
    }

    private void createAccountReference(String username, String password, String email){
        account = new AccountDTO();
        account.setUsername(username);
        account.setPassword(password);
        account.setEmail(email);
    }
}
