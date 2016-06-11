/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.notifytec.services;

import br.com.notifytec.daos.SemestreDao;
import br.com.notifytec.models.Resultado;
import br.com.notifytec.models.ResultadoPaginacao;
import br.com.notifytec.models.SemestreModel;
import java.util.List;
import javax.inject.Inject;

/**
 *
 * @author Bruno
 */
public class SemestreService {
    @Inject
    private SemestreDao dao;
    
    public ResultadoPaginacao<SemestreModel> get(int pagina) {
        return dao.paginated(pagina);
    }
    
    public List<SemestreModel> getList(){;
        return dao.get();
    }
    
    public Resultado<SemestreModel> validarCamposObrigatorios(SemestreModel f) {
        Resultado<SemestreModel> r = new Resultado<>();

        if (f.getInicio() == null) {
            r.addError(getMensagemNulo("inicio"));
        }
        else if (f.getFim() == null) {
            r.addError(getMensagemNulo("fim"));
        }
        return r;
    }

    private String getMensagemNulo(String campo) {
        return "O campo " + campo + " não pode ser nulo.";
    }

    public Resultado<SemestreModel> add(SemestreModel f) {
        Resultado<SemestreModel> r = new Resultado<>();

        r.merge(validarCamposObrigatorios(f));

        if (!r.isSucess()) {
            r.setResult(f);
            return r;
        }      
        dao.save(f);
        r.setResult(dao.get(f.getId()));
        return r;
    }
    
    public Resultado<SemestreModel> edit(SemestreModel f){
        Resultado<SemestreModel> r = new Resultado<>();
        r.merge(validarCamposObrigatorios(f));
        if(!r.isSucess()){
            r.setResult(f);
            return r;
        }
        dao.editar(f);
        r.setResult(dao.get(f.getId()));
        return r;
    }
    
}
