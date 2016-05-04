package br.com.notifytec.services;

import br.com.notifytec.models.UserModel;
import br.com.notifytec.security.JwtManager;
import br.com.notifytec.security.MD5Manager;
import br.com.notifytec.security.UserSession;
import javax.inject.Inject;

public class UserService {

    @Inject
    private JwtManager jwtManager;

    @Inject
    private UserSession userSession;

    public UserModel get(String login) {
        //TODO: pegar o usuario pelo banco;
        UserModel user = new UserModel();
        user.setSenha(MD5Manager.generate(login));
        
        return user;
    }

    public UserModel login(String login, String password) throws IllegalAccessException {        
        UserModel userModel = get(login);

        if(!userModel.isAlterouSenha()){
            throw new IllegalAccessException("Você ainda não alterou sua senha padrão. Altere-a pelo aplicativo para prosseguir com o acesso ao NotifyTec pela web.");
        }
        
        if (!userModel.getSenha().equals(MD5Manager.generate(password))) {
            throw new IllegalAccessException("A senha não confere.");
        }

        userModel.setToken(jwtManager.newToken(userModel.getId()));
        userSession.setUser(userModel);
            
        return userModel;
    }
}
