package br.com.notifytec.daos;

import java.util.List;
import java.util.UUID;
import javax.inject.Inject;
import javax.persistence.EntityManager;

public class CrudDao<T> {

    @Inject
    private EntityManager manager;

    private final Class<T> type;
    private final String nomeDaTabela;

    public CrudDao(Class<T> type, String nomeDaTabela) {
        this.type = type;
        this.nomeDaTabela = nomeDaTabela.toUpperCase();
    }

    public List<T> get(){
        return manager.createQuery("from " + nomeDaTabela).getResultList();
    }
    
    public T get(UUID id) {
        return (T) manager.find(type, id);
    }
    
    public void remover(UUID id) {
        manager.remove(get(id));
    }
    
    public void remover(T model) {        
        manager.remove(model);
    }
    
    public void editar(T model) {
        manager.merge(model);
    }
}
