package br.com.notifytec.models;

import javax.persistence.EntityManager;

public class Transacao <T> {
    private T resultado;
    private EntityManager entityManager;

    public T getResultado() {
        return resultado;
    }

    public void setResultado(T resultado) {
        this.resultado = resultado;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }   
    
}
