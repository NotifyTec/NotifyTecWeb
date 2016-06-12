package br.com.notifytec.daos;

import br.com.notifytec.models.Parametros;
import br.com.notifytec.models.UsuarioModel;
import java.util.UUID;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

public class UsuarioDao extends CrudDao<UsuarioModel> {

    @Inject
    private EntityManager manager;

    public UsuarioDao() {
        super(UsuarioModel.class, Parametros.Tabelas.TABELA_USUARIO);
    }

    public UsuarioModel getByTokenDeRedefinirSenha(String token){
        try{
            UsuarioModel usuario = manager.
                    createQuery("from USUARIO where TOKENRECUPERARSENHA = ?", UsuarioModel.class)
                    .setParameter(1, token)
                    .getSingleResult();
            return usuario;
        }catch(NoResultException ex){
            return null;
        }
    }
    
    public UsuarioModel get(String login) {
        try {
            UsuarioModel usuario = manager.
                    createQuery("from USUARIO where LOGIN = ?", UsuarioModel.class)
                    .setParameter(1, login)
                    .getSingleResult();
            
            return usuario;
        } catch (NoResultException ex) {
            return null;
        }
    }

}
