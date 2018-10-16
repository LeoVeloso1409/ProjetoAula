/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.produto.cadastro;

import Exception.ProdutoExistenteException;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
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
    
 //Dados da Tabela
    private ObservableList<Produtos> dados;
            
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        pBO = new ProdutoBO();
        
        configurarTabela();
        
        carregarDados();
        
    } 
    
    /**
     * Faz a configuração da tabela e das colunas
     */
    private void configurarTabela(){
        
        //Configurando as colunas da tabela
        TableColumn<Produtos, String> colNome = new TableColumn("Nome");
        TableColumn<Produtos, String> colCodigo = new TableColumn("Código");
        TableColumn<Produtos, Double> colPreco = new TableColumn("Preço");
        TableColumn<Produtos, Double> colQtde = new TableColumn("Quantidade");
        TableColumn<Produtos, String> colValidade = new TableColumn("Validade");

        //Configurar como os valores serão lidos (nome dos atributos)
        colNome.setCellValueFactory(new PropertyValueFactory<Produtos, String>("nome"));
        colCodigo.setCellValueFactory(new PropertyValueFactory<Produtos, String>("codigo"));
        colPreco.setCellValueFactory(new PropertyValueFactory<Produtos, Double>("preco"));
        colQtde.setCellValueFactory(new PropertyValueFactory<Produtos, Double>("quantidade"));
        colValidade.setCellValueFactory(new PropertyValueFactory<Produtos, String>("validade"));
        
        //Adiciona as colunas na tabela na ordem que devem aparecer
        tabela.getColumns().addAll(colCodigo, colNome, colPreco, 
                colQtde, colValidade);
        
    }
    
    
    /**
     * Vai carregar os dados na tabela
     */
    private void carregarDados(){
        
        try {
            //Cnvertendo o ArrayList no ObservableList com os dados do Banco
            dados = FXCollections.observableArrayList(pBO.listar());
            
            //Joga os dados na tabela para exibir
            tabela.setItems(dados);
            
        } catch (SQLException ex) {
            //Mensagem de Erro
            ex.printStackTrace();
        }
        
    }
    @FXML
    private void btnSalvar(ActionEvent event) {
        
        //double precoDouble = Double.parseDouble(preco.getText());
        //double qtdDouble = Double.parseDouble(qtd.getText());
       
        // Pegar dados da tela Produtos
       
        //Manda a classe de nogocio salvar o produto
        try{
             Produtos p = new Produtos(
                0,
                nome.getText(),
                preco.getText(),
                codigo.getText(),  
                qtd.getText(),
                validade.getValue()
            );
                    
            pBO.salvar(p);
            
            // Atualizando os dados da tabela
            carregarDados();
            
            //Limpando os dados apos salvar
            limparCampos();
            
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setTitle("SUCESSO");
            a.setHeaderText(null);
            a.setContentText("Produto Salvo com Sucesso!");
            a.showAndWait();
            
        }
        catch(SQLException e){
            //TODO Colocar uma mensagen de erro
            e.printStackTrace();
            
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("ERRO");
            a.setHeaderText(null);
            a.setContentText("Erro de Comunicação com o Banco de Dados.");
            a.showAndWait();
        }
        catch(ProdutoExistenteException e){
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("ERRO");
            a.setHeaderText(null);
            a.setContentText(e.getMessage());
            a.showAndWait();
        } catch (ParseException ex) {
            Logger.getLogger(ProdutoCadastroController.class.getName()).log(Level.SEVERE, null, ex);
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("ERRO");
            a.setHeaderText(null);
            a.setContentText("Erro na Conversão dos Valores e Quantidade");
            a.showAndWait();
        }
    }
    
    private void limparCampos(){
        nome.setText(null);
        preco.setText(null);
        codigo.clear();
        qtd.setText(null);
        validade.setValue(null);
        
    }

    @FXML
    private void btnEditar(ActionEvent event) {
    }

    @FXML
    private void btnExcluir(ActionEvent event) {
    }
    
}
