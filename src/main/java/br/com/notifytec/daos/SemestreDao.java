/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.notifytec.daos;

import br.com.notifytec.models.Parametros;
import br.com.notifytec.models.SemestreModel;
import java.math.BigInteger;
import javax.persistence.EntityManager;

/**
 *
 * @author Bruno
 */
public class SemestreDao extends CrudDao<SemestreModel> {

    public SemestreDao() {
        super(SemestreModel.class, Parametros.Tabelas.TABELA_SEMESTRE);
    }

    public boolean verificaIntercalado(SemestreModel f) {
        EntityManager manager = open();
        BigInteger count = (BigInteger) manager.createNativeQuery("SELECT \n"
                + "	COUNT(*) \n"
                + "FROM \n"
                + "	SEMESTRE x \n"
                + "WHERE  \n"
                + "	((x.INICIO > :inicio AND x.INICIO < :fim) OR\n"
                + "    (x.FIM > :inicio AND x.FIM < :fim) OR\n"
                + "    (x.INICIO <= :inicio AND x.FIM >= :fim)) AND"
                + "     x.ID != :id")
                .setParameter("inicio", f.getInicio())
                .setParameter("fim", f.getFim())
                .setParameter("id", f.getId())
                .getSingleResult();
        close(manager);

        return count.intValue() > 0;
    }
}
