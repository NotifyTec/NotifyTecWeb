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
import br.com.notifytec.utils.DateParse;
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
    @Consumes("application/json")
    public void add(String inicio,
            String fim) {
        try {
            SemestreModel f = new SemestreModel();
            f.setId(UUID.randomUUID());
            f.setInicio(DateParse.toDate(inicio));
            f.setFim(DateParse.toDate(fim));
            Resultado r = semestreService.add(f);

            returnSuccess(r);
        } catch (Exception ex) {
            returnError(null, ex);
        }
    }

    @Post
    @Path("/remove")
    @Consumes("application/json")
    public void remove(String id) {
        try {
            Resultado r = semestreService.remove(UUID.fromString(id));
            returnSuccess(r);
        } catch (Exception ex) {
            returnError(null, ex);
        }
    }
    
    @Post
    @Path("/edit")
    @Consumes("application/json")
    public void edit(String id,
            String inicio,
            String fim) {
        try {            
            SemestreModel f = new SemestreModel();
            f.setId(UUID.fromString(id));
            f.setInicio(DateParse.toDate(inicio));
            f.setFim(DateParse.toDate(fim));
            Resultado r = semestreService.edit(f);

            returnSuccess(r);
        } catch (Exception ex) {
            returnError(null, ex);
        }
    }
}
