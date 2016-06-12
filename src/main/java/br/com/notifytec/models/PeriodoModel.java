/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.notifytec.models;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

/**
 *
 * @author felip
 */
@Entity(name = "PERIODO")
public class PeriodoModel {
    @Id
    @Column(name = "ID")
    private UUID id;
    @Column(name = "NUMERO")
    private int numero;
    @Column(name = "CURSOID")
    private UUID cursoid;

    @Transient
    private String cursoNome;

    public String getCursoNome() {
        return cursoNome;
    }

    public void setCursoNome(String cursoNome) {
        this.cursoNome = cursoNome;
    }
    
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public UUID getCursoid() {
        return cursoid;
    }

    public void setCursoid(UUID cursoid) {
        this.cursoid = cursoid;
    }
}
