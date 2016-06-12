package br.com.notifytec.controllers;

import br.com.caelum.vraptor.Consumes;
import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.notifytec.models.AlunoNotificacaoModel;
import br.com.notifytec.models.NotificacaoCompletaModel;
import br.com.notifytec.models.NotificacaoModel;
import br.com.notifytec.models.NotificacaoOpcaoModel;
import br.com.notifytec.services.NotificacaoService;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;
import javax.inject.Inject;

@Controller
@Path("/Notificacao")
public class NotificacaoController extends BaseController {

    @Inject
    private NotificacaoService notificacaoService;

    private List<NotificacaoCompletaModel> listar(boolean recebidos){
        List<NotificacaoCompletaModel> l = new ArrayList<>();
        
        NotificacaoCompletaModel n1 = new NotificacaoCompletaModel();
        n1.setConteudo("Churrasco!!! Com pizza!!!");
        n1.setDataHoraEnvio(Calendar.getInstance().getTime());
        n1.setId(UUID.randomUUID());
        n1.setTitulo("AVISO: EBAAA!!! Churrasco tudo pago.");
        n1.setTotalAlunosEnviados(40);
        n1.setTotalAlunosVisualizados(25);
        n1.setUsuarioID(UUID.randomUUID());
                                
        l.add(n1);
        
        NotificacaoCompletaModel n2 = new NotificacaoCompletaModel();
        n2.setConteudo("Dia?? Decidam.");
        n2.setDataHoraEnvio(Calendar.getInstance().getTime());
        n2.setId(UUID.randomUUID());
        n2.setTitulo("ENQUETE: Que dia o churras?");
        n2.setTotalAlunosEnviados(40);
        n1.setTotalAlunosVisualizados(25);
        n2.setUsuarioID(UUID.randomUUID());
        
        List<NotificacaoOpcaoModel> ops = new ArrayList();
        NotificacaoOpcaoModel op1 = new NotificacaoOpcaoModel();
        op1.setId(UUID.randomUUID());
        op1.setNome("Terça-feira");
        op1.setNotificacaoID(n2.getId());
        op1.setTotalRespondidos(5);
        ops.add(op1);
        
        NotificacaoOpcaoModel op2 = new NotificacaoOpcaoModel();
        op2.setId(UUID.randomUUID());
        op2.setNome("Quarta-feira");
        op2.setNotificacaoID(n2.getId());
        op2.setTotalRespondidos(6);
        ops.add(op2);
        
        n2.setOpcoes(ops);        
        n2.setTotalRespondidos(11);
        
        if(recebidos){
            AlunoNotificacaoModel r = new AlunoNotificacaoModel();
            r.setAlunoID(UUID.randomUUID());
            r.setId(UUID.randomUUID());
            r.setNotificacaoID(n2.getId());
            r.setNotificacaoOpcao(op1.getId());
            r.setVisualizouEm(Calendar.getInstance().getTime());
            
            n2.setResposta(r);
        }
        
        l.add(n2);
        
        return l;
    }
    
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
    @Get()
    @Consumes("application/json")
    public void responder(UUID opcaoID, UUID alunoID) {
        try {
            returnSuccess(notificacaoService.responder(opcaoID, alunoID));
        } catch (Exception ex) {
            returnError(ex);
        }
    }

    @Path("/enviar")
    @Get()
    @Consumes("application/json")
    public void enviar(NotificacaoCompletaModel notificacao, UUID periodoID) {
        try {
            returnSuccess(notificacaoService.enviar(notificacao, periodoID));
        } catch (Exception ex) {
            returnError(ex);
        }
    }
}
