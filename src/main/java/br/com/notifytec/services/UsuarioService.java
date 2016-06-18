package br.com.notifytec.services;

import br.com.notifytec.daos.CrudDao;
import br.com.notifytec.daos.PeriodoDao;
import br.com.notifytec.daos.UsuarioDao;
import br.com.notifytec.models.CursoModel;
import br.com.notifytec.models.Parametros;
import br.com.notifytec.models.Resultado;
import br.com.notifytec.models.Transacao;
import br.com.notifytec.models.UsuarioModel;
import br.com.notifytec.security.JwtManager;
import br.com.notifytec.security.MD5Manager;
import br.com.notifytec.security.UserSession;
import java.util.Calendar;
import java.util.UUID;
import javax.inject.Inject;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.SimpleEmail;

public class UsuarioService {

    @Inject
    private JwtManager jwtManager;

    @Inject
    private UserSession userSession;

    @Inject
    private UsuarioDao dao;

    public UsuarioModel updateGcm(String gcm, UUID usuarioID) {
        UsuarioModel u = dao.get(usuarioID);
        u.setGcmToken(gcm);
        dao.editar(u);

        return u;
    }

    public UsuarioModel get(UUID usuarioID) {
        return dao.get(usuarioID);
    }
    
    public UsuarioModel get(String login) {
        return ((UsuarioDao) dao).get(login);
    }

    private void validarNovaSenha(String novaSenha) throws Exception {
        if (novaSenha == null || novaSenha.isEmpty()) {
            throw new Exception("A senha não pode ser nula.");
        }
    }

    public UsuarioModel redefinirSenha(String novaSenha, UUID usuarioID) throws Exception {
        validarNovaSenha(novaSenha);

        UsuarioModel usuario = dao.get(usuarioID);
        usuario.setSenha(MD5Manager.generate(novaSenha));
        usuario.setAlterouSenha(true);
        dao.editar(usuario);

        return usuario;
    }

    public UsuarioModel esqueceuSenha(String login) throws Exception {
        UsuarioModel u = dao.get(login);

        if (u == null) {
            throw new Exception("Usuário não existe.");
        }

        String token = UUID.randomUUID().toString().substring(0, 5);
        u.setTokenRecuperarSenha(token);

        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH, 7);

        u.setDataValidadeToken(c.getTime());

        try {
            Email e = new SimpleEmail();
            e.setSubject("[NotifyTec] Redefinição de senha.");
            e.setMsg("Insira o token no aplicativo para redefinir a senha: " + token + ".");
            e.setHostName("smtp.gmail.com");
            e.setSmtpPort(587);
            e.setAuthenticator(new DefaultAuthenticator("notifytec.suporte@gmail.com", "notifytec!@#$%"));
            e.setFrom("notifytec.suporte@gmail.com");
            e.setSSLOnConnect(true);
            e.addTo(u.getEmail());
            e.send();

        } catch (Exception ex) {
            throw new Exception("Não foi possível enviar o token para seu e-mail.");
        }

        dao.editar(u);

        u.setTokenRecuperarSenha(null);
        return u;
    }

    public UsuarioModel confirmarEsqueceuSenha(String token, String novaSenha) throws Exception {
        UsuarioModel u = dao.getByTokenDeRedefinirSenha(token);

        if (u == null) {
            throw new Exception("Token inválido.");
        }

        validarNovaSenha(novaSenha);

        if (u.getDataValidadeToken().before(Calendar.getInstance().getTime())) {
            throw new Exception("Token expirou.");
        }

        u.setSenha(MD5Manager.generate(novaSenha));
        u.setTokenRecuperarSenha(null);
        u.setDataValidadeToken(null);
        u.setAlterouSenha(Boolean.TRUE);
        dao.editar(u);

        return u;
    }

    public UsuarioModel login(String login, String password) throws IllegalAccessException {
        UsuarioModel userModel = get(login);

        if (userModel == null) {
            throw new NullPointerException("Não há usuários com o login informado.");
        }

        if (!userModel.getSenha().equals(MD5Manager.generate(password))) {
            throw new IllegalAccessException("A senha não confere.");
        }

        userModel.setToken(jwtManager.newToken(userModel));
        userSession.setUser(userModel);

        return userModel;
    }

    public Resultado<Transacao<UsuarioModel>> add(UsuarioModel u) {
        //Resultado<Transacao<UsuarioModel>> resultado = new Resultado<>();

        String senhaMD5 = MD5Manager.generate(u.getSenha());
        u.setSenha(senhaMD5);
        u.setAlterouSenha(Boolean.FALSE);
        u.setId(UUID.randomUUID());

        // TODO: VALIDAR EMAIL!!!
        Resultado<Transacao<UsuarioModel>> transacao = new Resultado<>();
        Transacao em = new Transacao();
        em = dao.save(false, u);
        em.setResultado(dao.get(u.getId()));
        transacao.setResult(em);
        return transacao;
    }

}
