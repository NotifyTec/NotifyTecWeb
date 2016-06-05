package br.com.notifytec.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;

@Entity(name = "ALUNO")
public class AlunoModel extends PessoaModel {
        
    @Column(name = "RA")
    private String ra;
        
    public String getRa() {
        return ra;
    }

    public void setRa(String ra) {
        this.ra = ra;
    }
}
