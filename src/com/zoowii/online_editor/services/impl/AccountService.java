package com.zoowii.online_editor.services.impl;

import com.zoowii.online_editor.enums.UserRoles;
import com.zoowii.online_editor.models.AccountEntity;
import com.zoowii.online_editor.services.IAccountService;
import com.zoowii.online_editor.utils.BCrypt;
import com.zoowii.playmore.annotation.Service;
import zuice.utils.StringUtils;

/**
 * Created by zoowii on 15/2/10.
 */
@Service
public class AccountService implements IAccountService {
    @Override
    public AccountEntity findByUserNameOrEmail(String usernameOrEmail) {
        return AccountEntity.findByUserNameOrEmail(usernameOrEmail);
    }

    @Override
    public void initAccounts() {
        if (AccountEntity.find.where().count() > 0) {
            return;
        }
        AccountEntity user = new AccountEntity();
        user.setUserName("root");
        user.setAliasName("root");
        user.setEmail("root@localhost.local");
        user.setRole(UserRoles.ADMIN);
        user.setPassword(BCrypt.hashpw("root", BCrypt.gensalt()));
        user.save();
    }

    @Override
    public boolean checkPassword(AccountEntity accountEntity, String password) {
        return accountEntity != null && accountEntity.checkPassword(password);
    }
}
