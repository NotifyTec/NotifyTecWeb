package br.com.notifytec.models;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Transient;

@Entity(name = "NOTIFICACAO")
public class NotificacaoCompletaModel extends NotificacaoModel {

    @Transient
    private int totalAlunosEnviados;
    @Transient
    private int totalAlunosVisualizados;
    @Transient
    private int totalRespondidos;
    @Transient
    private AlunoNotificacaoModel resposta;

    public AlunoNotificacaoModel getResposta() {
        return resposta;
    }

    public void setResposta(AlunoNotificacaoModel resposta) {
        this.resposta = resposta;
    }

    public int getTotalAlunosEnviados() {
        return totalAlunosEnviados;
    }

    public void setTotalAlunosEnviados(int totalAlunosEnviados) {
        this.totalAlunosEnviados = totalAlunosEnviados;
    }

    public int getTotalAlunosVisualizados() {
        return totalAlunosVisualizados;
    }

    public void setTotalAlunosVisualizados(int totalAlunosVisualizados) {
        this.totalAlunosVisualizados = totalAlunosVisualizados;
    }

    public int getTotalRespondidos() {
        return totalRespondidos;
    }

    public void setTotalRespondidos(int totalRespondidos) {
        this.totalRespondidos = totalRespondidos;
    }
}
