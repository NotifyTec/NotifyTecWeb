package br.com.notifytec.services;

import br.com.notifytec.daos.AlunoDao;
import br.com.notifytec.models.AlunoModel;
import br.com.notifytec.models.AlunoPeriodoModel;
import br.com.notifytec.models.FuncionarioModel;
import br.com.notifytec.models.PeriodoSemestre;
import br.com.notifytec.models.Resultado;
import br.com.notifytec.models.ResultadoPaginacao;
import br.com.notifytec.models.Transacao;
import br.com.notifytec.models.UsuarioModel;
import java.util.List;
import java.util.UUID;
import javax.inject.Inject;

public class AlunoService{
    
    @Inject
    private UsuarioService usuarioService;    
    @Inject
    private AlunoPeriodoService alunoPeriodoService;
    @Inject
    private AlunoDao dao;

   
    
     public ResultadoPaginacao<AlunoModel> getByFilter(String nome, String ra, String cpf, String email, boolean ativo){
        ResultadoPaginacao<AlunoModel> r = new ResultadoPaginacao<>();
        r = dao.getByFilter(nome,ra,cpf,email,ativo);        
        UsuarioModel u = new UsuarioModel();
        for(AlunoModel f : r.getResult() ){
             if(f.isAtivo())
                f.setAtivoTraduzido("Ativo");
            else
                f.setAtivoTraduzido("Inativo"); 
            u = usuarioService.getById(f.getUsuarioId());
            f.setEmail(u.getEmail());            
        }        
        return r;
    }
     
     
    public Resultado<AlunoModel> validarCamposObrigatorios(AlunoModel f) {
        Resultado<AlunoModel> r = new Resultado<>();

        if (f.getNome() == null || f.getNome().isEmpty()) {
            r.addError(getMensagemNulo("nome"));
        }

        if (f.getNome() == null || f.getNome().isEmpty()) {
            r.addError(getMensagemNulo("sobrenome"));
        }

        if (f.getCpf() == null || f.getCpf().isEmpty()) {
            r.addError(getMensagemNulo("CPF"));
        }

        return r;
    }

    private String getMensagemNulo(String campo) {
        return "O campo " + campo + " não pode ser nulo.";
    }
    
    private Resultado<AlunoModel> verificaSeJaTemCPF(String cpf){
        Resultado<AlunoModel> r = new Resultado<>();
        List<AlunoModel> lista =  dao.getByCPF(cpf);
        if(lista.size() > 0){
          r.addError("Já Existe um aluno cadastrado com o CPF informado!");
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
    
    public Resultado<AlunoModel> edit(AlunoModel aluno, List<PeriodoSemestre> list){
        Resultado<AlunoModel> r = new Resultado<>();
        
        r.merge(validarCamposObrigatorios(aluno));
        //r.merge(verificaSeJaTemCPF(f.getCpf()));
        //r.merge(verificaSeJaTemEmail(f.getEmail()));
        
        if (!r.isSucess()) {
            r.setResult(aluno);
            return r;
        }
        alunoPeriodoService.edit(list, aluno.getId());
        dao.editar(aluno);
        r.setResult(dao.get(aluno.getId()));
        return r;
        
    }
    
    
    
     public Resultado<AlunoModel> addAlunoService(AlunoModel f, List<PeriodoSemestre> list){
        Resultado<AlunoModel> r = new Resultado<>();
        
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
        //n?o da o commit ainda pois ha altera??o na tabela alunoperiodo ainda...
        dao.save(transacao.getResult().getEntityManager(), false, f);
        
        boolean commit = false;
        for(int i = 0; i< list.size(); i++){
            AlunoPeriodoModel p = new AlunoPeriodoModel();
            p.setID(UUID.randomUUID());
            p.setAlunoID(f.getId());
            p.setPeriodoID(UUID.fromString(list.get(i).getIdperiodo()));
            p.setSemestreID(UUID.fromString(list.get(i).getIssemestre()));     
            if(i == list.size() -1)
                commit = true;
            alunoPeriodoService.addAlunoPeriodo(transacao.getResult().getEntityManager(),commit, p);
        } 
        r.setResult(dao.get(f.getId()));
        return r;
    }
     
    private UsuarioModel gerarUsuario(AlunoModel f) {
        UsuarioModel u = new UsuarioModel();
        u.setEmail(f.getEmail());
        u.setLogin(f.getCpf());
        u.setPodeEnviar(Boolean.FALSE);
        u.setSenha(f.getCpf());
        return u;
    }
     
    
    public ResultadoPaginacao<AlunoModel> getList(int pagina) {
        ResultadoPaginacao<AlunoModel> r = new ResultadoPaginacao<>();
        r = dao.paginated(pagina);     
        UsuarioModel u = new UsuarioModel();
        
        for(AlunoModel f : r.getResult() ){           
            List<AlunoPeriodoModel> listaPeriodos = alunoPeriodoService.getListPeriodo(f.getId());
            f.setPeriodoSemestre(listaPeriodos);
            if(f.isAtivo())
                f.setAtivoTraduzido("Ativo");
            else
                f.setAtivoTraduzido("Inativo");        
            u = usuarioService.getById(f.getUsuarioId());
            f.setEmail(u.getEmail());           
        }
        return r;
    }
    

    
   
    public AlunoModel get(UUID id){
        AlunoModel aluno = dao.get(id);        
        preencher(aluno);        
        return aluno;
    }
    
    public AlunoModel preencher(AlunoModel aluno){        
        aluno.setUsuario(usuarioService.get(aluno.getUsuarioId()));        
        return aluno;
    }
    
}
