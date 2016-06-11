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
        for(FuncionarioModel f : r.getResult() ){
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

    public Resultado<FuncionarioModel> add(FuncionarioModel f) {
        Resultado<FuncionarioModel> r = new Resultado<>();

        r.merge(validarCamposObrigatorios(f));

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
