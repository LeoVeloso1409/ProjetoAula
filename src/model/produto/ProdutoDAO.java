package model.produto;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import model.conexao.ConnectionFactory;
import model.entidades.Produtos;

/**
 *
 * @author Leonardo Veloso
 */
public class ProdutoDAO {
    
    /**
     * Buscar um produto pelo código
     * @param c Código
     * @return 
     * @throws SQLException 
     */
    public Produtos buscarPeloCodigo(String c) throws SQLException{
        
        //Comando
        String sql = "SELECT * FROM produto WHERE codProduto = ?";
        
        //Preparar o SQL
        PreparedStatement ps = ConnectionFactory.prepararSQL(sql);
        
        //Substituir os parametros
        ps.setString(1, c);
        
        //Executa consulta no bd
        ResultSet resultado = ps.executeQuery();
        
        //Verificar se tem algum resultado
        if(resultado.next()){
            //Cria o objeto com o resultado do BD
            
            Produtos p = new Produtos(
                    resultado.getInt("idProduto"),
                    resultado.getString("nomeProduto"),
                    resultado.getDouble("precoprodutoo"),
                    resultado.getString("codProduto"),
                    resultado.getDouble("quantidade"),
                    LocalDate.parse(resultado.getDate("validade").toString())
            );
            
            return p;
        }
        
        return null;
    }
    
    public void salvar(Produtos p) throws SQLException{
        String sql = "INSERT INTO Produto (nomeProduto, precoProdutoo, codProduto, quantidade, validade)"
                +"VALUES (?, ?, ?, ?, ?)";
        
        PreparedStatement ps = ConnectionFactory.prepararSQL(sql);
        
        //Substituir os valores
        ps.setString(1, p.getNome());
        ps.setDouble(2, p.getPreco());
        ps.setString(3, p.getCodigo());
        ps.setDouble(4, p.getQuantidade());
        ps.setString(5, p.getValidade().toString());
        
        //Executar o comando no banco de dados
        ps.executeUpdate();
        
        //fechar a conexao
        ps.close();
    }
    
    /**
     * Retorna todos os produtos do banco de dados
     * @return 
     */
    
     public ArrayList<Produtos> listar() throws SQLException{
        
        //Comando
        String sql = "SELECT * FROM produto";
        
        //Preparar o SQL
        PreparedStatement ps = ConnectionFactory.prepararSQL(sql);
        
        //Executa consulta no bd
        ResultSet resultado = ps.executeQuery();
        
        //Criando a lista
        ArrayList<Produtos> lista = new ArrayList<Produtos>();
        
        //Enquanto tiver resultado no BD
        while(resultado.next()){
            
            //Cria o produto a partir do resultado do banco
            Produtos p = new Produtos(
                    resultado.getInt("idProduto"),
                    resultado.getString("nomeProduto"),
                    resultado.getDouble("precoProdutoo"),
                    resultado.getString("codProduto"),
                    resultado.getDouble("quantidade"),
                    LocalDate.parse(resultado.getDate("validade").toString())
            );
            
            //adiciona o resultado na lista
            lista.add(p);
            
        }//while
        
        return lista; 
    }
    
    public void excluir(Produtos p) throws SQLException{
        //Comando SQL
        String sql = "DELETE FROM Produto WHERE idProduto = ?";
        //Preparar o SQL
        PreparedStatement ps = ConnectionFactory.prepararSQL(sql);
        
        //Substituir os valores
        ps.setInt(1, p.getId());
       
        //Executar o comando no banco de dados
        ps.executeUpdate();
        
        //fechar a conexao
        ps.close();
    }
    
     public void editar(Produtos p) throws SQLException{
        
        //Comando SQL
        String sql = "UPDATE Produto SET nomeProduto=?, codProduto=?, precoProdutoo=?, quantidade=?, validade=? WHERE idProduto=?";
        
        //Preparar o SQL
        PreparedStatement ps = ConnectionFactory.prepararSQL(sql);
        
        //Substituir os valores
        ps.setString(1, p.getNome());
        ps.setString(2, p.getCodigo());
        ps.setDouble(3, p.getPreco());
        ps.setDouble(4, p.getQuantidade());
        ps.setString(5, p.getValidade().toString());
        ps.setInt(6, p.getId());
        
        //Executar o comando no banco de dados
        ps.executeUpdate();
        
        //fechar a conexao
        ps.close();
         
    }
     /**
     * Buscar um produto pelo código
     * @param p Código e Nome
     * @return 
     * @throws SQLException 
     */
     
     public ArrayList<Produtos> filtrarPeloCodigo(String pesquisa) throws SQLException{
        
        //Comando
        String sql = "SELECT * FROM produto WHERE codProduto LIKE ?";
        
        return filtrar(pesquisa, sql);
        
    }
    
    public ArrayList<Produtos> filtrarPeloNome(String pesquisa) throws SQLException{
        
        //Comando
        String sql = "SELECT * FROM produto WHERE nomeProduto LIKE ?";
        
        return filtrar(pesquisa, sql);
        
    }
     
    public ArrayList<Produtos> filtrar(String pesquisa, String sql) throws SQLException {
    
        //Preparar o SQL
        PreparedStatement ps = ConnectionFactory.prepararSQL(sql);
        
        //Substituir os parametros
        ps.setString(1, "%" + pesquisa + "%");
        
        //Executa consulta no bd
        ResultSet resultado = ps.executeQuery();
        
        //Criar a lista
        ArrayList<Produtos> lista = new ArrayList<Produtos>();
        
        //Verificar se tem algum resultado
        while(resultado.next()){
            //Cria o objeto com o resultado do BD
            
            Produtos p = new Produtos(
                    resultado.getInt("idProduto"),
                    resultado.getString("nomeProduto"),
                    resultado.getDouble("precoProdutoo"),
                    resultado.getString("codProduto"),
                    resultado.getDouble("quantidade"),
                    LocalDate.parse(resultado.getDate("validade").toString())
            );
            
            //Adiciono o produto na lista
            lista.add(p);
            
        }
        
        return lista;
        
    }
}