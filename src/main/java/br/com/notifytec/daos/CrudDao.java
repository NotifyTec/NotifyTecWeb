package br.com.notifytec.daos;

import br.com.notifytec.models.ResultadoPaginacao;
import br.com.notifytec.models.Transacao;
import java.util.List;
import java.util.UUID;
import javax.persistence.EntityManager;
import javax.persistence.Query;

public class CrudDao<T> extends DatabaseManager {

    private final int LIMITE_REGISTROS_POR_PAGINA = 25;

    private final Class<T> type;
    private final String nomeDaTabela;

    public CrudDao(Class<T> type, String nomeDaTabela) {
        this.type = type;
        this.nomeDaTabela = nomeDaTabela.toUpperCase();
    }

    public List<T> get() {
        EntityManager manager = open();
        List<T> l = manager.createQuery("from " + nomeDaTabela).getResultList();
        close(manager);
        return l;
    }

    public T get(UUID id) {
        EntityManager manager = open();
        T d = (T) manager.find(type, id);
        close(manager);
        return d;
    }

    public Transacao<T> save(EntityManager manager, boolean commit, T obj) {
        Transacao<T> t = new Transacao<>();
        t.setEntityManager(manager);

        return save(t, commit, obj);
    }

    public void save(T obj) {
        Transacao<T> t = new Transacao<>();
        t.setEntityManager(open());
        save(t, true, obj);
    }

    public Transacao<T> save(boolean commit, T obj) {
        return save(open(), commit, obj);
    }

    public Transacao<T> save(Transacao<T> t, boolean commit, T obj) {
        if (t.getEntityManager() == null) {
            t.setEntityManager(open());
        }

        if (!t.getEntityManager().getTransaction().isActive()) {
            t.getEntityManager().getTransaction().begin();
        }

        t.getEntityManager().persist(obj);

        if (commit) {
            t.getEntityManager().flush();
            t.getEntityManager().getTransaction().commit();
        }

        return t;
    }

    public ResultadoPaginacao<T> paginated(int page) {
        EntityManager manager = open();
        ResultadoPaginacao<T> resultado = new ResultadoPaginacao<T>();

        if (page <= 0) {
            page = 1;
        }

        String jpql = "SELECT count(o) FROM " + nomeDaTabela + " o";
        Query q = manager.createQuery(jpql);
        Long totalDeRegistros = (Long) q.getSingleResult();

        int limiteInicial = (page - 1) * LIMITE_REGISTROS_POR_PAGINA;;

        List<T> registros = manager.createQuery("from " + nomeDaTabela)
                .setFirstResult(limiteInicial)
                .setMaxResults(LIMITE_REGISTROS_POR_PAGINA).getResultList();

        resultado.setTotalDeRegistros(totalDeRegistros);
        resultado.setResult(registros);
        resultado.setPagina(page);
        resultado.setRegistrosPorPagina(LIMITE_REGISTROS_POR_PAGINA);
        close(manager);
        return resultado;
    }

    public void remover(UUID id) {
        remover(get(id));
    }

    public void remover(T model) {
        EntityManager manager = open(true);
        manager.remove(manager.contains(model) ? model : manager.merge(model));
        close(manager, true);
    }

    public void editar(T model) {
        EntityManager manager = open(true);
        manager.merge(model);
        close(manager, true);
    }
}
