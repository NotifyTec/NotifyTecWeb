package br.com.notifytec.services;

import br.com.notifytec.daos.CrudDao;
import br.com.notifytec.daos.NotificacaoOpcaoDao;
import br.com.notifytec.models.NotificacaoCompletaModel;
import br.com.notifytec.models.NotificacaoOpcaoModel;
import br.com.notifytec.models.Parametros;
import br.com.notifytec.models.ResultadoPaginacao;
import br.com.notifytec.models.Transacao;
import java.util.List;
import java.util.UUID;
import javax.inject.Inject;
import javax.persistence.EntityManager;

public class NotificacaoOpcaoService extends CrudService<NotificacaoOpcaoModel> {

    @Inject
    private NotificacaoOpcaoDao dao;

    public NotificacaoOpcaoService() {
        super(new NotificacaoOpcaoDao());
    }

    public List<NotificacaoOpcaoModel> getOpcaoList(UUID notificacaoID) {
        return dao.getOpcaoList(notificacaoID);
    }

    public int getTotalRespondidoOpcao(UUID notificacaoOpcaoID) {
        return dao.getTotalRespondidoOpcao(notificacaoOpcaoID);
    }

    public List<NotificacaoCompletaModel> carregarOpcoes(List<NotificacaoCompletaModel> n) {
        return dao.carregarOpcoes(n);
    }

    public int getTotalEnviados(UUID notificacaoID) {
        return dao.getTotalEnviados(notificacaoID);
    }

    public int getTotalLidos(UUID notificacaoID) {
        return dao.getTotalLidos(notificacaoID);
    }

    public int getTotalRespondidos(UUID notificacaoID) {
        return dao.getTotalRespondidos(notificacaoID);
    }
}
