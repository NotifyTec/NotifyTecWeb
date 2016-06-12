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
import br.com.notifytec.services.AlunoService;
import br.com.notifytec.services.CursoService;
import br.com.notifytec.services.SemestreService;
import javax.inject.Inject;

/**
 *
 * @author felip
 */
@Controller
@Path("/Aluno")
public class AlunoController extends BaseController{
    @Inject
    private AlunoService alunoService;
    @Inject
    private CursoService cursoService;
    @Inject
    private SemestreService semestreService;
    
    
    @Post
    @Path("/getList")    
    @Consumes("application/json")
    public void getList(int numeroPagina) {
        try {
            returnSuccess(alunoService.getList(numeroPagina));
        } catch (Exception ex) {
            returnError(null, ex);
        }
    }
    
    @Post
    @Path("/getByFilter")
    @Consumes("application/json")
    public void getByFilter(String nome, String ra, String cpf, String email, boolean ativo){
        try {
            returnSuccess(alunoService.getByFilter(nome,ra,cpf,email,ativo));
        } catch (Exception e) {
            returnError(null,e);
        }  
    }
    
    @Post
    @Path("/getPeriodos")    
    @Consumes("application/json")  
    public void getPeriodos(boolean ativo){
        try {            
            returnSuccess(cursoService.getListCursoPeriodo());
        }catch(Exception ex){
            returnError(null, ex);
        }
    }
    @Post
    @Path("/getSemestre")
    @Consumes("application/json")
    public void getSemestre(boolean ativo){
        try {
            returnSuccess(semestreService.get(1));
        } catch (Exception e) {
            returnError(null,e);
        }
    } 
    
}
