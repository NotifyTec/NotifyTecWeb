package br.com.notifytec.daos;

import br.com.notifytec.models.AlunoPeriodoModel;
import br.com.notifytec.models.Parametros;
import br.com.notifytec.services.AlunoService;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;
import javax.inject.Inject;
import javax.persistence.TemporalType;

public class AlunoPeriodoDao extends CrudDao<AlunoPeriodoModel> {

    @Inject
    private AlunoDao alunodao;
 
    
    public AlunoPeriodoDao() {
        super(AlunoPeriodoModel.class, Parametros.Tabelas.TABELA_ALUNO_PERIODO);
    }

    public List<UUID> toUUID(List<String> uuids){
        List<UUID> list = new ArrayList<>();
        for(String u : uuids){
            
            String nova = "";
            nova += u.substring(0, 8);
            nova += '-';
            nova += u.substring(8, 8+4);
            nova += '-';
            nova += u.substring(12, 8+4+4);
            nova += '-';
            nova += u.substring(16, 8+4+4+4);
            nova += '-';
            nova += u.substring(20, 8+4+4+4+12);
            
            list.add(UUID.fromString(nova));
        }
        
        return list;
    }
    
    public List<UUID> getAlunoPeriodosValidos(UUID periodoID) {
        List<String> l = manager.createNativeQuery("SELECT\n" +
"	HEX(a.ID)\n" +
"FROM \n" +
"	USUARIO u\n" +
"INNER JOIN\n" +
"	ALUNO a\n" +
"ON\n" +
"	a.USUARIOID = u.ID\n" +
"INNER JOIN\n" +
"	ALUNOPERIODO ap\n" +
"ON\n" +
"	ap.ALUNOID = a.ID\n" +
"INNER JOIN\n" +
"	SEMESTRE s\n" +
"ON\n" +
"	s.ID = ap.SEMESTREID\n" +
"WHERE\n" +
"	ap.PERIODOID = :periodoid AND\n" +
"    :date BETWEEN s.INICIO AND s.FIM;")
                .setParameter("periodoid", periodoID)
                .setParameter("date", Calendar.getInstance().getTime(), TemporalType.DATE)
               .getResultList();        
        
        return toUUID(l);
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
