package br.com.notifytec.models;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import org.hibernate.annotations.GenericGenerator;

@Entity(name = "DEPARTAMENTO")
public class DepartamentoModel {
    @Id
    @Column(name = "ID")
    private UUID id;
    @Column(name = "NOME")
    private String nome;

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
