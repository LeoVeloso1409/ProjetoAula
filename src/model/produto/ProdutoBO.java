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
    
    public void excluir(Produtos p) throws SQLException{
        //Mandar o dao excluir
        dao.excluir(p);
    }
    
    public void editar(Produtos p) throws SQLException, ProdutoExistenteException{
        
        //Verificar se existe o codigo do produto e
        //se o codigo é diferente desse mesmo produto
        
        Produtos aux = dao.buscarPeloCodigo(p.getCodigo());
        
        //Verificar se existe um produto com o mesmo código
        if( (aux != null) && (aux.getId() != p.getId())  ){
         
           //mensagem que já existe o produto com o codigo
            throw new ProdutoExistenteException(" Já "
                    + "existe produto cadastrado com mesmo"
                    + "código");
            
        }
        else{ //pode editar o produto
            
            //manda editar no banco de dados
            dao.editar(p);
        }
    }
    
     public ArrayList<Produtos> filtrarPeloCodigo(String pesquisa) throws SQLException{
        
        //Buscar no banco de dados
        return dao.filtrarPeloCodigo(pesquisa);
        
    }
    
    public ArrayList<Produtos> filtrarPeloNome(String pesquisa) throws SQLException{
        
        //Buscar no banco de dados
        return dao.filtrarPeloNome(pesquisa);
        
    }
}
