package br.com.notifytec.daos;

import br.com.notifytec.models.AlunoModel;
import br.com.notifytec.models.Parametros;
import br.com.notifytec.models.ResultadoPaginacao;
import br.com.notifytec.models.UsuarioModel;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

public class AlunoDao extends CrudDao<AlunoModel>{
    
    public AlunoDao() {
        super(AlunoModel.class, Parametros.Tabelas.TABELA_ALUNO);
    }
    
    public ResultadoPaginacao<AlunoModel> getByFilter(String nome, String ra, String cpf, String email, boolean ativo){
        EntityManager manager = open();
        ResultadoPaginacao<AlunoModel> resultado = new ResultadoPaginacao<AlunoModel>();
        String jpql = "SELECT count(o) FROM " + "ALUNO" + " o";
        Query q = manager.createQuery(jpql);
        Long totalDeRegistros = (Long) q.getSingleResult();
        int limiteInicial = 0;

        List<AlunoModel> registros = manager.createQuery("from ALUNO where (NOME like :nome or :nome = '') and  (RA like :ra or :ra = '') and (CPF like :cpf or :cpf = '') and  ATIVO = :ativo order by NOME asc")
                .setParameter("nome", "%" + nome + "%")
                .setParameter("ra", "%" + ra + "%")
                .setParameter("cpf","%" + cpf + "%")    
                .setParameter("ativo",ativo)
                .setFirstResult(limiteInicial)
                .setMaxResults(100).getResultList();

        resultado.setTotalDeRegistros(totalDeRegistros);
        resultado.setResult(registros);
        resultado.setPagina(1);
        resultado.setRegistrosPorPagina(100);
        close(manager);
        return resultado; 
    }
    
     public List<AlunoModel> getByCPF(String cpf){
         EntityManager manager = open();
        List<AlunoModel> l = 
                manager.createQuery("from ALUNO where CPF like :cpf AND ATIVO = true")
                        .setParameter("cpf", cpf).getResultList();
        close(manager);
        return l;
    }
     
    public List<AlunoModel> getByRA(String ra){
         EntityManager manager = open();
        List<AlunoModel> l = 
                manager.createQuery("from ALUNO where RA like :ra AND ATIVO = true")
                        .setParameter("ra", ra).getResultList();
        close(manager);
        return l;
    }
    
    public List<UsuarioModel> getByEmail(String email){
        EntityManager manager = open();
        List<UsuarioModel> l =  
                manager.createQuery("from USUARIO where EMAIL like :email")
                        .setParameter("email", email).getResultList();
        close(manager);
        return l;
    }
    
    public ResultadoPaginacao<AlunoModel> paginacao (int page){
        EntityManager manager = open();
        ResultadoPaginacao<AlunoModel> resultado = new ResultadoPaginacao<AlunoModel>();

        if (page <= 0) {
            page = 1;
        }

        String jpql = "SELECT count(o) FROM " + "ALUNO" + " o";
        Query q = manager.createQuery(jpql);
        Long totalDeRegistros = (Long) q.getSingleResult();

        int limiteInicial = (page - 1) * 25;

        List<AlunoModel> registros = manager.createQuery("from " + "ALUNO order by NOME asc")
                .setFirstResult(limiteInicial)
                .setMaxResults(25).getResultList();

        resultado.setTotalDeRegistros(totalDeRegistros);
        resultado.setResult(registros);
        resultado.setPagina(page);
        resultado.setRegistrosPorPagina(25);
        close(manager);
        return resultado;
    }
    
}
