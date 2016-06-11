/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.notifytec.daos;

import br.com.notifytec.models.CursoModel;
import br.com.notifytec.models.Parametros;

/**
 *
 * @author felip
 */
public class CursoDao extends CrudDao<CursoModel>{
     public CursoDao() {
        super(CursoModel.class, Parametros.Tabelas.TABELA_CURSO);
    }
    
}
