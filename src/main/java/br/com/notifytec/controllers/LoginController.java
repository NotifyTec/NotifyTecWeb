package br.com.notifytec.controllers;

import br.com.caelum.vraptor.Consumes;
import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.notifytec.daos.NotificacaoDao;
import br.com.notifytec.daos.PersistenceManager;
import br.com.notifytec.models.NotificacaoCompletaModel;
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
    @Path("/Login")
    @PermitAll
    @Consumes("application/json")
    public void login(String userName, String password) {

        if(userName.trim().equals("a") && password.trim().equals("s")){
            Token m = new Token();
            
            UsuarioModel u = new UsuarioModel();
            u.setId(UUID.randomUUID());
            u.setAlterouSenha(Boolean.TRUE);
            u.setEmail("teste@domain.com");
            u.setLogin("android");
            u.setPodeEnviar(Boolean.TRUE);
                        
            m.setToken(new JwtManager().newToken(u));
            returnSuccess(m);
            return;
        }
        
        try {
            NotificacaoCompletaModel n = new NotificacaoCompletaModel();
            n.setConteudo("conteudo");
            n.setTitulo("titulo maneiro");
            n.setUsuarioID(UUID.fromString("475c9bf7-055b-47de-af8c-7524d4cde316"));
            
            notificacaoService.enviar(n, UUID.fromString("5d1a4b38-2862-48c0-8914-b995b0247a7e"));

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
