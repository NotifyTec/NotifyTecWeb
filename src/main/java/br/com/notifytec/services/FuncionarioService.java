package br.com.notifytec.services;

import br.com.notifytec.daos.FuncionarioDao;
import br.com.notifytec.models.DepartamentoModel;
import br.com.notifytec.models.FuncionarioModel;
import br.com.notifytec.models.Parametros;
import br.com.notifytec.models.Resultado;
import br.com.notifytec.models.ResultadoPaginacao;
import br.com.notifytec.models.Transacao;
import br.com.notifytec.models.UsuarioModel;
import java.util.List;
import java.util.UUID;
import javax.inject.Inject;

public class FuncionarioService {

    @Inject
    private FuncionarioDao dao;
    @Inject
    private UsuarioService usuarioService;
    @Inject
    private DepartamentoService departamentoService;
    
    public ResultadoPaginacao<FuncionarioModel> get(int pagina) {
        ResultadoPaginacao<FuncionarioModel> r = new ResultadoPaginacao<>();
        r = dao.paginated(pagina);
        List<DepartamentoModel> departamentos = departamentoService.getList();
        UsuarioModel u = new UsuarioModel();
        for(FuncionarioModel f : r.getResult() ){            
            u = usuarioService.get(f.getUsuarioId());
            f.setEmail(u.getEmail());
            for(DepartamentoModel d: departamentos){
                if(f.getDepartamentoId().equals(d.getId()))
                    f.setNomedepartamento(d.getNome());
            }
        }
        
        
        return r;
    }
    
    public ResultadoPaginacao<FuncionarioModel> getByFilter(String nome, boolean ativo){
        ResultadoPaginacao<FuncionarioModel> r = new ResultadoPaginacao<>();
        r = dao.getByFilter(nome,ativo);
        List<DepartamentoModel> departamentos = departamentoService.getList();
        UsuarioModel u = new UsuarioModel();
        for(FuncionarioModel f : r.getResult() ){
            u = usuarioService.get(f.getUsuarioId());
            f.setEmail(u.getEmail());
            for(DepartamentoModel d: departamentos){
                if(f.getDepartamentoId().equals(d.getId()))
                    f.setNomedepartamento(d.getNome());
            }
        }
        
        return r;
    }
    

    public Resultado<FuncionarioModel> validarCamposObrigatorios(FuncionarioModel f) {
        Resultado<FuncionarioModel> r = new Resultado<>();

        if (f.getNome() == null || f.getNome().isEmpty()) {
            r.addError(getMensagemNulo("nome"));
        }

        if (f.getNome() == null || f.getNome().isEmpty()) {
            r.addError(getMensagemNulo("sobrenome"));
        }

        if (f.getCpf() == null || f.getCpf().isEmpty()) {
            r.addError(getMensagemNulo("CPF"));
        }

        if (f.getDepartamentoId() == null || f.getDepartamentoId() == Parametros.emptyUUID) {
            r.addError(getMensagemNulo("departamento"));
        }

        return r;
    }

    private String getMensagemNulo(String campo) {
        return "O campo " + campo + " não pode ser nulo.";
    }
    
    private Resultado<FuncionarioModel> verificaSeJaTemCPF(String cpf){
        Resultado<FuncionarioModel> r = new Resultado<>();
        List<FuncionarioModel> lista =  dao.getByCPF(cpf);
        if(lista.size() > 0){
          r.addError("Já Existe uma pessoa cadastrada com o CPF informado!");
        }
        return r;
    }
    private Resultado<FuncionarioModel> verificaSeJaTemEmail(String email){
        Resultado<FuncionarioModel> r = new Resultado<>();
        List<UsuarioModel> lista =  dao.getByEmail(email);
        if(lista.size()>0){
            r.addError("Já Existe uma pessoa cadastrada com o Email informado!");
        }
        return r;
    }
    
    public Resultado<FuncionarioModel> edit(FuncionarioModel f){
        Resultado<FuncionarioModel> r = new Resultado<>();        
        r.merge(validarCamposObrigatorios(f));
        if(!r.isSucess()){
            r.setResult(f);
            return r;
        }
        dao.editar(f);
        r.setResult(dao.get(f.getId()));
        return r;
    }
    

    public Resultado<FuncionarioModel> add(FuncionarioModel f) {
        Resultado<FuncionarioModel> r = new Resultado<>();

        r.merge(validarCamposObrigatorios(f));
        r.merge(verificaSeJaTemCPF(f.getCpf()));
        r.merge(verificaSeJaTemEmail(f.getEmail()));
        
        if (!r.isSucess()) {
            r.setResult(f);
            return r;
        }

        Resultado<Transacao<UsuarioModel>> transacao = usuarioService.add(gerarUsuario(f));
        if (!transacao.isSucess()) {
            r.merge(transacao);
            return r;
        }

        f.setUsuarioId(transacao.getResult().getResultado().getId());
        f.setId(UUID.randomUUID());

        dao.save(transacao.getResult().getEntityManager(), true, f);

        r.setResult(dao.get(f.getId()));

        return r;
    }

    private UsuarioModel gerarUsuario(FuncionarioModel f) {
        UsuarioModel u = new UsuarioModel();
        u.setEmail(f.getEmail());
        u.setLogin(f.getCpf());
        u.setPodeEnviar(Boolean.TRUE);
        u.setSenha(f.getCpf());

        return u;
    }
}
