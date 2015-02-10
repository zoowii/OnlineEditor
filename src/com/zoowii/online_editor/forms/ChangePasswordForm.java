package com.zoowii.online_editor.forms;

import com.zoowii.formutils.annotations.Length;

import javax.validation.constraints.NotNull;

/**
 * Created by zoowii on 15/2/11.
 */
public class ChangePasswordForm {
    @NotNull
    @Length(min = 4, max = 30, message = "密码长度不少于4位,不长于30位")
    private String oldPassword;
    @NotNull
    @Length(min = 4, max = 30, message = "密码长度不少于4位,不长于30位")
    private String newPassword;

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
