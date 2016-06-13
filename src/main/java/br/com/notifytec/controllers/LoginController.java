package br.com.notifytec.controllers;

import br.com.caelum.vraptor.Consumes;
import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.notifytec.daos.NotificacaoDao;
import br.com.notifytec.daos.PersistenceManager;
import br.com.notifytec.models.NotificacaoCompletaModel;
import br.com.notifytec.models.Resultado;
import br.com.notifytec.models.Token;
import br.com.notifytec.models.UsuarioModel;
import br.com.notifytec.security.JwtManager;
import br.com.notifytec.security.UserSession;
import br.com.notifytec.services.NotificacaoService;
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

    @Inject
    private NotificacaoService notificacaoService;

    @Post
    @Path("/LoginApp")
    @PermitAll
    @Consumes("application/json")
    public void loginApp(String userName, String password) {
        try {            
            UsuarioModel u = userService.login(userName, password);
            returnSuccess(u);            
        } catch (Exception ex) {
            returnError(ex);
        }
    }

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
            
            if(!userModel.isAlterouSenha()){
                throw new IllegalAccessException("Você ainda não alterou sua senha padrão. Altere-a pelo aplicativo para prosseguir com o acesso ao NotifyTec pela web.");
            }
            
            Token tokenModel = new Token();
            tokenModel.setToken(userModel.getToken());

            returnSuccess(tokenModel);
        } catch (Exception ex) {
            ex.printStackTrace();
            returnError(null, ex.getMessage());
        }
    }
    
    @Post
    @Path("/RedefinirSenha")    
    @Consumes("application/json")
    public void redefinirSenha(String novaSenha, String usuarioID) {
        try {
            UsuarioModel usuario = userService.redefinirSenha(novaSenha, UUID.fromString(usuarioID));
            returnSuccess(usuario);
        } catch (Exception ex) {
            returnError(ex);
        }
    }
    
    @Post
    @Path("/EsqueceuSenha")
    @PermitAll
    @Consumes("application/json")
    public void esqueceuSenha(String login) {
        try {
            UsuarioModel usuario = userService.esqueceuSenha(login);
            returnSuccess(usuario);
        } catch (Exception ex) {
            returnError(ex);
        }
    }

    @Post
    @Path("/ConfirmarEsqueceuSenha")
    @PermitAll
    @Consumes("application/json")
    public void confirmarEsqueceuSenha(String token, String novaSenha) {
        try {
            UsuarioModel usuario = userService.confirmarEsqueceuSenha(token, novaSenha);
            returnSuccess(usuario);
        } catch (Exception ex) {
            returnError(ex);
        }
    }
    
    @Post
    @Path("/UpdateGcm")    
    @Consumes("application/json")
    public void updateGcm(String gcm, String usuarioID) {
        try {
            UsuarioModel usuario = userService.updateGcm(gcm, UUID.fromString(usuarioID));
            returnSuccess(usuario);
        } catch (Exception ex) {
            returnError(ex);
        }
    }
}
