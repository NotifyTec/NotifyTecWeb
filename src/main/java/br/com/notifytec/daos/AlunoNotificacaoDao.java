package br.com.notifytec.daos;

import br.com.notifytec.models.AlunoNotificacaoModel;
import br.com.notifytec.models.Parametros;
import java.util.UUID;
import javax.inject.Inject;
import javax.persistence.EntityManager;

public class AlunoNotificacaoDao extends CrudDao<AlunoNotificacaoModel>{
        
    public AlunoNotificacaoDao() {
        super(AlunoNotificacaoModel.class, Parametros.Tabelas.TABELA_ALUNO_NOTIFICACAO);
    }
    
    public AlunoNotificacaoModel getAlunoNotificacao(UUID notificacaoID, UUID usuarioID) {
        return (AlunoNotificacaoModel)manager.createNativeQuery("select n.* from ALUNONOTIFICACAO n WHERE n.ALUNOID = (SELECT ID FROM ALUNO WHERE USUARIOID = :usuarioid) AND n.NOTIFICACAOID = :notificacaoid",
                AlunoNotificacaoModel.class)
                .setParameter("usuarioid", usuarioID)
                .setParameter("notificacaoid", notificacaoID)
                .getSingleResult();
    }
}
