package org.nocket.component.panel.login;

import javax.validation.constraints.NotNull;

import org.apache.wicket.util.io.IClusterable;

public class UserLoginData implements IClusterable {

    private static final long serialVersionUID = 1L;

    private String username;
    private String password;

    @NotNull
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @NotNull
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
