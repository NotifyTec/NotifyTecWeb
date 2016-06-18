package br.com.notifytec.daos;

import br.com.notifytec.models.NotificacaoCompletaModel;
import br.com.notifytec.models.NotificacaoOpcaoModel;
import br.com.notifytec.models.Parametros;
import br.com.notifytec.services.CursoService;
import br.com.notifytec.services.PeriodoService;
import java.math.BigInteger;
import java.util.List;
import java.util.UUID;
import javax.inject.Inject;
import javax.persistence.EntityManager;

public class NotificacaoOpcaoDao extends CrudDao<NotificacaoOpcaoModel> {

    @Inject
    private PeriodoService periodoService;
    @Inject
    private CursoService cursoService;
    @Inject
    private FuncionarioDao funcionarioDao;

    public NotificacaoOpcaoDao() {
        super(NotificacaoOpcaoModel.class, Parametros.Tabelas.TABELA_NOTIFICACAO_OPCAO);
    }

    public List<NotificacaoOpcaoModel> getOpcaoList(UUID notificacaoID) {
        EntityManager manager = open();
        List<NotificacaoOpcaoModel> l
                = manager.createNativeQuery("select n.* from NOTIFICACAOOPCAO n where n.NOTIFICAOID = :notificacaoid", NotificacaoOpcaoModel.class)
                .setParameter("notificacaoid", notificacaoID).getResultList();

        if (l == null || l.size() == 0) {
            return null;
        }

        for (NotificacaoOpcaoModel o : l) {
            o.setTotalRespondidos(getTotalRespondidoOpcao(o.getId()).intValue());
        }
        close(manager);
        return l;
    }

    public BigInteger getTotalRespondidoOpcao(UUID notificacaoOpcaoID) {
        EntityManager manager = open();
        BigInteger i = (BigInteger) manager.createNativeQuery("SELECT count(a.ID) FROM ALUNONOTIFICACAO a WHERE a.NOTIFICACAOOPCAOID = :notificacaoOpcaoID")
                .setParameter("notificacaoOpcaoID", notificacaoOpcaoID)
                .getSingleResult();
        close(manager);
        return i;
    }

    public List<NotificacaoCompletaModel> carregarOpcoes(List<NotificacaoCompletaModel> n) {
        for (NotificacaoCompletaModel not : n) {
            List<NotificacaoOpcaoModel> list = getOpcaoList(not.getId());
            if (list != null) {
                not.setOpcoes(list);
            }
            not.setTotalRespondidos(getTotalRespondidos(not.getId()).intValue());
            not.setTotalAlunosVisualizados(getTotalLidos(not.getId()).intValue());
            not.setTotalAlunosEnviados(getTotalEnviados(not.getId()).intValue());

            not.setNomePeriodo(String.valueOf(periodoService.get(not.getPeriodoID()).getNumero()));
            not.setNomeCurso(cursoService.getByPeriodo(not.getPeriodoID()).getNome());
            not.setNomeUsuario(funcionarioDao.getByUsuario(not.getUsuarioID()).getNome());
        }

        return n;
    }

    public BigInteger getTotalEnviados(UUID notificacaoID) {
        EntityManager manager = open();
        BigInteger i = (BigInteger) manager.createNativeQuery("SELECT count(a.ID) FROM ALUNONOTIFICACAO a WHERE a.NOTIFICACAOID = :notificacaoid")
                .setParameter("notificacaoid", notificacaoID)
                .getSingleResult();
        close(manager);
        return i;
    }

    public BigInteger getTotalLidos(UUID notificacaoID) {
        EntityManager manager = open();
        BigInteger i = (BigInteger) manager.createNativeQuery("SELECT count(a.ID) FROM ALUNONOTIFICACAO a WHERE a.VIZUALIZOUEM IS NOT NULL AND a.NOTIFICACAOID = :notificacaoid")
                .setParameter("notificacaoid", notificacaoID)
                .getSingleResult();
        close(manager);
        return i;
    }

    public BigInteger getTotalRespondidos(UUID notificacaoID) {
        EntityManager manager = open();
        BigInteger i = (BigInteger) manager.createNativeQuery("SELECT count(a.ID) FROM ALUNONOTIFICACAO a WHERE a.NOTIFICACAOOPCAOID IS NOT NULL AND a.NOTIFICACAOID = :notificacaoid")
                .setParameter("notificacaoid", notificacaoID)
                .getSingleResult();
        close(manager);
        return i;
    }
}
