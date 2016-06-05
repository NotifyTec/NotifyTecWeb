package br.com.notifytec.daos;

import br.com.notifytec.models.AlunoNotificacaoModel;
import br.com.notifytec.models.NotificacaoCompletaModel;
import br.com.notifytec.models.NotificacaoModel;
import br.com.notifytec.models.NotificacaoOpcaoModel;
import br.com.notifytec.models.Parametros;
import br.com.notifytec.models.Transacao;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;
import javax.inject.Inject;
import javax.persistence.EntityManager;

public class NotificacaoDao extends CrudDao<NotificacaoCompletaModel> {

    @Inject
    private NotificacaoOpcaoDao notificacaoOpcaoDao;
    @Inject
    private AlunoNotificacaoDao alunoNotificacaoDao;

    public NotificacaoDao() {
        super(NotificacaoCompletaModel.class, Parametros.Tabelas.TABELA_NOTIFICACAO);
    }

    public List<NotificacaoModel> getPorUsuario(UUID usuarioID) {
        return manager.createQuery("from NOTIFICACAO where USUARIOID = :usuarioid")
                .setParameter("usuarioid", usuarioID).getResultList();
    }

    public List<NotificacaoCompletaModel> getRecebidas(UUID alunoID) {
        List<NotificacaoCompletaModel> r = new ArrayList<NotificacaoCompletaModel>();

        List<NotificacaoCompletaModel> n = manager.createQuery("select n.* from NOTIFICACAO n inner join ALUNONOTIFICACAO nop on nop.NOTIFICACAOID = n.ID where nop.ALUNOID = :notificacaoid",
                NotificacaoCompletaModel.class)
                .setParameter("notificacaoid", alunoID)
                .getResultList();

        n = notificacaoOpcaoDao.carregarOpcoes(n);

        for (NotificacaoModel item : n) {
            NotificacaoCompletaModel nrm = new NotificacaoCompletaModel();
            nrm.setResposta(alunoNotificacaoDao.getAlunoNotificacao(item.getId(), alunoID));

            r.add(nrm);
        }

        return r;
    }

    public List<NotificacaoCompletaModel> getEnviadas(UUID usuarioID) {
        List<NotificacaoCompletaModel> r = new ArrayList<NotificacaoCompletaModel>();

        List<NotificacaoCompletaModel> n = manager.createQuery("select n.* from NOTIFICACAO n WHERE n.USUARIOID = :usuarioid",
                NotificacaoCompletaModel.class)
                .setParameter("usuarioid", usuarioID)
                .getResultList();

        n = notificacaoOpcaoDao.carregarOpcoes(n);

        return n;
    }

    public NotificacaoCompletaModel responder(UUID opcaoID, UUID alunoID) {
        AlunoNotificacaoModel n = alunoNotificacaoDao.getAlunoNotificacao(opcaoID, alunoID);
        n.setVisualizouEm(Calendar.getInstance().getTime());
        n.setNotificacaoOpcao(opcaoID);

        alunoNotificacaoDao.editar(n);

        return get(n.getNotificacaoID());
    }


}
