package model.produto;

import Exception.ProdutoExistenteException;
import java.sql.SQLException;
import java.util.ArrayList;
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
    
    public void salvar(Produtos p) throws SQLException, ProdutoExistenteException{
        
        // Lógica de Negocios de salvar produtos
        //verifica se existe um produto com o mesmo codigo.
        if( dao.buscarPeloCodigo(p.getCodigo()) != null ){
            //TODO lançar exceção
            //mensagem que já existe o produto com o codigo
            throw new ProdutoExistenteException("Produto cadastrado com código ja existente!");
            
        }else{
            //mandar salvar no banco de dados
            dao.salvar(p);
        }
        
    }
    
    public ArrayList<Produtos> listar() throws SQLException{
        
        return dao.listar();
        
    }
}
