/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.notifytec.services;

import br.com.notifytec.daos.PeriodoDao;
import br.com.notifytec.models.FuncionarioModel;
import br.com.notifytec.models.PeriodoModel;
import br.com.notifytec.models.Resultado;
import br.com.notifytec.models.Transacao;
import java.util.List;
import java.util.UUID;
import javax.inject.Inject;
import javax.persistence.EntityManager;

/**
 *
 * @author felip
 */

public class PeriodoService {
    @Inject
    private PeriodoDao dao;
    @Inject
    private UsuarioService usuarioService;

    public List<PeriodoModel> get(UUID cursoID) {
        List<PeriodoModel> list = dao.getByID(cursoID);
        return list;
    }
    
    public Resultado<PeriodoModel> editar(UUID cursoID, int qtdPeriodo){
         Resultado<PeriodoModel> resultado = new Resultado<>(); 
         List<PeriodoModel> periodos = dao.getByID(cursoID);
         if(periodos.size() == qtdPeriodo)
             return resultado;
         if(qtdPeriodo > periodos.size()){
             for(int i = periodos.size()+1; i<=qtdPeriodo; i++){
                 PeriodoModel p = new PeriodoModel();
                 p.setId(UUID.randomUUID());
                 p.setNumero(i);
                 p.setCursoid(cursoID);
                dao.save(p);
             }
         }else{
             for(int i = periodos.size(); i > qtdPeriodo; i--){
                 dao.remover(periodos.get(i - 1));
             }
         }
         return resultado;
    }
    
    
    

    /*public Resultado<PeriodoModel> add(PeriodoModel f) {
        Resultado<PeriodoModel> r = new Resultado<>();
        dao.save(true,f);
        r.setResult(dao.get(f.getId()));
        return r;
    } */  
        public Resultado<PeriodoModel> add(EntityManager manager ,PeriodoModel u) {
            
            
            Resultado<PeriodoModel> resultado = new Resultado<>(); 
           boolean commit = false;
            for(int i =1; i<=u.getNumero(); i++){
                PeriodoModel p = new PeriodoModel();
                p.setId(UUID.randomUUID());
                p.setCursoid(u.getCursoid());
                p.setNumero(i);   
                if(i == u.getNumero())
                    commit = true;
                else
                    commit = false;
                dao.save(manager, commit, p);
                resultado.setResult(dao.get(p.getId())); 
            }
            return resultado;
    } 
}
