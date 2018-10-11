/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.produto.cadastro;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import model.entidades.Produtos;
import model.produto.ProdutoBO;

/**
 * FXML Controller class
 *
 * @author Leonardo Veloso
 */
public class ProdutoCadastroController implements Initializable {

    @FXML
    private JFXTextField id;
    @FXML
    private JFXTextField codigo;
    @FXML
    private JFXTextField nome;
    @FXML
    private JFXTextField preco;
    @FXML
    private JFXTextField qtd;
    @FXML
    private JFXDatePicker validade;
    @FXML
    private JFXTextField pesquisar;
    @FXML
    private JFXComboBox<String> cboxFiltro;
    @FXML
    private TableView<Produtos> tabela;
    
    private ProdutoBO pBO;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        pBO = new ProdutoBO();
    }    

    @FXML
    private void btnSalvar(ActionEvent event) {
        
        double precoDouble = Double.parseDouble(preco.getText());
        double qtdDouble = Double.parseDouble(qtd.getText());
       
        // Pegar dados da tela Produtos
        Produtos p = new Produtos(
                0,
                nome.getText(),
                precoDouble,
                codigo.getText(),  
                qtdDouble,
                validade.getValue()
            );
        
        //Manda a classe de nogocio salvar o produto
        try{
            pBO.salvar(p);
        }
        catch(SQLException e){
            //TODO Colocar uma mensagen de erro
            e.printStackTrace();
        }
    }

    @FXML
    private void btnEditar(ActionEvent event) {
    }

    @FXML
    private void btnExcluir(ActionEvent event) {
    }
    
}
