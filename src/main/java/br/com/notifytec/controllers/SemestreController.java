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
import br.com.notifytec.models.Resultado;
import br.com.notifytec.models.SemestreModel;
import br.com.notifytec.services.SemestreService;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import javax.annotation.security.PermitAll;
import javax.inject.Inject;

/**
 *
 * @author Bruno
 */
@Controller
@Path("/Semestre")
public class SemestreController extends BaseController {
    
    @Inject
    private SemestreService semestreService;

    @Post
    @Path("/getList")
    @PermitAll
    @Consumes("application/json")
    public void getList(int numeroPagina) {
        try {
            returnSuccess(semestreService.get(numeroPagina));
        } catch (Exception ex) {
            returnError(null, ex);
        }
    }
    
    @Post
    @Path("/add")
    @PermitAll
    @Consumes("application/json")
    public void add(String inicio,
                    String fim){
        try{
            Date dtInicio, dtFim;
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            dtInicio = df.parse(inicio);
            dtFim = df.parse(fim);          
            
            SemestreModel f = new SemestreModel();
            f.setId(UUID.randomUUID());
            f.setInicio(dtInicio);
            f.setFim(dtFim);
            Resultado r = semestreService.add(f);
            
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
                    String inicio,
                    String fim){
         try{
            System.out.println("TESTE /edit String Inicio = " + inicio);
            System.out.println("TESTE /edit String Fim = " + fim);
            
            Date dtInicio, dtFim;
            DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
            dtInicio = df.parse(inicio);
            dtFim = df.parse(fim);
            
            System.out.println("TESTE /edit Date Inicio = " + dtInicio);
            System.out.println("TESTE /edit Date Fim = " + dtFim);
             
            SemestreModel f = new SemestreModel();
            f.setId(UUID.fromString(id));
            f.setInicio(dtInicio);
            f.setFim(dtFim);
            Resultado r = semestreService.edit(f);
            
            returnSuccess(r);
        }catch(Exception ex){
            returnError(null, ex);
        }
    }
}
