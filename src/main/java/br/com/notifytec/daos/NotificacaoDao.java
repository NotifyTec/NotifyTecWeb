package br.com.notifytec.daos;

import br.com.notifytec.models.AlunoNotificacaoModel;
import br.com.notifytec.models.NotificacaoCompletaModel;
import br.com.notifytec.models.NotificacaoModel;
import br.com.notifytec.models.NotificacaoOpcaoModel;
import br.com.notifytec.models.Parametros;
import br.com.notifytec.models.Transacao;
import br.com.notifytec.models.UsuarioModel;
import br.com.notifytec.services.CursoService;
import br.com.notifytec.services.PeriodoService;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import javax.transaction.Transactional;

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

    public List<String> getTokens(UUID periodoID){
        String sql = "SELECT\n" +
                        "	GCMTOKEN\n" +
                        "FROM \n" +
                        "	USUARIO u\n" +
                        "INNER JOIN\n" +
                        "	ALUNO a\n" +
                        "ON\n" +
                        "	a.USUARIOID = u.ID\n" +
                        "INNER JOIN\n" +
                        "	ALUNOPERIODO ap\n" +
                        "ON\n" +
                        "	ap.ALUNOID = a.ID\n" +
                        "INNER JOIN\n" +
                        "	SEMESTRE s\n" +
                        "ON\n" +
                        "	s.ID = ap.SEMESTREID\n" +
                        "WHERE\n" +
                        "	ap.PERIODOID = :periodoid AND\n" +
                        "    :date BETWEEN s.INICIO AND s.FIM;";
        
        return manager.createNativeQuery(sql)
                .setParameter("periodoid", periodoID)
                .setParameter("date", Calendar.getInstance().getTime(), TemporalType.DATE)
                .getResultList();
    }
    
    public List<NotificacaoCompletaModel> getPorUsuario(UUID usuarioID) {
        UsuarioModel usuario = usuarioDao.get(usuarioID);        
        if(usuario.isPodeEnviar()){
            return getEnviadas(usuarioID);
        }else{
            return getRecebidas(usuarioID);
        }
    }

    @Transactional
    public List<NotificacaoCompletaModel> getRecebidas(UUID usuarioID) {        
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
        
        manager.getTransaction().begin();
        Query update = manager.createNativeQuery("UPDATE \n" +
"	ALUNONOTIFICACAO\n" +
"SET\n" +
"	VIZUALIZOUEM = NOW()\n" +
"WHERE\n" +
"	ALUNOID = (SELECT ID FROM ALUNO WHERE USUARIOID = :usuarioid) AND\n" +
"    VIZUALIZOUEM IS NULL")
                .setParameter("usuarioid", usuarioID);
        update.executeUpdate();
        manager.getTransaction().commit();
        
        for (NotificacaoCompletaModel item : n) {
            item.setResposta(alunoNotificacaoDao.getAlunoNotificacao(item.getId(), usuarioID));                        
        }

        return n;
    }

    public List<NotificacaoCompletaModel> getEnviadas(UUID usuarioID) {
        List<NotificacaoCompletaModel> n = manager.createNativeQuery("SELECT\n" +
"	n.*\n" +
"FROM\n" +
"	NOTIFICACAO n\n" +
"WHERE\n" +
"	n.USUARIOID = :usuarioid",
                NotificacaoCompletaModel.class)
                .setParameter("usuarioid", usuarioID)
                .getResultList();

        n = notificacaoOpcaoDao.carregarOpcoes(n);

        return n;
    }
    
    public NotificacaoCompletaModel getRecebidasPorID(UUID usuarioID, UUID notificacaoID) {        
        List<NotificacaoCompletaModel> n = manager.createNativeQuery("SELECT\n" +
"	n.*\n" +
"FROM\n" +
"	NOTIFICACAO n\n" +
"INNER JOIN\n" +
"	ALUNONOTIFICACAO an\n" +
"ON\n" +
"	n.ID = an.NOTIFICACAOID\n" +
"WHERE\n" +
"	an.ALUNOID = (SELECT ALUNOID FROM ALUNO WHERE USUARIOID = :usuarioid)"
                + "AND n.ID = :notificacaoID",
                NotificacaoCompletaModel.class)
                .setParameter("usuarioid", usuarioID)
                .setParameter("notificacaoID", notificacaoID)
                .getResultList();

        n = notificacaoOpcaoDao.carregarOpcoes(n);

        for (NotificacaoCompletaModel item : n) {
            item.setResposta(alunoNotificacaoDao.getAlunoNotificacao(item.getId(), usuarioID));                        
        }

        return n.get(0);
    }
    
    public NotificacaoCompletaModel getEnviadasPorID(UUID notificacaoID) {
        List<NotificacaoCompletaModel> r = new ArrayList<NotificacaoCompletaModel>();

        List<NotificacaoCompletaModel> n = manager.createNativeQuery("SELECT\n" +
"	n.*\n" +
"FROM\n" +
"	NOTIFICACAO n\n" +
"WHERE\n" +
"	n.ID = :notificacaoid",
                NotificacaoCompletaModel.class)
                .setParameter("notificacaoid", notificacaoID)
                .getResultList();

        n = notificacaoOpcaoDao.carregarOpcoes(n);

        return n.get(0);
    }

    public NotificacaoCompletaModel responder(UUID notificacaoID, UUID notificacaoOpcaoID, UUID usuarioID) throws Exception {
        
        AlunoNotificacaoModel n = alunoNotificacaoDao.getAlunoNotificacao(notificacaoID, usuarioID);                
        if(n.getNotificacaoOpcao() != null)
            throw new Exception("Você já respondeu essa questão.");
        n.setVisualizouEm(Calendar.getInstance().getTime());
        n.setNotificacaoOpcao(notificacaoOpcaoID);

        alunoNotificacaoDao.editar(n);

        return getRecebidasPorID(usuarioID, notificacaoID);
    }
}
