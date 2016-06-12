package br.com.notifytec.services;

import br.com.notifytec.daos.CrudDao;
import br.com.notifytec.daos.NotificacaoDao;
import br.com.notifytec.models.AlunoNotificacaoModel;
import br.com.notifytec.models.AlunoPeriodoModel;
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

public class NotificacaoService extends CrudService<NotificacaoCompletaModel> {

    @Inject
    private NotificacaoDao notificacaoDao;
    @Inject
    private NotificacaoOpcaoService notificacaoOpcaoService;
    @Inject
    private AlunoPeriodoService alunoPeriodoService;
    @Inject
    private AlunoNotificacaoService alunoNotificacaoService;
    @Inject
    private GcmService gcmService;
    
    public NotificacaoService() {
        super(new NotificacaoDao());
    }

    public List<NotificacaoCompletaModel> getPorUsuario(UUID usuarioID){
        return notificacaoDao.getPorUsuario(usuarioID);
    }
    
    public List<NotificacaoCompletaModel> getRecebidas(UUID alunoID) {
        return notificacaoDao.getRecebidas(alunoID);
    }

    public List<NotificacaoCompletaModel> getEnviadas(UUID pessoaID) {
        return notificacaoDao.getEnviadas(pessoaID);
    }

    public NotificacaoCompletaModel responder(UUID opcaoID, UUID alunoID) {
        return notificacaoDao.responder(opcaoID, alunoID);
    }

    public NotificacaoCompletaModel enviar(NotificacaoCompletaModel notificacao, UUID periodoID) {
        validarNotificacaoParaEnvio(notificacao, periodoID);

        Transacao<NotificacaoCompletaModel> t = notificacaoDao.save(false, notificacao);
        Transacao<NotificacaoOpcaoModel> to;

        if (notificacao.getOpcoes() != null) {
            for (NotificacaoOpcaoModel o : notificacao.getOpcoes()) {
                to = notificacaoOpcaoService.save(t.getEntityManager(), false, o);
            }
        }

        List<AlunoPeriodoModel> periodos = alunoPeriodoService.getAlunoPeriodosValidos(periodoID);
        Transacao<AlunoNotificacaoModel> tp;
        for (AlunoPeriodoModel periodo : periodos) {
            AlunoNotificacaoModel not = new AlunoNotificacaoModel();
            not.setId(UUID.randomUUID());
            not.setAlunoID(periodo.getAlunoID());
            not.setNotificacaoID(notificacao.getId());

            tp = alunoNotificacaoService.save(t.getEntityManager(), false, not);

            // TODO: SEND GC MESSAGE
        }
        
        //gcmService.send(get(notificacao.getId()), new ArrayList<String>());
        
        t.getEntityManager().getTransaction().commit();
        
        return get(notificacao.getId());
    }

    public void validarNotificacaoParaEnvio(NotificacaoCompletaModel n, UUID periodoID) {
        n.setId(UUID.randomUUID());
        n.setDataHoraEnvio(Calendar.getInstance().getTime());
        
        if(periodoID == null || periodoID == Parametros.emptyUUID){
            throw new NullPointerException("O período do curso é obrigatório.");
        }
        
        if (n.getConteudo() == null || n.getConteudo().isEmpty()) {
            throw new NullPointerException("O campo conteúdo é obrigatório.");
        }

        if (n.getTitulo() == null || n.getTitulo().isEmpty()) {
            throw new NullPointerException("O campo título é obrigatório.");
        }

        if (n.getUsuarioID() == null || n.getUsuarioID() == Parametros.emptyUUID) {
            throw new NullPointerException("O usuário é obrigatório.");
        }

        if (n.getOpcoes() != null) {
            for (NotificacaoOpcaoModel op : n.getOpcoes()) {
                op.setId(UUID.randomUUID());
                op.setNotificacaoID(n.getId());
                
                if(op.getNome() == null || op.getNome().isEmpty()){
                    throw new NullPointerException("Todos os nomes das opções são obrigatórios.");
                }                                
            }
        }
    }
}
