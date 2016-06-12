package br.com.notifytec.services;

import br.com.notifytec.daos.AlunoDao;
import br.com.notifytec.daos.CrudDao;
import br.com.notifytec.models.AlunoModel;
import br.com.notifytec.models.Parametros;
import br.com.notifytec.models.ResultadoPaginacao;
import br.com.notifytec.models.UsuarioModel;
import java.util.UUID;
import javax.inject.Inject;

public class AlunoService extends CrudService<AlunoModel>{
    
    @Inject
    private UsuarioService usuarioService;
    @Inject
    private AlunoDao dao;
    public AlunoService() {
        super(new CrudDao(AlunoModel.class, Parametros.Tabelas.TABELA_ALUNO));
    }
    
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
    
    public ResultadoPaginacao<AlunoModel> getList(int pagina) {
        ResultadoPaginacao<AlunoModel> r = new ResultadoPaginacao<>();
        r = dao.paginated(pagina);     
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
    
    
    
    
    @Override
    public AlunoModel get(UUID id){
        AlunoModel aluno = super.get(id);        
        preencher(aluno);        
        return aluno;
    }
    
    public AlunoModel preencher(AlunoModel aluno){        
        aluno.setUsuario(usuarioService.get(aluno.getUsuarioId()));        
        return aluno;
    }
    
}
