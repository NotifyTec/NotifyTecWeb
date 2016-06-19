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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.UUID;
import javax.inject.Inject;

/**
 *
 * @author Bruno
 */
public class SemestreService {
    
    @Inject
    private SemestreDao dao;
    
    public ResultadoPaginacao<SemestreModel> get(int pagina) {
        ResultadoPaginacao<SemestreModel> resultado = new ResultadoPaginacao<>();
        resultado = dao.paginated(pagina);
        for (SemestreModel s : resultado.getResult()) {
            DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            s.setNomeSemestre(format.format(s.getInicio()) + " - " + format.format(s.getFim()));
        }
        return resultado;
    }
    
    public List<SemestreModel> getList() {
        return dao.get();
    }
    
    public Resultado remove(UUID semestreID) {
        Resultado r = new Resultado();
        try {
            dao.remover(semestreID);
        } catch (Exception ex) {
            r.addError("Não foi possível excluir pois o semestre está em uso.");
        }
        return r;
    }
    
    public Resultado<SemestreModel> validarCamposObrigatorios(SemestreModel f) {
        Resultado<SemestreModel> r = new Resultado<>();
        
        if (f.getInicio() == null) {
            r.addError(getMensagemNulo("inicio"));
        } else if (f.getFim() == null) {
            r.addError(getMensagemNulo("fim"));
        } else if (f.getInicio().after(f.getFim())) {
            r.addError("A data inicial deve ser anterior a data final.");
        } else if (dao.verificaIntercalado(f)) {
            r.addError("As datas estão intercaladas com outro semestre.");
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
    
    public Resultado<SemestreModel> edit(SemestreModel f) {
        Resultado<SemestreModel> r = new Resultado<>();
        r.merge(validarCamposObrigatorios(f));
        if (!r.isSucess()) {
            r.setResult(f);
            return r;
        }
        dao.editar(f);
        r.setResult(dao.get(f.getId()));
        return r;
    }
    
}
