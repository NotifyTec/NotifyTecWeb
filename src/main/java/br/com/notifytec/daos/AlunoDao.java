package br.com.notifytec.daos;

import br.com.notifytec.models.AlunoModel;
import br.com.notifytec.models.Parametros;

public class AlunoDao extends CrudDao<AlunoModel>{
    
    public AlunoDao() {
        super(AlunoModel.class, Parametros.Tabelas.TABELA_ALUNO);
    }
    
}
