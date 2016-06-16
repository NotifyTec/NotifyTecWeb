/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.notifytec.models;

/**
 *
 * @author felip
 */
public class PeriodoSemestre {
    private String idperiodo;
    private String nomeperiodo;
    private String idsemestre;
    private String nomesemestre;

    public String getIdperiodo() {
        return idperiodo;
    }

    public void setIdperiodo(String idperiodo) {
        this.idperiodo = idperiodo;
    }

    public String getNomeperiodo() {
        return nomeperiodo;
    }

    public void setNomeperiodo(String nomeperiodo) {
        this.nomeperiodo = nomeperiodo;
    }

    public String getIssemestre() {
        return idsemestre;
    }

    public void setIssemestre(String issemestre) {
        this.idsemestre = issemestre;
    }

    public String getNomesemestre() {
        return nomesemestre;
    }

    public void setNomesemestre(String nomesemestre) {
        this.nomesemestre = nomesemestre;
    }
}
