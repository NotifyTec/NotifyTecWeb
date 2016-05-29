package br.com.notifytec.daos;

import br.com.notifytec.models.FuncionarioModel;
import br.com.notifytec.models.Parametros;

public class FuncionarioDao extends CrudDao<FuncionarioModel> {

    public FuncionarioDao() {
        super(FuncionarioModel.class, Parametros.Tabelas.TABELA_PESSOA);
    }
    
}
