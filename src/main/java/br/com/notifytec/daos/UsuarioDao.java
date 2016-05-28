package br.com.notifytec.daos;

import br.com.notifytec.models.UsuarioModel;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

public class UsuarioDao {

    @Inject
    private EntityManager manager;

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
