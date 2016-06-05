package br.com.notifytec.models;

import java.util.Date;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

@Entity(name = "ALUNOPERIODO")
public class AlunoPeriodoModel {
    @Id
    @Column(name = "ID")
    private UUID ID;
    @Column(name = "ALUNOID")
    private UUID alunoID;
    @Column(name = "PERIODOID")
    private UUID periodoID;    
    @Column(name = "SEMESTREID")
    private UUID semestreID; 
    @Transient
    private AlunoModel aluno;

    public AlunoModel getAluno() {
        return aluno;
    }

    public void setAluno(AlunoModel aluno) {
        this.aluno = aluno;
    }
    
    public UUID getID() {
        return ID;
    }

    public void setID(UUID ID) {
        this.ID = ID;
    }

    public UUID getAlunoID() {
        return alunoID;
    }

    public void setAlunoID(UUID alunoID) {
        this.alunoID = alunoID;
    }

    public UUID getPeriodoID() {
        return periodoID;
    }

    public void setPeriodoID(UUID periodoID) {
        this.periodoID = periodoID;
    }

    public UUID getSemestreID() {
        return semestreID;
    }

    public void setSemestreID(UUID semestreID) {
        this.semestreID = semestreID;
    }
    
    
}
