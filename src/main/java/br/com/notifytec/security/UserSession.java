package br.com.notifytec.security;

import br.com.notifytec.models.UsuarioModel;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;

@SessionScoped
public class UserSession implements Serializable {

    private UsuarioModel user;

    public void setUser(UsuarioModel user) {
        this.user = user;
    }

    public UsuarioModel getUser() {
        return this.user;
    }

    public boolean isLogged() {
        return this.user != null;
    }
}
