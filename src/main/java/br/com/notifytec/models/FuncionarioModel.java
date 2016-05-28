package br.com.notifytec.models;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import org.hibernate.annotations.GenericGenerator;

@Entity(name = "FUNCIONARIO")
public class FuncionarioModel extends PessoaModel {

    @Id
    @Column(name = "ID")
    private UUID id;

    //@Column(name = "DEPARTAMENTOID")
    private UUID departamentoId;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getDepartamento() {
        return departamentoId;
    }

    public void setDepartamento(UUID departamentoId) {
        this.departamentoId = departamentoId;
    }
}
