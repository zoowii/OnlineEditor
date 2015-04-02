package com.zoowii.online_editor.forms;

import com.zoowii.formutils.annotations.Email;
import com.zoowii.formutils.annotations.Length;

import javax.validation.constraints.NotNull;

/**
 * Created by zoowii on 15/4/2.
 */
public class RegisterForm {
    @NotNull
    @Length(min = 4, max = 30)
    private String username;
    @NotNull
    @Email
    private String email;
    @NotNull
    @Length(min = 4, max = 30)
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
