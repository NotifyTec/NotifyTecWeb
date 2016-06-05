package br.com.notifytec.services;

import br.com.notifytec.daos.CrudDao;
import br.com.notifytec.models.ResultadoPaginacao;
import br.com.notifytec.models.Transacao;
import java.util.List;
import java.util.UUID;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

public class CrudService<T> {

    protected CrudDao<T> dao;

    public CrudService(CrudDao<T> dao) {
        this.dao = dao;
    }

    public List<T> get() {
        return dao.get();
    }

    public T get(UUID id) {
        return dao.get(id);
    }

    public Transacao<T> save(EntityManager manager, boolean commit, T obj) {
        return dao.save(manager, commit, obj);
    }

    public Transacao<T> save(boolean commit, T obj) {
        return dao.save(commit, obj);
    }

    public Transacao<T> save(Transacao<T> t, boolean commit, T obj) {
        return dao.save(t, commit, obj);
    }

    public void save(T obj) {
        dao.save(obj);
    }

    public ResultadoPaginacao<T> paginated(int page) {
        return dao.paginated(page);
    }

    public void remover(UUID id) {
        dao.remover(id);
    }

    public void remover(T model) {
        dao.remover(model);
    }

    public void editar(T model) {
        dao.editar(model);
    }
}
