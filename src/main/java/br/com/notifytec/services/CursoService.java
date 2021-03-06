/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.notifytec.services;

import br.com.notifytec.daos.CrudDao;
import br.com.notifytec.daos.CursoDao;
import br.com.notifytec.daos.FuncionarioDao;
import br.com.notifytec.daos.PeriodoDao;
import br.com.notifytec.models.AlunoModel;
import br.com.notifytec.models.CursoModel;
import br.com.notifytec.models.FuncionarioModel;
import br.com.notifytec.models.Parametros;
import br.com.notifytec.models.PeriodoModel;
import br.com.notifytec.models.Resultado;
import br.com.notifytec.models.ResultadoPaginacao;
import br.com.notifytec.models.Transacao;
import br.com.notifytec.models.UsuarioModel;
import java.util.List;
import java.util.UUID;
import javax.inject.Inject;

/**
 *
 * @author felip
 */
public class CursoService {    
    @Inject
    private PeriodoService periodoService;
    @Inject
    private CursoDao dao;
    
    public CursoService() {   
        
    }

    public List<CursoModel> get(){
        return dao.get();
    }
    
    public CursoModel getByPeriodo(UUID periodoID){
        return dao.getByPeriodo(periodoID);
    }
    
    public ResultadoPaginacao<CursoModel> get(int pagina) {
        ResultadoPaginacao<CursoModel> list = dao.paginated(pagina);
        for(CursoModel m : list.getResult()){
            m.setPeriodo(periodoService.getByCurso(m.getId()).size());
            if(m.isAtivo())
                m.setAtivoTraduzido("Ativo");
            else
                m.setAtivoTraduzido("Inativo");
        }
        return list;
    }

    public Resultado<CursoModel> validarCamposObrigatorios(CursoModel f) {
        Resultado<CursoModel> r = new Resultado<>();

        if (f.getNome() == null || f.getNome().isEmpty()) {
            r.addError(getMensagemNulo("nome"));
        }
        return r;
    }
    
    public Resultado<CursoModel> verificaSeJaTemComNome(String nome){
         Resultado<CursoModel> r = new Resultado<>();
        List<CursoModel> lista =  dao.getByNome(nome);
        if(lista.size() > 0){
          r.addError("Já Existe um curso cadastrado com o nome informado!");
        }
        return r;
    }

    private String getMensagemNulo(String campo) {
        return "O campo " + campo + " não pode ser nulo.";
    }
    
    public Resultado<CursoModel> edit(CursoModel f, int qtdPeriodos){
        Resultado<CursoModel> r = new Resultado<>();
        r.merge(validarCamposObrigatorios(f));
        if(!r.isSucess()){
            r.setResult(f);
            return r;
        }
        r.merge(periodoService.editar(f.getId(),qtdPeriodos));
        dao.editar(f);
        r.setResult(dao.get(f.getId()));
        return r;
    }
    public List<CursoModel>getListCursoPeriodo(){
        List<CursoModel> lista = dao.get();
        for(CursoModel m : lista){
            m.setListPeriodo(periodoService.getByCurso(m.getId()));
            for(PeriodoModel p : m.getListPeriodo()){
                if(m.getApelido() != null)
                    p.setCursoNome(p.getNumero() + " º " + m.getApelido());
                else
                    p.setCursoNome(p.getNumero() + " º " + m.getNome());
            }
        }
        return lista;
    }
    
    
    
    public Resultado<CursoModel> add(CursoModel f, int qtdPeriodo) {
        f.setId(UUID.randomUUID());     
        Resultado<CursoModel> r = new Resultado<>();
        r.merge(validarCamposObrigatorios(f));
        r.merge(verificaSeJaTemComNome(f.getNome()));
        if (!r.isSucess()) {
            r.setResult(f);
            return r;
        }
        Resultado<Transacao<CursoModel>> transacao = new Resultado<>();       
        Transacao em = new Transacao();
        em = dao.save(false, f);
        em.setResultado(dao.get(f.getId()));     
        transacao.setResult(em);        
        PeriodoModel p = new PeriodoModel();
        p.setCursoid(f.getId());
        p.setId(UUID.randomUUID());
        p.setNumero(qtdPeriodo);        
        r.merge(periodoService.add(transacao.getResult().getEntityManager(), p));
        return r;
    }
}
