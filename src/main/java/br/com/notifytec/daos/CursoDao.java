/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.notifytec.daos;

import br.com.notifytec.models.CursoModel;
import br.com.notifytec.models.Parametros;
import java.util.UUID;
import java.util.List;

/**
 *
 * @author felip
 */
public class CursoDao extends CrudDao<CursoModel>{
     public CursoDao() {
        super(CursoModel.class, Parametros.Tabelas.TABELA_CURSO);
    }
    public List<CursoModel> getByNome(String nome){
        return 
            manager.createQuery("from CURSO where NOME like :nome")
                        .setParameter("nome", nome).getResultList();
    }
    public CursoModel getByPeriodo(UUID periodoID){
        return (CursoModel)manager.createNativeQuery("SELECT\n" +
"	c.*\n" +
"FROM\n" +
"	PERIODO p\n" +
"INNER JOIN\n" +
"	CURSO c\n" +
"ON \n" +
"	c.ID = p.CURSOID\n" +
"WHERE\n" +
"	p.ID = :periodoid", CursoModel.class)
                .setParameter("periodoid", periodoID)
                .getSingleResult();
    }
}
