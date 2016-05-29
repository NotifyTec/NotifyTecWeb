package br.com.notifytec.models;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import org.hibernate.annotations.GenericGenerator;

@Entity(name = "PESSOA")
public class FuncionarioModel extends PessoaModel {

    @Column(name = "DEPARTAMENTOID")
    private UUID departamentoId;

    public UUID getDepartamento() {
        return departamentoId;
    }

    public void setDepartamento(UUID departamentoId) {
        this.departamentoId = departamentoId;
    }
}
