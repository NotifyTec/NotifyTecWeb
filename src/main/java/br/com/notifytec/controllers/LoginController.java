package br.com.notifytec.controllers;

import br.com.caelum.vraptor.Consumes;
import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.notifytec.daos.NotificacaoDao;
import br.com.notifytec.daos.PersistenceManager;
import br.com.notifytec.models.Token;
import br.com.notifytec.models.UsuarioModel;
import br.com.notifytec.security.UserSession;
import br.com.notifytec.services.UsuarioService;
import java.util.UUID;
import javax.annotation.security.PermitAll;
import javax.inject.Inject;

@Controller
@Path("/Login")
public class LoginController extends BaseController {

    @Inject
    private UsuarioService userService;

    @Inject
    UserSession userSession;

    @Post
    @Path("/Login")
    @PermitAll
    @Consumes("application/json")
    public void login(String userName, String password) {

        try {            
            if (userName == null || password == null) {
                returnError("O usuário e a senha devem ser informadas.");
                return;
            } 

            UsuarioModel userModel = userService.login(userName, password);

            Token tokenModel = new Token();
            tokenModel.setToken(userModel.getToken());

            returnSuccess(tokenModel);
        } catch (Exception ex) {
            ex.printStackTrace();
            returnError(null, ex.getMessage());
        }
    }

}
