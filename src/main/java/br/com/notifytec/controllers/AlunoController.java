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
import br.com.notifytec.models.AlunoModel;
import br.com.notifytec.models.PeriodoSemestre;
import br.com.notifytec.services.AlunoService;
import br.com.notifytec.services.CursoService;
import br.com.notifytec.services.SemestreService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;
import java.util.List;
import java.lang.reflect.Type;
import java.util.UUID;
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
    @Path("/add")
    @Consumes("application/json")
    public void add(String nome,
                    String sobrenome,
                    String ra,
                    String cpf,
                    String email,
                    boolean ativo,
                    String periodos
                    ){
        try {
            Type tipo = new TypeToken<ArrayList<PeriodoSemestre>>(){}.getType();
            List<PeriodoSemestre> listaPeriodo = new Gson().fromJson(periodos, tipo);
            AlunoModel aluno = new AlunoModel();
            aluno.setNome(nome);
            aluno.setAtivo(ativo);
            aluno.setSobrenome(sobrenome);
            aluno.setCpf(cpf);
            aluno.setEmail(email);
            aluno.setRa(ra);
            returnSuccess(alunoService.addAlunoService(aluno,listaPeriodo));
        } catch (Exception e) {
            returnError(null,e);
        }
    }
    
    @Post
    @Path("/edit")
    @Consumes("application/json")
    public void edit(String id,
                    String nome,
                    String sobrenome,
                    String ra,
                    String cpf,
                    String email,
                    boolean ativo,
                    String usuarioID,
                    String periodos
                    ){
        try {
            Type tipo = new TypeToken<ArrayList<PeriodoSemestre>>(){}.getType();
            List<PeriodoSemestre> listaPeriodo = new Gson().fromJson(periodos, tipo);
            AlunoModel aluno = new AlunoModel();
            aluno.setId(UUID.fromString(id));
            aluno.setNome(nome);
            aluno.setAtivo(ativo);
            aluno.setSobrenome(sobrenome);
            aluno.setCpf(cpf);
            aluno.setEmail(email);
            aluno.setRa(ra);
            aluno.setUsuarioId(UUID.fromString(usuarioID));
            returnSuccess(alunoService.edit(aluno,listaPeriodo));
        } catch (Exception e) {
            returnError(null,e);
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
