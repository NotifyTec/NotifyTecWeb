package br.com.notifytec.daos;

import br.com.notifytec.models.NotificacaoModel;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;

public class NotificacaoDao {

    @Inject
    private EntityManager manager;

    public List<NotificacaoModel> getList() {
        List<NotificacaoModel> result = 
                manager.createQuery("from NOTIFICACAO").getResultList();
        return result;
    }
}
