package br.com.notifytec.services;

import br.com.notifytec.daos.FuncionarioDao;
import br.com.notifytec.models.FuncionarioModel;
import br.com.notifytec.models.PaginatedList;
import java.util.List;
import javax.inject.Inject;

public class FuncionarioService {
    @Inject
    private FuncionarioDao dao;
    
    public PaginatedList get(int pagina){
        return dao.paginated(pagina);
    }
}
