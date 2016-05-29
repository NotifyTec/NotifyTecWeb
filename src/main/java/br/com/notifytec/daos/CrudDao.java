package br.com.notifytec.daos;

import br.com.notifytec.models.PaginatedList;
import br.com.notifytec.models.ResultadoPaginacao;
import br.com.notifytec.models.Transacao;
import java.util.List;
import java.util.UUID;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

@Transactional
public class CrudDao<T> {

    private final int LIMITE_REGISTROS_POR_PAGINA = 25;

    @Inject
    @PersistenceContext
    private EntityManager manager;

    private final Class<T> type;
    private final String nomeDaTabela;

    public CrudDao(Class<T> type, String nomeDaTabela) {
        this.type = type;
        this.nomeDaTabela = nomeDaTabela.toUpperCase();
    }

    public List<T> get() {
        return manager.createQuery("from " + nomeDaTabela).getResultList();
    }

    public T get(UUID id) {
        return (T) manager.find(type, id);
    }

    public Transacao<T> save(EntityManager manager, boolean commit, T obj) {        
        Transacao<T> t = new Transacao<>();
        t.setEntityManager(manager);
        
        return save(t, commit, obj);
    }

    public Transacao<T> save(boolean commit, T obj) {        
        return save(manager, commit, obj);
    }
    
    public Transacao<T> save(Transacao<T> t, boolean commit, T obj) {        
        if (t.getEntityManager() == null) {            
            t.setEntityManager(this.manager);
        }

        if (!t.getEntityManager().getTransaction().isActive()) {
            t.getEntityManager().getTransaction().begin();
        }

        t.getEntityManager().persist(obj);

        if (commit) {
            t.getEntityManager().getTransaction().commit();
        }

        return t;
    }

    public void save(T obj) {
        Transacao<T> t = new Transacao<>();
        t.setEntityManager(manager);
        save(t, true, obj);
    }

    public ResultadoPaginacao<T> paginated(int page) {
        ResultadoPaginacao<T> resultado = new ResultadoPaginacao<T>();

        if (page <= 0) {
            page = 1;
        }

        String jpql = "SELECT count(o) FROM " + nomeDaTabela + " o";
        Query q = manager.createQuery(jpql);
        Long totalDeRegistros = (Long) q.getSingleResult();

        int limiteInicial = (page - 1) * LIMITE_REGISTROS_POR_PAGINA;        ;

        List<T> registros = manager.createQuery("from " + nomeDaTabela)
                .setFirstResult(limiteInicial)
                .setMaxResults(LIMITE_REGISTROS_POR_PAGINA).getResultList();

        resultado.setTotalDeRegistros(totalDeRegistros);
        resultado.setResult(registros);
        resultado.setPagina(page);
        resultado.setRegistrosPorPagina(LIMITE_REGISTROS_POR_PAGINA);

        return resultado;
    }

    @Transactional
    public void remover(UUID id) {
        remover(get(id));
    }

    @Transactional
    public void remover(T model) {
        manager.getTransaction().begin();
        manager.remove(model);
        manager.getTransaction().commit();
    }

    @Transactional
    public void editar(T model) {
        manager.getTransaction().begin();
        manager.merge(model);
        manager.getTransaction().commit();
    }
}
