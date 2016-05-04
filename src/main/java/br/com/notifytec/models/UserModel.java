package br.com.notifytec.models;

import java.util.Date;
import java.util.UUID;

public class UserModel {
    private UUID id;
    private String login;
    private String senha;
    private boolean podeEnviar;
    private boolean alterouSenha;
    private String email;
    private boolean tokenRecuperarSenha;
    private Date dataValidadeToken;
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public boolean isPodeEnviar() {
        return podeEnviar;
    }

    public void setPodeEnviar(boolean podeEnviar) {
        this.podeEnviar = podeEnviar;
    }

    public boolean isAlterouSenha() {
        return alterouSenha;
    }

    public void setAlterouSenha(boolean alterouSenha) {
        this.alterouSenha = alterouSenha;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isTokenRecuperarSenha() {
        return tokenRecuperarSenha;
    }

    public void setTokenRecuperarSenha(boolean tokenRecuperarSenha) {
        this.tokenRecuperarSenha = tokenRecuperarSenha;
    }

    public Date getDataValidadeToken() {
        return dataValidadeToken;
    }

    public void setDataValidadeToken(Date dataValidadeToken) {
        this.dataValidadeToken = dataValidadeToken;
    }
}
