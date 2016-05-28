package br.com.notifytec.services;

import br.com.notifytec.daos.FuncionarioDao;
import br.com.notifytec.models.FuncionarioModel;
import java.util.List;
import javax.inject.Inject;

public class FuncionarioService {
    @Inject
    private FuncionarioDao dao;
    
    public List<FuncionarioModel> get(){
        return dao.get();
    }
}
