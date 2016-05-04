package br.com.notifytec.security;

import br.com.notifytec.models.UserModel;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;

@SessionScoped
public class UserSession implements Serializable {

    private UserModel user;

    public void setUser(UserModel user) {
        this.user = user;
    }

    public UserModel getUser() {
        return this.user;
    }

    public boolean isLogged() {
        return this.user != null;
    }
}
