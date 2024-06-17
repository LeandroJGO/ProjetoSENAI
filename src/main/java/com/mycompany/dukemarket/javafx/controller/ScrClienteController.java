
package com.mycompany.dukemarket.javafx.controller;

import com.mycompany.dukemarket.javafx.App;
import com.mycompany.dukemarket.javafx.DAO.ClienteDAO;
import com.mycompany.dukemarket.javafx.model.Cliente;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author Aluno
 */
public class ScrClienteController implements Initializable {
    //Aqui iniciamos a vinculação dos componentes da View na classe
    //scrClienteController.
    // Atributos tipo TableColumn recebem no operador Diamond(<>) dois
    //Parametros: a classe e o tipo.
    @FXML 
    TableView <Cliente> tblCliente;
    @FXML
    TableColumn <Cliente, Integer> tcoId;
    @FXML
    TableColumn <Cliente, String> tcoNome;
    @FXML
    TableColumn <Cliente, String> tcoEndereco;
    @FXML
    TableColumn <Cliente, String> tcoCidade;
    @FXML
    TableColumn <Cliente, String> tcoUf;
    @FXML
    TableColumn <Cliente, String> tcoCep;
    
    //@FXML -> Permite que o Java FX vincule de forma automática o componente
    //da View na programação.
    @FXML
    TextField txtId;
    @FXML
    TextField txtNome;
    @FXML
    TextField txtEndereco;
    @FXML
    TextField txtCidade;
    @FXML
    TextField txtUf;
    @FXML
    TextField txtCep;
    
    ClienteDAO cDAO;
    private boolean flagNovo;
    Cliente cClicked;
    
    private void bindColumns() {
        
        //Vincula a coluna tcoId com o atributo id da classe Cliente
        tcoId.setCellValueFactory(new PropertyValueFactory<>("id"));
        
        //Adiciona uma configuração extra de alinhamento á direita.
        tcoId.setStyle("fx-alignment: CENTER_RIGHT;");
        
        tcoNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        
        tcoEndereco.setCellValueFactory(new PropertyValueFactory<>("endereco"));
        
        tcoCidade.setCellValueFactory(new PropertyValueFactory<>("cidade"));
        
        tcoUf.setCellValueFactory(new PropertyValueFactory<>("uf"));
        tcoUf.setStyle("fx-alignment: CENTER_RIGHT;");
        
        tcoCep.setCellValueFactory(new PropertyValueFactory<>("cep"));
        tcoCep.setStyle("fx-alignment: CENTER_RIGHT;");           
    }
    
    public void carregaDados() {
        
        //Esta parte instancia a classe pDAO.
        this.cDAO = new ClienteDAO();
        
        //Carrega view tblProduto com todos os resultados de produto.
        //Define os itens da TableView como sendo o resultado da pDAO.getResult
        this.tblCliente.getItems().setAll(cDAO.getResults());
    }
    
    /**
     * Action do botão btnNovo.
     * Limpa os campos e posiciona o focus em txtId.
     * 
     * @return void
     */
    
    @FXML
    private void btnNovoClick() {
        
        this.flagNovo = true;
        
        txtId.setText(""); //Define o texto do campo ID como vazio("")
        txtId.setEditable(false); //Desativa o modo de edição do ID
        
        txtNome.setText("");
        txtNome.requestFocus(); /*Define o foco para txtNome (coloca
        o cursor de texto posicionado no componente).*/
        
        txtEndereco.setText("");
        txtCidade.setText("");
        txtUf.setText("");
        txtCep.setText("");
    }
    
    /**
     * Gerencia o click na tabela, identificando qual registro foi selecionado e
     * preenchendo os TextFields.
     */
    
    @FXML
    public void tblClienteOnMouseClicked() {
        
        //getSelectedItem devolve um objeto Cliente.
        //cClicked representa o Cliente Clicado.
        
        //tblCliente.getSelectionModel().getSelectedItem() retorna um Cliente
        
        this.flagNovo = false;
        
        this.cClicked = tblCliente.getSelectionModel().getSelectedItem();        
        
        if (cClicked != null) {
            
            txtId.setText(String.valueOf(cClicked.getId()));
            txtId.setEditable(false);
            
            txtNome.setText(cClicked.getNome());
            txtEndereco.setText(cClicked.getEndereco());
            
            txtCidade.setText(cClicked.getCidade());
            txtUf.setText(cClicked.getUf());
            txtCep.setText(cClicked.getCep());
            
            this.flagNovo = false;
        }
    }
    
  
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
          
        //Vincula as colunas da TableView á classe Cliente.
        bindColumns();
        
        //Busca Dados
        carregaDados();
    }
    /**
     * Gerencia o click do botão salvar. Adiciona um novo registro quando
     * flagNovo == true ou atualiza quando flagNovo == false.
     */
    @FXML
    public void btnSalvarClick() {
        
        //intancia o Cliente para ser utilizada no método btnSalvarClick
 
        Cliente c = new Cliente();
        
        c.setNome(txtNome.getText());
        c.setEndereco(txtEndereco.getText());
        c.setCidade(txtCidade.getText());
        c.setUf(txtUf.getText());
        c.setCep(txtCep.getText());
        
        if(this.flagNovo == true) {
            //Novo produto...
            
            this.cDAO.create(c);
            
        } else {
            //Ops... é uma atualização!
            c.setId(Integer.parseInt(txtId.getText()));
            this.cDAO.update(c);
        }
        
        this.carregaDados();           
    }
    /**
     * Gerencia o click do botão excluir. Remove o registro selecionado
     * carregado em cClicked.
     * 
     */
    
    public void btnExcluirClick() {
        
        if (this.cClicked != null) {
            
            Alert alert = new Alert (Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmar Exlusão de Cliente");
            alert.setHeaderText(cClicked.getNome());
            alert.setContentText("Tem certeza que deseja excluir ?");
            
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                this.cDAO.delete(this.cClicked.getId());
                
                this.tblCliente.getItems().remove(this.cClicked);
            }
            
            this.tblCliente.getSelectionModel().clearSelection();
            cClicked = null;         
            
        } else {
                
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Ops!");
            alert.setHeaderText("Atenção");
            alert.setContentText("Você deve selecionar um registro antes de exclui-lo");
            
            alert.showAndWait();
        }
    }
}
