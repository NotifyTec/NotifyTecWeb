/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.notifytec.controllers;
import br.com.caelum.vraptor.Consumes;
import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.notifytec.models.DepartamentoModel;
import br.com.notifytec.models.Resultado;
import br.com.notifytec.services.DepartamentoService;
import java.util.UUID;
import javax.annotation.security.PermitAll;
import javax.inject.Inject;

/**
 *
 * @author felip
 */
@Controller
@Path("/Departamento")
public class DepartamentoController extends BaseController{
    @Inject
    private DepartamentoService departamentoService;
   

    @Post
    @Path("/getList")
    @Consumes("application/json")
    public void getList(int numeroPagina) {
        try {
            returnSuccess(departamentoService.get(numeroPagina));
        } catch (Exception ex) {
            returnError(null, ex);
        }
    }
    
    @Post
    @Path("/remove")
    @Consumes("application/json")
    public void remove(String id){
        try {
            UUID ID = UUID.fromString(id);
            returnSuccess(departamentoService.remove(ID));
        } catch (Exception e) {
            returnError(null,e);
        }
    }
    
    @Post
    @Path("/add")
    @Consumes("application/json")
    public void add(String nome){
        try{
            DepartamentoModel f = new DepartamentoModel();
            f.setId(UUID.randomUUID());
            f.setNome(nome);
            Resultado r = departamentoService.add(f);
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
                    String nome){
         try{
            DepartamentoModel f = new DepartamentoModel();
            f.setId(UUID.fromString(id));
            f.setNome(nome);
            Resultado r = departamentoService.edit(f);
            returnSuccess(r);
        }catch(Exception ex){
            returnError(null, ex);
        }
    }
}
