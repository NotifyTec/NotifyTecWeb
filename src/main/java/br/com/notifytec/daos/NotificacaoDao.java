package br.com.notifytec.daos;

import br.com.notifytec.models.AlunoNotificacaoModel;
import br.com.notifytec.models.NotificacaoCompletaModel;
import br.com.notifytec.models.NotificacaoModel;
import br.com.notifytec.models.NotificacaoOpcaoModel;
import br.com.notifytec.models.Parametros;
import br.com.notifytec.models.Transacao;
import br.com.notifytec.models.UsuarioModel;
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
    @Inject
    private UsuarioDao usuarioDao;

    public NotificacaoDao() {
        super(NotificacaoCompletaModel.class, Parametros.Tabelas.TABELA_NOTIFICACAO);
    }

    public List<NotificacaoCompletaModel> getPorUsuario(UUID usuarioID) {
        UsuarioModel usuario = usuarioDao.get(usuarioID);        
        if(usuario.isPodeEnviar()){
            return getEnviadas(usuarioID);
        }else{
            return getRecebidas(usuarioID);
        }
    }

    public List<NotificacaoCompletaModel> getRecebidas(UUID usuarioID) {
        List<NotificacaoCompletaModel> r = new ArrayList<NotificacaoCompletaModel>();

        List<NotificacaoCompletaModel> n = manager.createNativeQuery("SELECT\n" +
"	n.*\n" +
"FROM\n" +
"	NOTIFICACAO n\n" +
"INNER JOIN\n" +
"	ALUNONOTIFICACAO an\n" +
"ON\n" +
"	n.ID = an.NOTIFICACAOID\n" +
"WHERE\n" +
"	an.ALUNOID = (SELECT ALUNOID FROM ALUNO WHERE USUARIOID = :usuarioid);",
                NotificacaoCompletaModel.class)
                .setParameter("usuarioid", usuarioID)
                .getResultList();

        n = notificacaoOpcaoDao.carregarOpcoes(n);

        for (NotificacaoModel item : n) {
            NotificacaoCompletaModel nrm = new NotificacaoCompletaModel();
            nrm.setResposta(alunoNotificacaoDao.getAlunoNotificacao(item.getId(), usuarioID));

            r.add(nrm);
        }

        return r;
    }

    public List<NotificacaoCompletaModel> getEnviadas(UUID usuarioID) {
        List<NotificacaoCompletaModel> r = new ArrayList<NotificacaoCompletaModel>();

        List<NotificacaoCompletaModel> n = manager.createNativeQuery("SELECT\n" +
"	n.*\n" +
"FROM\n" +
"	NOTIFICACAO n\n" +
"WHERE\n" +
"	n.USUARIOID = :usuarioid;",
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
