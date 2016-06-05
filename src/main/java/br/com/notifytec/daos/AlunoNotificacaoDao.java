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
    
    public AlunoNotificacaoModel getAlunoNotificacao(UUID notificacaoID, UUID alunoID) {
        return manager.createQuery("select n.* from ALUNONOTIFICACAO n WHERE n.ALUNOID = :alunoid AND n.NOTIFICACAOID = :notificacaoid",
                AlunoNotificacaoModel.class)
                .setParameter("alunoid", alunoID)
                .setParameter("notificacaoid", notificacaoID)
                .getSingleResult();
    }
}
