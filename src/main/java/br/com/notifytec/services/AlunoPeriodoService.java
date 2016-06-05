package br.com.notifytec.services;

import br.com.notifytec.daos.AlunoPeriodoDao;
import br.com.notifytec.daos.CrudDao;
import br.com.notifytec.models.AlunoModel;
import br.com.notifytec.models.AlunoPeriodoModel;
import br.com.notifytec.models.Parametros;
import java.util.List;
import java.util.UUID;
import javax.inject.Inject;

public class AlunoPeriodoService extends CrudService<AlunoPeriodoModel> {
    
    @Inject
    private AlunoPeriodoDao dao;
    
    public AlunoPeriodoService() {
        super(new CrudDao(AlunoPeriodoModel.class, Parametros.Tabelas.TABELA_ALUNO_PERIODO));
    }
    
    public List<AlunoPeriodoModel> getAlunoPeriodosValidos(UUID periodoID) {
        return dao.getAlunoPeriodosValidos(periodoID);
    }
}
