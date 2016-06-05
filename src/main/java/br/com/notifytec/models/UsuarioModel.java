package br.com.notifytec.models;

import java.util.Date;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;
import org.hibernate.annotations.GenericGenerator;

@Entity(name = "USUARIO")
public class UsuarioModel {

    @Id
    @Column(name = "ID")
    private UUID id;
    @Column(name = "LOGIN")
    private String login;
    @Column(name = "SENHA")
    private String senha;
    @Column(name = "PODEENVIAR")
    private Boolean podeEnviar;
    @Column(name = "ALTEROUSENHA")
    private Boolean alterouSenha;
    @Column(name = "EMAIL")
    private String email;
    @Column(name = "TOKENRECUPERARSENHA")
    private String tokenRecuperarSenha;
    @Column(name = "DATAVALIDADETOKEN")
    private Date dataValidadeToken;
    @Column(name = "GCMTOKEN")
    private String gcmToken;
    @Transient
    private String token;

    public String getGcmToken() {
        return gcmToken;
    }

    public void setGcmToken(String gcmToken) {
        this.gcmToken = gcmToken;
    }    
    
    public String getToken() {
        return this.token;
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

    public Boolean isPodeEnviar() {
        return podeEnviar;
    }

    public void setPodeEnviar(Boolean podeEnviar) {
        this.podeEnviar = podeEnviar;
    }

    public Boolean isAlterouSenha() {
        return alterouSenha;
    }

    public void setAlterouSenha(Boolean alterouSenha) {
        this.alterouSenha = alterouSenha;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String isTokenRecuperarSenha() {
        return tokenRecuperarSenha;
    }

    public void setTokenRecuperarSenha(String tokenRecuperarSenha) {
        this.tokenRecuperarSenha = tokenRecuperarSenha;
    }

    public Date getDataValidadeToken() {
        return dataValidadeToken;
    }

    public void setDataValidadeToken(Date dataValidadeToken) {
        this.dataValidadeToken = dataValidadeToken;
    }
}
