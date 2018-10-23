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
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
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
        TableColumn<Produtos, Double> colPreco = new TableColumn("Preço R$");
        TableColumn<Produtos, Double> colQtde = new TableColumn("Quantidade");
        TableColumn<Produtos, String> colValidade = new TableColumn("Validade");

        //Configurar como os valores serão lidos (nome dos atributos)
        colNome.setCellValueFactory(new PropertyValueFactory<Produtos, String>("nome"));
        colCodigo.setCellValueFactory(new PropertyValueFactory<Produtos, String>("codigo"));
        colPreco.setCellValueFactory(new PropertyValueFactory<Produtos, Double>("precoFormatado"));
        colQtde.setCellValueFactory(new PropertyValueFactory<Produtos, Double>("quantidadeFormatada"));
        colValidade.setCellValueFactory(new PropertyValueFactory<Produtos, String>("validadeFormatada"));
        
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
       
        
        try{
            //Manda a classe de nogocio salvar o produto
            if (id.getText().isEmpty()) {//no caso de inserir

                //Pegando os dados da tela e criando um produto
                Produtos p = new Produtos(
                        "0",
                        nome.getText(),
                        preco.getText(),
                        codigo.getText(),
                        qtd.getText(),
                        validade.getValue()
                );

                //Mandando a classe de negocio salvar o produto
                pBO.salvar(p);

                //Atualizando os dados da tabela
                carregarDados();
                //dados.add(p); (outra opção que adiciona no fim da tabela)

                //Limpando os campos apos salvar
                limparCampos();

                //Mensagem de Sucesso
                Alert a = new Alert(Alert.AlertType.INFORMATION);
                a.setTitle("Sucesso");
                a.setHeaderText(null);
                a.setContentText("Produto salvo com sucesso!");
                a.showAndWait();

            } else {//no caso de editar

                //Configurando a caixa de confirmacao
                Alert conf = new Alert(Alert.AlertType.CONFIRMATION);
                conf.setTitle("Editar");
                conf.setHeaderText("");
                conf.setContentText("Deseja Realmente salvar as alterações?");

                //Pegando o botao que foi pressionado
                Optional<ButtonType> btn = conf.showAndWait();

                if (btn.get() == ButtonType.OK) {//quer mesmo salvar
                    //Pegando os dados da tela e criando um produto
                    Produtos p = new Produtos(
                            id.getText(),
                            nome.getText(),
                            preco.getText(),
                            codigo.getText(),
                            qtd.getText(),
                            validade.getValue()
                    );

                    pBO.editar(p);

                    //Atualizando os dados da tabela
                    carregarDados();
                    //dados.add(p); (outra opção que adiciona no fim da tabela)

                    //Limpando os campos apos salvar
                    limparCampos();

                    //Mensagem de Sucesso
                    Alert a = new Alert(Alert.AlertType.INFORMATION);
                    a.setTitle("Sucesso");
                    a.setHeaderText(null);
                    a.setContentText("Produto salvo com sucesso!");
                    a.showAndWait();

                } else {

                    //Limpando os campos apos salvar
                    limparCampos();

                }

            }
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
        
        // Pegar o produto selecionado (pode ser null)
        Produtos p = tabela.getSelectionModel().getSelectedItem();
        
        if(p != null){ //para item selecionado
            // Preenche os campos com os dados do item selecionado
            id.setText(String.valueOf(p.getId()));
            nome.setText(p.getNome());
            codigo.setText(p.getCodigo());
            preco.setText(p.getPrecoFormatado());
            qtd.setText(p.getQuantidadeFormatada());
            validade.setValue(p.getValidade()); 
        }
        else{//Não tem produto selecionado
            
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("ERRO");
            a.setHeaderText(null);
            a.setContentText("Selecione um produto ");
            a.showAndWait();
        }
    }
    
    /**
     * Exclui um produto no banco de dados
     * @param event 
     */

    @FXML
    private void btnExcluir(ActionEvent event) {
        
        // Pegar o produto selecionado (pode ser null)
        Produtos p = tabela.getSelectionModel().getSelectedItem();
        
        // Verifica se tem produto selecionado na tabela.
        if(p != null){
            Alert conf = new Alert(Alert.AlertType.CONFIRMATION);
            conf.setTitle("Excluir");
            conf.setHeaderText("");
            conf.setContentText("Deseja realmente excluir este Item?");
            
           //Pegando o botao que foi pressionado
            Optional<ButtonType> btn = conf.showAndWait();

            //Verificar qual botão foi pressionado
            if (btn.get() == ButtonType.OK) { //vai excluir

                try {
                    
                    //Manda a classe de negocio excluir
                    pBO.excluir(p);
                    
                    //Atualizar a tabela
                    carregarDados();
                    
                    //Mensagem de excluído com sucesso
                    Alert m = new Alert(Alert.AlertType.INFORMATION);
                    m.setTitle("Sucesso");
                    m.setHeaderText(null);
                    m.setContentText("Produto excluído com sucesso");
                    m.showAndWait();
                    
                } catch (SQLException ex) {
                    //Mensagem de erro
                    Alert a = new Alert(Alert.AlertType.ERROR);
                    a.setTitle("ERRO");
                    a.setHeaderText(null);
                    a.setContentText("Erro de comunicação com "
                    + "o Banco de Dado procure o administrador "
                    + "do sistema");
                    a.showAndWait();
                }
            }
        }
    } 
    private void carregarComboBusca(){
        // Criar a lista
        ObservableList<String> lista = FXCollections.observableArrayList("Código", "Nome");
        
        //Jogar a lista no combo
        cboxFiltro.getItems().addAll(lista);
    }
}
