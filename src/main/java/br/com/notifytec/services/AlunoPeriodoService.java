package br.com.notifytec.services;

import br.com.notifytec.daos.AlunoPeriodoDao;
import br.com.notifytec.models.AlunoPeriodoModel;
import br.com.notifytec.models.PeriodoSemestre;
import br.com.notifytec.models.Resultado;
import java.util.List;
import java.util.UUID;
import javax.inject.Inject;
import javax.persistence.EntityManager;

public class AlunoPeriodoService{
    
    @Inject
    private AlunoPeriodoDao dao;
    
    public List<AlunoPeriodoModel> getAlunoPeriodosValidos(UUID periodoID) {
        return dao.getAlunoPeriodosValidos(periodoID);
    }
    
    public  Resultado<AlunoPeriodoModel> addAlunoPeriodo(EntityManager manager,boolean commit,AlunoPeriodoModel u) {
        Resultado<AlunoPeriodoModel> resultado = new Resultado<>(); 
        dao.save(manager, commit, u);
        resultado.setResult(dao.get(u.getID())); 
        return resultado;
    }  
    public List<AlunoPeriodoModel> getListPeriodo(UUID alunoID){
        List<AlunoPeriodoModel> lista = dao.getAlunoPeriodo(alunoID);
        return lista;
    }
    
    public void edit (List<PeriodoSemestre> lista, UUID alunoID){
        List<AlunoPeriodoModel> listaPeriodo = getListPeriodo(alunoID);
        for(AlunoPeriodoModel a : listaPeriodo){
            dao.remover(a);
        }
        for(PeriodoSemestre p : lista ){
            AlunoPeriodoModel a = new AlunoPeriodoModel();
            a.setID(UUID.randomUUID());
            a.setAlunoID(alunoID);
            a.setSemestreID(UUID.fromString(p.getIssemestre()));
            a.setPeriodoID(UUID.fromString(p.getIdperiodo()));
            dao.save(a);
        }
    }
    
}
