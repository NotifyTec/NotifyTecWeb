/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.notifytec.services;

import br.com.notifytec.models.Resultado;
import br.com.notifytec.models.ResultadoPaginacao;
import br.com.notifytec.daos.DepartamentoDao;
import br.com.notifytec.models.DepartamentoModel;
import java.util.List;
import javax.inject.Inject;

/**
 *
 * @author felip
 */
public class DepartamentoService {
    
    @Inject
    private DepartamentoDao dao;  

    public ResultadoPaginacao<DepartamentoModel> get(int pagina) {
        return dao.paginated(pagina);
    }
    
    public List<DepartamentoModel> getList(){
        return dao.get();
    }

    public Resultado<DepartamentoModel> validarCamposObrigatorios(DepartamentoModel f) {
        Resultado<DepartamentoModel> r = new Resultado<>();

        if (f.getNome() == null || f.getNome().isEmpty()) {
            r.addError(getMensagemNulo("nome"));
        }
        return r;
    }

    private String getMensagemNulo(String campo) {
        return "O campo " + campo + " não pode ser nulo.";
    }

    public Resultado<DepartamentoModel> add(DepartamentoModel f) {
        Resultado<DepartamentoModel> r = new Resultado<>();

        r.merge(validarCamposObrigatorios(f));

        if (!r.isSucess()) {
            r.setResult(f);
            return r;
        }      
        dao.save(f);
        r.setResult(dao.get(f.getId()));
        return r;
    }
}