/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Exception;

/**
 * Apresenta uma exceçao quando o cadastrar um produto com codigo ja exisntente.
 * @author Leonardo Veloso
 */
public class ProdutoExistenteException extends Exception{
    
    public ProdutoExistenteException(String m){
        super(m);
    }
    
    
    
}
