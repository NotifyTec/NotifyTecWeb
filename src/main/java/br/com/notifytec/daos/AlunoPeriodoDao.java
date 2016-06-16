package br.com.notifytec.daos;

import br.com.notifytec.models.AlunoPeriodoModel;
import br.com.notifytec.models.Parametros;
import br.com.notifytec.services.AlunoService;
import java.util.List;
import java.util.UUID;
import javax.inject.Inject;

public class AlunoPeriodoDao extends CrudDao<AlunoPeriodoModel> {

    @Inject
    private AlunoDao alunodao;
 
    
    public AlunoPeriodoDao() {
        super(AlunoPeriodoModel.class, Parametros.Tabelas.TABELA_ALUNO_PERIODO);
    }

    public List<AlunoPeriodoModel> getAlunoPeriodosValidos(UUID periodoID) {
        List<AlunoPeriodoModel> periodos = get();
        
        for(AlunoPeriodoModel p : periodos){
            if(!p.getPeriodoID().equals(periodoID)){
                periodos.remove(p);
            }
        }
        
        preencher(periodos);
        
        return periodos;
        
        //return manager.createNativeQuery("ap.* FROM ALUNOPERIODO ap INNER JOIN SEMESTRE s ON ap.SEMESTREID = s.ID WHERE ap.PERIODOID = :periodoid AND NOW() between s.INICIO AND s.FIM",
          //      AlunoPeriodoModel.class)
            //    .setParameter("periodoid", periodoID)
              //  .getResultList();
    }
    
    public List<AlunoPeriodoModel> getAlunoPeriodo(UUID alunoID){
           List<AlunoPeriodoModel> lista = 
                manager.createQuery("from ALUNOPERIODO where ALUNOID = :alunoID")
                        .setParameter("alunoID", alunoID).getResultList();  
           return lista;
    }

    private void preencher(List<AlunoPeriodoModel> list){
        for(AlunoPeriodoModel a : list){
            a.setAluno(alunodao.get(a.getAlunoID()));
        }
    }
}
