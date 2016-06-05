package br.com.notifytec.daos;

import br.com.notifytec.models.NotificacaoCompletaModel;
import br.com.notifytec.models.NotificacaoOpcaoModel;
import br.com.notifytec.models.Parametros;
import java.util.List;
import java.util.UUID;

public class NotificacaoOpcaoDao extends CrudDao<NotificacaoOpcaoModel> {
    
    public NotificacaoOpcaoDao() {
        super(NotificacaoOpcaoModel.class, Parametros.Tabelas.TABELA_NOTIFICACAO_OPCAO);
    }

    public List<NotificacaoOpcaoModel> getOpcaoList(UUID notificacaoID) {
        List<NotificacaoOpcaoModel> l
                = manager.createQuery("select n.* from NOTIFICACAOOPCAO n where n.NOTIFICAOID = :notificacaoid", NotificacaoOpcaoModel.class)
                .setParameter("notificacaoid", notificacaoID).getResultList();

        for (NotificacaoOpcaoModel o : l) {
            o.setTotalRespondidos(getTotalRespondidoOpcao(o.getId()));
        }

        return l;
    }

    public int getTotalRespondidoOpcao(UUID notificacaoOpcaoID) {
        return (int) manager.createQuery("SELECT count(a.ID) FROM ALUNONOTIFICACAO a WHERE a.NOTIFICACAOOPCAOID = :notificacaoOpcaoID")
                .setParameter("notificacaoOpcaoID", notificacaoOpcaoID)
                .getSingleResult();
    }

    public List<NotificacaoCompletaModel> carregarOpcoes(List<NotificacaoCompletaModel> n) {
        for (NotificacaoCompletaModel not : n) {
            not.setOpcoes(getOpcaoList(not.getId()));
            not.setTotalRespondidos(getTotalRespondidos(not.getId()));
            not.setTotalAlunosVisualizados(getTotalLidos(not.getId()));
            not.setTotalAlunosEnviados(getTotalEnviados(not.getId()));
        }

        return n;
    }

    public int getTotalEnviados(UUID notificacaoID) {
        return (int) manager.createQuery("SELECT count(a.ID) FROM ALUNONOTIFICACAO a WHERE a.NOTIFICACAOID = :notificacaoid")
                .setParameter("notificacaoid", notificacaoID)
                .getSingleResult();
    }

    public int getTotalLidos(UUID notificacaoID) {
        return (int) manager.createQuery("SELECT count(a.ID) FROM ALUNONOTIFICACAO a WHERE a.VIZUALIZOUEM IS NOT NULL AND a.NOTIFICACAOID = :notificacaoid")
                .setParameter("notificacaoid", notificacaoID)
                .getSingleResult();
    }

    public int getTotalRespondidos(UUID notificacaoID) {
        return (int) manager.createQuery("SELECT count(a.ID) FROM ALUNONOTIFICACAO a WHERE a.NOTIFICACAOOPCAOID IS NOT NULL AND a.NOTIFICACAOID = :notificacaoid")
                .setParameter("notificacaoid", notificacaoID)
                .getSingleResult();
    }
}
