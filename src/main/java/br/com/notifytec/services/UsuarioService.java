package br.com.notifytec.services;

import br.com.notifytec.daos.UsuarioDao;
import br.com.notifytec.models.UsuarioModel;
import br.com.notifytec.security.JwtManager;
import br.com.notifytec.security.MD5Manager;
import br.com.notifytec.security.UserSession;
import javax.inject.Inject;

public class UsuarioService {

    @Inject
    private JwtManager jwtManager;

    @Inject
    private UserSession userSession;
    
    @Inject
    private UsuarioDao usuarioDao;

    public UsuarioModel get(String login) {        
        return usuarioDao.get(login);
    }

    public UsuarioModel login(String login, String password) throws IllegalAccessException {        
        UsuarioModel userModel = get(login);

        if(userModel == null){
            throw new NullPointerException("Não há usuários com o login informado.");
        }
        
        if(!userModel.isAlterouSenha()){
            throw new IllegalAccessException("Você ainda não alterou sua senha padrão. Altere-a pelo aplicativo para prosseguir com o acesso ao NotifyTec pela web.");
        }
        
        if (!userModel.getSenha().equals(MD5Manager.generate(password))) {
            throw new IllegalAccessException("A senha não confere.");
        }

        userModel.setToken(jwtManager.newToken(userModel));
        userSession.setUser(userModel);
            
        return userModel;
    }
    
    
}
