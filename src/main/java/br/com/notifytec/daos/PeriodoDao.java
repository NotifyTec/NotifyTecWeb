/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.notifytec.daos;

import br.com.notifytec.models.CursoModel;
import br.com.notifytec.models.NotificacaoModel;
import br.com.notifytec.models.Parametros;
import br.com.notifytec.models.PeriodoModel;
import java.util.AbstractList;
import java.util.List;
import java.util.UUID;
import javax.inject.Inject;
import javax.persistence.EntityManager;



public class PeriodoDao extends CrudDao<PeriodoModel>{    
    public PeriodoDao() {
        super(PeriodoModel.class, Parametros.Tabelas.TABELA_PERIODO);
    }
    
    public List<PeriodoModel> getByID(UUID cursoID) {
        return 
                manager.createQuery("from PERIODO where CURSOID = :cursoID order by NUMERO asc")
                        .setParameter("cursoID", cursoID).getResultList();        
    }
}
