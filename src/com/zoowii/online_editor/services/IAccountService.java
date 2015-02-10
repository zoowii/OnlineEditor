package com.zoowii.online_editor.services;

import com.zoowii.online_editor.models.AccountEntity;

/**
 * Created by zoowii on 15/2/10.
 */
public interface IAccountService {
    public AccountEntity findByUserNameOrEmail(String usernameOrEmail);

    public void initAccounts();

    public boolean checkPassword(AccountEntity accountEntity, String password);
}
