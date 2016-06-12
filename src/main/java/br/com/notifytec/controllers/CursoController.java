package br.com.notifytec.controllers;

import br.com.caelum.vraptor.Consumes;
import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.notifytec.models.CursoModel;
import br.com.notifytec.models.Resultado;
import br.com.notifytec.services.CursoService;
import br.com.notifytec.services.PeriodoService;
import java.util.UUID;
import javax.annotation.security.PermitAll;
import javax.inject.Inject;

@Controller
@Path("/Curso")
public class CursoController extends BaseController {

    @Inject
    private CursoService cursoService;
    @Inject
    private PeriodoService periodoService;

    @Post
    @Path("/getList")
    @PermitAll
    @Consumes("application/json")
    public void getList(int numeroPagina) {
        try {
            returnSuccess(cursoService.get(numeroPagina));
        } catch (Exception ex) {
            returnError(null, ex);
        }
    }
    
    @Post
    @Path("/getCursos")
    @PermitAll
    @Consumes("application/json")
    public void getList() {
        try {
            returnSuccess(cursoService.get());
        } catch (Exception ex) {
            returnError(null, ex);
        }
    }
    
    @Post
    @Path("/getListPeriodo")
    @PermitAll
    @Consumes("application/json")
    public void getPeriodoList(UUID cursoID){
        try {
            returnSuccess(periodoService.get(cursoID));
        } catch (Exception e) {
            returnError(null,e);
        }
    }
    
    
    @Post
    @Path("/add")
    @PermitAll
    @Consumes("application/json")
    public void add(String nome,
                    String apelido,
                    boolean ativo,
                    int periodo){
        try{
            CursoModel f = new CursoModel();
            f.setNome(nome);
            f.setApelido(apelido);
            f.setAtivo(ativo);
            Resultado r = cursoService.add(f, periodo);
            
            returnSuccess(r);
        }catch(Exception ex){
            returnError(null, ex);
        }
    }
    @Post
    @Path("/edit")
    @PermitAll
    @Consumes("application/json")
    public void edit(String id,
                    String nome,
                    String apelido,
                    boolean ativo,
                    int periodo){
         try{
            CursoModel f = new CursoModel();
            f.setId(UUID.fromString(id));
            f.setNome(nome);
            f.setApelido(apelido);
            f.setAtivo(ativo);
            Resultado r = cursoService.edit(f, periodo);
            
            returnSuccess(r);
        }catch(Exception ex){
            returnError(null, ex);
        }
    }
    
    
}
