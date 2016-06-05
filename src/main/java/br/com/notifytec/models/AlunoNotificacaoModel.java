package br.com.notifytec.models;

import java.util.Date;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "ALUNONOTIFICACAO")
public class AlunoNotificacaoModel {

    @Id    
    @Column(name = "ID")
    private UUID id;
    @Column(name = "ALUNOID")
    private UUID alunoID;
    @Column(name = "NOTIFICACAOID")
    private UUID notificacaoID;
    @Column(name = "NOTIFICACAOOPCAOID")
    private UUID notificacaoOpcao;
    @Column(name = "VIZUALIZOUEM")
    private Date visualizouEm;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getAlunoID() {
        return alunoID;
    }

    public void setAlunoID(UUID alunoID) {
        this.alunoID = alunoID;
    }

    public UUID getNotificacaoID() {
        return notificacaoID;
    }

    public void setNotificacaoID(UUID notificacaoID) {
        this.notificacaoID = notificacaoID;
    }

    public UUID getNotificacaoOpcao() {
        return notificacaoOpcao;
    }

    public void setNotificacaoOpcao(UUID notificacaoOpcao) {
        this.notificacaoOpcao = notificacaoOpcao;
    }

    public Date getVisualizouEm() {
        return visualizouEm;
    }

    public void setVisualizouEm(Date visualizouEm) {
        this.visualizouEm = visualizouEm;
    }
    
    
}
