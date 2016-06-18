package br.com.notifytec.daos;

import br.com.notifytec.models.AlunoModel;
import br.com.notifytec.models.FuncionarioModel;
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
                manager.createQuery("from ALUNO where CPF like :cpf")
                        .setParameter("cpf", cpf).getResultList();
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
    
}
