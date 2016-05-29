package br.com.notifytec.models;

import java.util.List;

public class ResultadoPaginacao<T> extends Resultado<List<T>> {    
    private long pagina;
    private long registrosPorPagina;
    private long totalDeRegistros;

    public long getPagina() {
        return pagina;
    }

    public void setPagina(long pagina) {
        this.pagina = pagina;
    }

    public long getRegistrosPorPagina() {
        return registrosPorPagina;
    }

    public void setRegistrosPorPagina(long registrosPorPagina) {
        this.registrosPorPagina = registrosPorPagina;
    }

    public long getTotalDeRegistros() {
        return totalDeRegistros;
    }

    public void setTotalDeRegistros(long totalDeRegistros) {
        this.totalDeRegistros = totalDeRegistros;
    }
    
}
