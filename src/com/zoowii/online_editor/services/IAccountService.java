package com.zoowii.online_editor.services;

import com.zoowii.online_editor.models.AccountEntity;

/**
 * Created by zoowii on 15/2/10.
 */
public interface IAccountService {
    AccountEntity findByUserNameOrEmail(String usernameOrEmail);

    void initAccounts();

    boolean checkPassword(AccountEntity accountEntity, String password);
}
