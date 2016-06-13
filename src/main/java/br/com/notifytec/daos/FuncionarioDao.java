package br.com.notifytec.daos;

import br.com.notifytec.models.FuncionarioModel;
import br.com.notifytec.models.Parametros;
import br.com.notifytec.models.ResultadoPaginacao;
import br.com.notifytec.models.UsuarioModel;
import java.util.List;
import java.util.UUID;
import javax.persistence.Query;

public class FuncionarioDao extends CrudDao<FuncionarioModel> {

    public FuncionarioDao() {
        super(FuncionarioModel.class, Parametros.Tabelas.TABELA_PESSOA);
    }
    
    
    public List<FuncionarioModel> getByCPF(String cpf){
        return 
                manager.createQuery("from PESSOA where CPF like :cpf")
                        .setParameter("cpf", cpf).getResultList();
    }
    
    public List<UsuarioModel> getByEmail(String email){
        return 
                manager.createQuery("from USUARIO where EMAIL like :email")
                        .setParameter("email", email).getResultList();
    }
    
    public FuncionarioModel getByUsuario(UUID usuarioID){
        return (FuncionarioModel)manager.createNativeQuery("SELECT\n" +
"	f.*\n" +
"FROM\n" +
"	PESSOA f\n" +
"WHERE\n" +
"	f.USUARIOID = :usuarioid", FuncionarioModel.class)
                .setParameter("usuarioid", usuarioID)
                .getSingleResult();
    }
    
    public ResultadoPaginacao<FuncionarioModel> getByFilter(String nome, boolean ativo){
          ResultadoPaginacao<FuncionarioModel> resultado = new ResultadoPaginacao<FuncionarioModel>();


        String jpql = "SELECT count(o) FROM " + "PESSOA" + " o";
        Query q = manager.createQuery(jpql);
        Long totalDeRegistros = (Long) q.getSingleResult();

        int limiteInicial = 0;

        List<FuncionarioModel> registros = manager.createQuery("from PESSOA where (NOME like :nome or :nome = '') and ATIVO = :ativo order by NOME asc")
                .setParameter("nome", nome)
                .setParameter("ativo",ativo)
                .setFirstResult(limiteInicial)
                .setMaxResults(100).getResultList();

        resultado.setTotalDeRegistros(totalDeRegistros);
        resultado.setResult(registros);
        resultado.setPagina(1);
        resultado.setRegistrosPorPagina(100);

        return resultado; 
    }
    
}
