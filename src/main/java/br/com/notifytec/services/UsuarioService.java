package br.com.notifytec.services;

import br.com.notifytec.daos.CrudDao;
import br.com.notifytec.daos.UsuarioDao;
import br.com.notifytec.models.Parametros;
import br.com.notifytec.models.Resultado;
import br.com.notifytec.models.Transacao;
import br.com.notifytec.models.UsuarioModel;
import br.com.notifytec.security.JwtManager;
import br.com.notifytec.security.MD5Manager;
import br.com.notifytec.security.UserSession;
import java.util.UUID;
import javax.inject.Inject;
import javax.persistence.EntityManager;

public class UsuarioService extends CrudService<UsuarioModel>{

    @Inject
    private JwtManager jwtManager;

    @Inject
    private UserSession userSession;
    
    public UsuarioService() {
        super(new UsuarioDao());
    }

    public UsuarioModel get(String login) {                
        return ((UsuarioDao)dao).get(login);
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

    public Resultado<Transacao<UsuarioModel>> add(UsuarioModel u) {
        Resultado<Transacao<UsuarioModel>> resultado = new Resultado<>();
        
        String senhaMD5 = MD5Manager.generate(u.getSenha());
        u.setSenha(senhaMD5);
        u.setAlterouSenha(Boolean.FALSE);        
        u.setId(UUID.randomUUID());
        
        // TODO: VALIDAR EMAIL!!!
        
        Transacao em = ((UsuarioDao)dao).save(false, u);
        em.setResultado(((UsuarioDao)dao).get(u.getId()));
        resultado.setResult(em);
        
        return resultado;
    }  
    
}
