package br.com.notifytec.services;

import br.com.notifytec.daos.AlunoDao;
import br.com.notifytec.daos.CrudDao;
import br.com.notifytec.models.AlunoModel;
import br.com.notifytec.models.Parametros;
import br.com.notifytec.models.UsuarioModel;
import java.util.UUID;
import javax.inject.Inject;

public class AlunoService extends CrudService<AlunoModel>{
    
    @Inject
    private UsuarioService usuarioService;
    
    public AlunoService() {
        super(new CrudDao(AlunoModel.class, Parametros.Tabelas.TABELA_ALUNO));
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
