package br.com.notifytec.models;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;

@Entity(name = "ALUNO")
public class AlunoModel extends PessoaModel {
        
    @Column(name = "RA")
    private String ra;
        
    @Transient
    private String ativoTraduzido;
    
    @Transient
    private List<AlunoPeriodoModel> periodoSemestre;

    public List<AlunoPeriodoModel> getPeriodoSemestre() {
        return periodoSemestre;
    }

    public void setPeriodoSemestre(List<AlunoPeriodoModel> periodoSemestre) {
        this.periodoSemestre = periodoSemestre;
    }

    public String getAtivoTraduzido() {
        return ativoTraduzido;
    }

    public void setAtivoTraduzido(String AtivoTraduzido) {
        this.ativoTraduzido = AtivoTraduzido;
    }
            
    public String getRa() {
        return ra;
    }

    public void setRa(String ra) {
        this.ra = ra;
    }
}
