package com.zoowii.online_editor.forms;

import com.zoowii.formutils.annotations.Length;

import javax.validation.constraints.NotNull;

/**
 * Created by zoowii on 15/1/25.
 */
public class LoginForm {
    /**
     * username or password
     */
    @NotNull
    @Length(min = 4, max = 40)
    private String username;
    @NotNull
    @Length(min = 6, max = 30, message = "密码长度不少于6位,不长于30位")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
