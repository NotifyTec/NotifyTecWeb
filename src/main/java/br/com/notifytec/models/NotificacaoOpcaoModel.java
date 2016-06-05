package br.com.notifytec.models;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity(name = "NOTIFICACAOOPCAO")
public class NotificacaoOpcaoModel {

    @Id
    @Column(name = "ID")
    private UUID id;
    @Column(name = "NOME")
    private String nome;
    @Column(name = "NOTIFICAOID")
    private UUID notificacaoID;
    @Transient
    private int totalRespondidos;

    public int getTotalRespondidos() {
        return totalRespondidos;
    }

    public void setTotalRespondidos(int totalRespondidos) {
        this.totalRespondidos = totalRespondidos;
    }

    public UUID getNotificacaoID() {
        return notificacaoID;
    }

    public void setNotificacaoID(UUID notificacaoID) {
        this.notificacaoID = notificacaoID;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

}
