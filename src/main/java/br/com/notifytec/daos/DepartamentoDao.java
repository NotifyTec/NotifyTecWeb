/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.notifytec.daos;

import br.com.notifytec.models.DepartamentoModel;
import br.com.notifytec.models.Parametros;
import br.com.notifytec.models.ResultadoPaginacao;
import java.util.List;
import java.util.UUID;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author felip
 */
public class DepartamentoDao extends CrudDao<DepartamentoModel>{
    public DepartamentoDao(){
        super(DepartamentoModel.class, Parametros.Tabelas.TABELA_DEPARTAMENTO);
    }
    
    public List<DepartamentoModel> getByNome(String nome){
        EntityManager manager = open();
          List<DepartamentoModel> l =
            manager.createQuery("from DEPARTAMENTO where NOME like :nome")
                        .setParameter("nome", nome).getResultList();
          close(manager);
          return l;
    }
    
    public ResultadoPaginacao<DepartamentoModel> getPaginacao(int page){
        EntityManager manager = open();
        ResultadoPaginacao<DepartamentoModel> resultado = new ResultadoPaginacao<DepartamentoModel>();

        if (page <= 0) {
            page = 1;
        }

        String jpql = "SELECT count(o) FROM " + "DEPARTAMENTO" + " o";
        Query q = manager.createQuery(jpql);
        Long totalDeRegistros = (Long) q.getSingleResult();

        int limiteInicial = (page - 1) * 25;
        List<DepartamentoModel> registros = manager.createQuery("from DEPARTAMENTO order by NOME")
                .setFirstResult(limiteInicial)
                .setMaxResults(25).getResultList();
        resultado.setTotalDeRegistros(totalDeRegistros);
        resultado.setResult(registros);
        resultado.setPagina(page);
        resultado.setRegistrosPorPagina(25);
        close(manager);
        return resultado;
    }
    public boolean getCountDepartamento(UUID id){
        EntityManager manager = open();
        List<DepartamentoModel> l = manager.createQuery("from PESSOA where DEPARTAMENTOID like :id")
                        .setParameter("id", id).getResultList();
        close(manager);
        if(l.size() > 0) return false;
        return true;
    }
    
}
