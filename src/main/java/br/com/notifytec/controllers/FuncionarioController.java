package br.com.notifytec.controllers;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.notifytec.security.UserSession;
import br.com.notifytec.services.FuncionarioService;
import javax.inject.Inject;

@Controller
@Path("/Funcionario")
public class FuncionarioController extends BaseController {

    @Inject
    private FuncionarioService funcionarioService;

    @Get
    @Path("/getList")
    public void getList(int numeroPagina) {
        try {
            returnSuccess(funcionarioService.get(numeroPagina));
        } catch (Exception ex) {
            returnError(null, ex);
        }
    }
}
