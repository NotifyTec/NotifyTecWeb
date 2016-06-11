/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.notifytec.daos;

import br.com.notifytec.models.Parametros;
import br.com.notifytec.models.SemestreModel;

/**
 *
 * @author Bruno
 */
public class SemestreDao extends CrudDao<SemestreModel> {
     public SemestreDao() {
        super(SemestreModel.class, Parametros.Tabelas.TABELA_SEMESTRE);
    }
}
