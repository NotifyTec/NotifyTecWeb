package br.com.notifytec.daos;

import br.com.notifytec.models.FuncionarioModel;

public class FuncionarioDao extends CrudDao<FuncionarioModel> {

    public FuncionarioDao() {
        super(FuncionarioModel.class, "FUNCIONARIO");
    }
    
}
