package br.com.notifytec.services;

import br.com.notifytec.daos.AlunoNotificacaoDao;
import br.com.notifytec.daos.CrudDao;
import br.com.notifytec.models.AlunoNotificacaoModel;
import br.com.notifytec.models.Parametros;

public class AlunoNotificacaoService extends CrudService<AlunoNotificacaoModel> { 
    
    public AlunoNotificacaoService() {
        super(new AlunoNotificacaoDao());
    }
    
}
