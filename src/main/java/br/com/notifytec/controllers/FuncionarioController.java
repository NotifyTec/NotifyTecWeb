package br.com.notifytec.controllers;

import br.com.caelum.vraptor.Consumes;
import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.notifytec.models.FuncionarioModel;
import br.com.notifytec.models.Resultado;
import br.com.notifytec.security.UserSession;
import br.com.notifytec.services.DepartamentoService;
import br.com.notifytec.services.FuncionarioService;
import java.util.UUID;
import javax.annotation.security.PermitAll;
import javax.inject.Inject;

@Controller
@Path("/Funcionario")
public class FuncionarioController extends BaseController {

    @Inject
    private FuncionarioService funcionarioService;
    @Inject
    private DepartamentoService departamentoService;

    @Post
    @Path("/getList")    
    @Consumes("application/json")
    public void getList(int numeroPagina) {
        try {
            returnSuccess(funcionarioService.get(numeroPagina));
        } catch (Exception ex) {
            returnError(null, ex);
        }
    }
    
    @Post
    @Path("/getDepartamentos")    
    @Consumes("application/json")  
    public void getDepartamentos(String pagina){
        try {            
            returnSuccess(departamentoService.get(Integer.parseInt(pagina)));
        }catch(Exception ex){
            returnError(null, ex);
        }

    }
    
    
   
    @Post
    @Path("/add")
    @Consumes("application/json")
    public void add(String nome,
                    String sobrenome,
                    String cpf,
                    String apelido,
                    String departamentoId,
                    boolean ativo,
                    String email){
        try{
            FuncionarioModel f = new FuncionarioModel();
            f.setNome(nome);
            f.setSobrenome(sobrenome);
            f.setCpf(cpf);
            f.setApelido(apelido);
            f.setDepartamentoId(UUID.fromString(departamentoId));
            f.setEmail(email);
            
            Resultado r = funcionarioService.add(f);
            
            returnSuccess(r);
        }catch(Exception ex){
            returnError(null, ex);
        }
    }
}
