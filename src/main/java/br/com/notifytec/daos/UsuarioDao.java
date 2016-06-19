package br.com.notifytec.daos;

import br.com.notifytec.models.CursoModel;
import br.com.notifytec.models.Parametros;
import br.com.notifytec.models.UsuarioModel;
import java.util.List;
import java.util.UUID;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

public class UsuarioDao extends CrudDao<UsuarioModel> {

    public UsuarioDao() {
        super(UsuarioModel.class, Parametros.Tabelas.TABELA_USUARIO);
    }

    public UsuarioModel getByTokenDeRedefinirSenha(String token){
        EntityManager manager = open();
        try{
            UsuarioModel usuario = manager.
                    createQuery("from USUARIO where TOKENRECUPERARSENHA = ?", UsuarioModel.class)
                    .setParameter(1, token)
                    .getSingleResult();
            close(manager);
            return usuario;
        }catch(NoResultException ex){
            close(manager);
            return null;
        }
    }
    
    public UsuarioModel get(String login) {
        EntityManager manager = open();
        try {
            UsuarioModel usuario = manager.
                    createQuery("from USUARIO where LOGIN = ?", UsuarioModel.class)
                    .setParameter(1, login)
                    .getSingleResult();
            close(manager);
            return usuario;
        } catch (NoResultException ex) {
            close(manager);
            return null;
        }
    }
    public boolean editarEmail(UUID id, String email){
        EntityManager manager = open();
        UsuarioModel l =
            manager.createQuery("from USUARIO where ID = ?",UsuarioModel.class)
                        .setParameter(1, id).getSingleResult();
        if(l.getEmail() == email){
            return false;
        }else{
            l.setEmail(email);
            editar(l);
            return true;
        } 
    }

}
