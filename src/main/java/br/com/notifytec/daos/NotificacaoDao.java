package br.com.notifytec.daos;

import br.com.notifytec.models.NotificacaoModel;
import java.util.List;
import java.util.UUID;
import javax.inject.Inject;
import javax.persistence.EntityManager;

public class NotificacaoDao {

    @Inject
    private EntityManager manager;

    public List<NotificacaoModel> get(UUID usuarioID) {
        return 
                manager.createQuery("from NOTIFICACAO where USUARIOID = :usuarioid")
                        .setParameter("usuarioid", usuarioID).getResultList();        
    }
}
