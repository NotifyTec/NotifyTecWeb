package br.com.notifytec.services;

import br.com.notifytec.daos.CrudDao;
import br.com.notifytec.daos.NotificacaoDao;
import br.com.notifytec.models.AlunoNotificacaoModel;
import br.com.notifytec.models.AlunoPeriodoModel;
import br.com.notifytec.models.NotificacaoCompletaModel;
import br.com.notifytec.models.NotificacaoModel;
import br.com.notifytec.models.NotificacaoOpcaoModel;
import br.com.notifytec.models.Parametros;
import br.com.notifytec.models.Resultado;
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
    private FirebaseService firebaseService;
    
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

    public NotificacaoCompletaModel responder(UUID notificacaoID, UUID notificacaoOpcaoID, UUID usuarioID) throws Exception {
        return notificacaoDao.responder(notificacaoID, notificacaoOpcaoID, usuarioID);
    }

    public Resultado<NotificacaoCompletaModel> enviar(NotificacaoCompletaModel notificacao) {
        validarNotificacaoParaEnvio(notificacao);

        Transacao<NotificacaoCompletaModel> t = notificacaoDao.save(false, notificacao);
        Transacao<NotificacaoOpcaoModel> to;

        if (notificacao.getOpcoes() != null) {
            for (NotificacaoOpcaoModel o : notificacao.getOpcoes()) {
                to = notificacaoOpcaoService.save(t.getEntityManager(), false, o);
            }
        }

        List<UUID> alunosID = alunoPeriodoService.getAlunoPeriodosValidos(notificacao.getPeriodoID());
        Transacao<AlunoNotificacaoModel> tp;
        for (UUID alunoID : alunosID) {
            AlunoNotificacaoModel not = new AlunoNotificacaoModel();
            not.setId(UUID.randomUUID());
            not.setAlunoID(alunoID);
            not.setNotificacaoID(notificacao.getId());

            tp = alunoNotificacaoService.save(t.getEntityManager(), false, not);
        }
                
        //List<String> gcmTokens = notificacaoDao.getTokens(notificacao.getPeriodoID());
        
        Resultado<NotificacaoCompletaModel> r = new Resultado<NotificacaoCompletaModel>();
        //firebaseService.send(notificacao, gcmTokens);
        
        if(r.isSucess()){        
            try{
                t.getEntityManager().getTransaction().commit();
                r.setResult(notificacaoDao.getEnviadasPorID(notificacao.getId()));
            }catch(Exception ex){
                r.addError(ex.getMessage());
            }
        }
        else{
            r.setResult(notificacao);
        }
        
        return r;
    }

    public void validarNotificacaoParaEnvio(NotificacaoCompletaModel n) {
        n.setId(UUID.randomUUID());
        n.setDataHoraEnvio(Calendar.getInstance().getTime());
        
        if(n.getPeriodoID() == null || n.getPeriodoID() == Parametros.emptyUUID){
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
