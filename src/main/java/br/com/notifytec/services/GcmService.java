package br.com.notifytec.services;

import br.com.notifytec.models.NotificacaoCompletaModel;
import br.com.notifytec.models.NotificacaoModel;
import br.com.notifytec.models.Resultado;
import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.MulticastResult;
import com.google.android.gcm.server.Sender;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GcmService {

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
    public String getAuthorizationKey() {
        // TODO: acessar o banco e pegar a chave de autorização.
        return "key=";
    }

    private byte[] getDataBinary(NotificacaoModel notificacao) throws UnsupportedEncodingException {
        return "".getBytes("UTF-8");
    }

    public Resultado send(NotificacaoCompletaModel notificacao, List<String> tokens) {
        Resultado resultado = new Resultado();

        try {
            Sender sender = new Sender(getAuthorizationKey());
            Message message = new Message.Builder()
                    .addData("notificacao", new Gson().toJson(notificacao))
                    .build();

            MulticastResult result = sender.send(message, tokens, 0);
            if (result.getFailure() > 0) {
                for (com.google.android.gcm.server.Result e : result.getResults()) {
                    String erro = e.getErrorCodeName();
                    if (erro != null && !erro.isEmpty()) {
                        resultado.addError(erro);
                    }
                }
            }

        } catch (IOException ex) {
            Logger.getLogger(GcmService.class.getName()).log(Level.SEVERE, null, ex);
            resultado.addError(MENSAGEM_DE_ERRO_AO_ENVIAR);
        }

        return resultado;
    }
}
