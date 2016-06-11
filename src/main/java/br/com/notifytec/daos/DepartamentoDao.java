/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.notifytec.daos;

import br.com.notifytec.models.DepartamentoModel;
import br.com.notifytec.models.Parametros;

/**
 *
 * @author felip
 */
public class DepartamentoDao extends CrudDao<DepartamentoModel>{
    public DepartamentoDao(){
        super(DepartamentoModel.class, Parametros.Tabelas.TABELA_DEPARTAMENTO);
    }
}
