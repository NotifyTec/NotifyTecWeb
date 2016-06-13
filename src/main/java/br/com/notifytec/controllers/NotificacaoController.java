package br.com.notifytec.controllers;

import br.com.caelum.vraptor.Consumes;
import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.view.Results;
import br.com.notifytec.models.AlunoNotificacaoModel;
import br.com.notifytec.models.NotificacaoCompletaModel;
import br.com.notifytec.models.NotificacaoModel;
import br.com.notifytec.models.NotificacaoOpcaoModel;
import br.com.notifytec.models.Resultado;
import br.com.notifytec.services.NotificacaoService;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.annotation.security.PermitAll;
import javax.inject.Inject;

@Controller
@Path("/Notificacao")
public class NotificacaoController extends BaseController {

    @Inject
    private NotificacaoService notificacaoService;
    
    @Path("/get")
    @Post()
    @Consumes("application/json")
    public void get(String usuarioID) {
        try{
            returnSuccess(notificacaoService.getPorUsuario(UUID.fromString(usuarioID)));
        }catch(Exception ex){
            returnError(ex);
        }
    }

    @Path("/responder")
    @Post()
    @Consumes("application/json")
    public void responder(String notificacaoID, String notificacaoOpcaoID, String usuarioID) {
        try {
            returnSuccess(notificacaoService.responder(
                            UUID.fromString(notificacaoID), 
                            UUID.fromString(notificacaoOpcaoID), 
                            UUID.fromString(usuarioID)));
        } catch (Exception ex) {
            returnError(ex);
        }
    }

    @Path("/enviar")
    @Post()
    @PermitAll
    @Consumes("application/json")
    public void enviar(String periodoID, 
                       String titulo, 
                       String conteudo, 
                       String expiraEm, 
                       String usuarioID, 
                       String opcoes) {
        try {
            NotificacaoCompletaModel n = new NotificacaoCompletaModel();
            n.setTitulo(titulo);
            n.setConteudo(conteudo);
            n.setUsuarioID(UUID.fromString(usuarioID));
            
            if(expiraEm != null && !expiraEm.isEmpty()){
                n.setExpiraEm(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").parse(expiraEm));
            }          
            
            String[] ops = new String[]{ };
            if(opcoes != null && !opcoes.isEmpty()){
                ops = opcoes.split("¬");
            }
            
            List<NotificacaoOpcaoModel> opcoesN = new ArrayList<>();            
            for(String s : ops){
                NotificacaoOpcaoModel o=new NotificacaoOpcaoModel();
                o.setNome(s);
                opcoesN.add(o);
            }
            n.setOpcoes(opcoesN);
                        
            n.setPeriodoID(UUID.fromString(periodoID));
            
            Resultado<NotificacaoCompletaModel> r = 
                    notificacaoService.enviar(n);
            
            result.use(Results.json()).withoutRoot().from(r).recursive().serialize();
        } catch (Exception ex) {
            returnError(ex);
        }
    }
}
