package br.com.notifytec.services;

import br.com.notifytec.models.FirebaseMessage;
import br.com.notifytec.models.FirebaseNotification;
import br.com.notifytec.models.FirebaseResponse;
import br.com.notifytec.models.NotificacaoCompletaModel;
import br.com.notifytec.models.NotificacaoModel;
import br.com.notifytec.models.Resultado;
import com.google.gson.Gson;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FirebaseService {

    private final String GOOGLE_CLOUD_MESSAGING_SEND_LINK = "https://gcm-http.googleapis.com/gcm/send";
    private final String MENSAGEM_DE_ERRO_AO_ENVIAR = "Não foi possível enviar a notificação. Tente novamente.";

    /*
    https://gcm-http.googleapis.com/gcm/send
    Content-Type:application/json
    Authorization:key=AIzaSyZ-1u...0GBYzPu7Udno5aA

    { "data": {
        "score": "5x1",
        "time": "15:10"
      },
      "to" : "bk3RNwTe3H0:CI2k_HHwgIpoDKCIZvvDMExUdFQ3P1..."
    }
     */

    private byte[] getDataBinary(NotificacaoModel notificacao) throws UnsupportedEncodingException {
        return "".getBytes("UTF-8");
    }

    public Resultado send(NotificacaoCompletaModel notificacao, List<String> tokens) {
        Resultado resultado = new Resultado();
        
        try{
            RestService<FirebaseResponse> rest = new RestService<FirebaseResponse>(FirebaseResponse.class);

            FirebaseMessage m = new FirebaseMessage();
            m.setData(notificacao);
            m.setRegistration_ids(tokens);

            FirebaseNotification n = new FirebaseNotification();
            n.setTitle(notificacao.getTitulo());
            n.setBody(notificacao.getConteudo());
            n.setIcon("ic_principal.png");

            m.setNotification(n);

            FirebaseResponse r = rest.execute(m);            
            resultado.addWarning("Sucesso para " + r.getSuccess() + " dispositivos. Falha para " + r.getFailure() + " dispositivos.");            
        }catch(Exception ex){
            resultado.addError(ex.getMessage());
        }
        
        return resultado;
    }
}
