package model.produto;

import java.sql.SQLException;
import model.entidades.Produtos;

/**
 *
 * @author Leonardo Veloso
 */
public class ProdutoBO {
    
    
    private ProdutoDAO dao;
    public ProdutoBO(){
        dao = new ProdutoDAO();
    }
    /**
     * Faz as verificações de negocio e manda salvar no banco de dados
     * 
     * @param p 
     */
    
    public void salvar(Produtos p) throws SQLException{
        
        // Lógica de Negocios de salvar produtos
        //verifica se existe um produto com o mesmo codigo.
        if( dao.buscarPeloCodigo(p.getCodigo()) != null ){
            //TODO lançar exceção
            //mensagem que já existe o produto com o codigo
        }else{
            //mandar salvar no banco de dados
            dao.salvar(p);
        }
        
    }
}
