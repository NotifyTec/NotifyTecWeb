package br.com.notifytec.models;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;

@Entity(name = "PESSOA")
public class FuncionarioModel extends PessoaModel {

    @Column(name = "DEPARTAMENTOID")
    private UUID departamentoId;
    
    @Column(name = "APELIDO")
    private String apelido;

    public UUID getDepartamentoId() {
        return departamentoId;
    }

    public void setDepartamentoId(UUID departamentoId) {
        this.departamentoId = departamentoId;
    }

    public String getApelido() {
        return apelido;
    }

    public void setApelido(String apelido) {
        this.apelido = apelido;
    }
}
