package br.com.notifytec.models;

import java.io.Serializable;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class PessoaModel implements Serializable {

    @Id
    @Column(name = "ID")
    private UUID id;
    @Column(name = "NOME")
    private String nome;
    @Column(name = "SOBRENOME")
    private String sobrenome;
    @Column(name = "CPF")
    private String cpf;
    @Column(name = "ATIVO")
    private boolean ativo;
    @Column(name = "USUARIOID")
    private UUID usuarioId;

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

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public UUID getUsuario() {
        return usuarioId;
    }

    public void setUsuario(UUID usuario) {
        this.usuarioId = usuario;
    }

}
