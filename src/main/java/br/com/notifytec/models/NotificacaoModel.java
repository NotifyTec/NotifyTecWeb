package br.com.notifytec.models;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import org.hibernate.annotations.GenericGenerator;

@Entity(name = "NOTIFICACAO")
public class NotificacaoModel {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "ID")
    private UUID id;
    @Column(name = "TITULO")
    private String titulo;
    @Column(name = "CONTEUDO")
    private String conteudo;
    @Column(name = "USUARIOID")
    private UUID usuarioID;
    @Column(name = "DATAHORAENVIO")
    private Date dataHoraEnvio;
    @Column(name = "EXPIRAEM")
    private Date expiraEm;
    //private UsuarioModel usuario;
    //private List<NotificacaoOpcaoModel> opcoes;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public UUID getUsuarioID() {
        return usuarioID;
    }

    public void setUsuarioID(UUID usuarioID) {
        this.usuarioID = usuarioID;
    }

    public Date getDataHoraEnvio() {
        return dataHoraEnvio;
    }

    public void setDataHoraEnvio(Date dataHoraEnvio) {
        this.dataHoraEnvio = dataHoraEnvio;
    }

    public Date getExpiraEm() {
        return expiraEm;
    }

    public void setExpiraEm(Date expiraEm) {
        this.expiraEm = expiraEm;
    }

    /*
    public UsuarioModel getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioModel usuario) {
        this.usuario = usuario;
    }
    */
/*
    public List<NotificacaoOpcaoModel> getOpcoes() {
        return opcoes;
    }

    public void setOpcoes(List<NotificacaoOpcaoModel> opcoes) {
        this.opcoes = opcoes;
    }
*/
}
