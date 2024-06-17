package com.mycompany.dukemarket.javafx.controller;

//Importar os componentes do Java FX:
import com.mycompany.dukemarket.javafx.App;
import com.mycompany.dukemarket.javafx.DAO.ProdutoDAO;
import com.mycompany.dukemarket.javafx.model.Produto;
import java.io.IOException;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import java.net.URL;
import java.util.Calendar;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

//Imports para o método tblProdutoOnMouseClicked()
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Locale;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;



/**
 * FXML Controller class
 *
 * @author Aluno
 */
public class ScrProdutosController implements Initializable {
    
    //Aqui iniciamos a vinculação dos componentes da View na classe
    //scrProdutosController.
    // Atributos tipo TableColumn recebem no operador Diamond(<>) dois
    //Parametros: a classe e o tipo.
    @FXML 
    TableView <Produto> tblProduto;
    @FXML
    TableColumn <Produto, Integer> tcoId;
    @FXML
    TableColumn <Produto, String> tcoCodBarras;
    @FXML
    TableColumn <Produto, String> tcoDescricao;
    @FXML
    TableColumn <Produto, Double> tcoQtd;
    @FXML
    TableColumn <Produto, Double> tcoValorCompra;
    @FXML
    TableColumn <Produto, Double> tcoValorVenda;
    @FXML
    TableColumn <Produto, Calendar> tcoDataCadastro;
    
    //@FXML -> Permite que o Java FX vincule de forma automática o componente
    //da View na programação.
    @FXML
    TextField txtId;
    @FXML
    TextField txtCodBarras;
    @FXML
    TextField txtDescricao;
    @FXML
    TextField txtQtd;
    @FXML
    TextField txtValorCompra;
    @FXML
    TextField txtValorVenda;
    @FXML
    DatePicker dtpDataCadastro;
    
    ProdutoDAO pDAO;
    private boolean flagNovo = true;
    Produto pClicked;
    
    /**
     * Conecta as colunas da TableView com os atributos da classe Produto.
     * @return void
     */
    
    private void bindColumns() {
        
        //Vincula a coluna tcoId com o atributo id da classe Produto
        tcoId.setCellValueFactory(new PropertyValueFactory<>("id"));
        
        //Adiciona uma configuração extra de alinhamento á direita.
        tcoId.setStyle("fx-alignment: CENTER_RIGHT;");
        
        tcoCodBarras.setCellValueFactory(new PropertyValueFactory<>("codBarras"));
        tcoCodBarras.setStyle("fx-alignment: CENTER_RIGHT;");
        
        tcoDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        
        tcoQtd.setCellValueFactory(new PropertyValueFactory<>("qtd"));
        tcoQtd.setStyle("fx-alignment: CENTER_RIGHT;");
        
        tcoValorCompra.setCellValueFactory(new PropertyValueFactory<>("valorCompra"));
        tcoValorCompra.setStyle("fx-alignment: CENTER_RIGHT;");
        
        tcoValorVenda.setCellValueFactory(new PropertyValueFactory<>("valorVenda"));
        tcoValorVenda.setStyle("fx-alignment: CENTER_RIGHT;");
        
        tcoDataCadastro.setCellValueFactory(new PropertyValueFactory<>("dataCadastro"));
        tcoDataCadastro.setStyle("fx-alignment: CENTER_RIGHT;");
        
    }
    
    public void carregaDados() {
        
        //Esta parte instancia a classe pDAO.
        this.pDAO = new ProdutoDAO();
        
        //Carrega view tblProduto com todos os resultados de produto.
        //Define os itens da TableView como sendo o resultado da pDAO.getResult
        this.tblProduto.getItems().setAll(pDAO.getResults());
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
        
        txtCodBarras.setText("");
        txtCodBarras.requestFocus(); /*Define o foco para txtCodBarras (coloca
        o cursor de texto posicionado no componente).*/
        
        txtDescricao.setText("");
        txtQtd.setText("");
        txtValorCompra.setText("");
        txtValorVenda.setText("");
        dtpDataCadastro.setValue(null); //Limpa o DatePicker   
    }
    
    /**
     * Gerencia o click na tabela, identificando qual registro foi selecionado e
     * preenchendo os TextFields.
     */
    
    @FXML
    public void tblProdutoOnMouseClicked() {
        
        //getSelectedItem devolve um objeto Produto.
        //pClicked representa o Produto Clicado.
        
        //tblProduto.getSelectionModel().getSelectedItem() retorna um Produto
        
        this.flagNovo = false;
        
        this.pClicked = tblProduto.getSelectionModel().getSelectedItem();        
        
        if (pClicked != null) {
            
            txtId.setText(String.valueOf(pClicked.getId()));
            txtId.setEditable(false);
            
            txtCodBarras.setText(pClicked.getCodBarras());
            txtDescricao.setText(pClicked.getDescricao());
            
            txtQtd.setText(String.valueOf(pClicked.getQtd()));
            txtValorCompra.setText(String.valueOf(pClicked.getValorCompra()));
            txtValorVenda.setText(String.valueOf(pClicked.getValorVenda()));
            
            //Tratamento da data do cadastro:
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            formatter = formatter.withLocale(Locale.US);
            LocalDate date = LocalDate.parse(pClicked.getDataCadastro(), formatter);
            
            dtpDataCadastro.setValue(date);
            
            this.flagNovo = false;
            
        }
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        //Vincula as colunas da TableView á classe Produto.
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
        
        //intancia o Produto para ser utilizada no método btnSalvarClick
 
        Produto p = new Produto();
        
        p.setCodBarras(txtCodBarras.getText());
        p.setDescricao(txtDescricao.getText());
        p.setQtd(Double.parseDouble(txtQtd.getText()));
        p.setValorCompra(Double.parseDouble(txtValorCompra.getText()));
        p.setValorVenda(Double.parseDouble(txtValorVenda.getText()));
        
        if(this.flagNovo == true) {
            //Novo produto...
            
            this.pDAO.create(p);
            
        } else {
            //Ops... é uma atualização!
            p.setId(Integer.parseInt(txtId.getText()));
            this.pDAO.update(p);
        }
        
        this.carregaDados();           
    }
    
    /**
     * Gerencia o click do botão excluir. Remove o registro selecionado
     * carregado em pClicked.
     * 
     */
    
    public void btnExcluirClick() {
        
        if (this.pClicked != null) {
            
            Alert alert = new Alert (AlertType.CONFIRMATION);
            alert.setTitle("Confirmar Exlusão de Produto");
            alert.setHeaderText(pClicked.getDescricao());
            alert.setContentText("Tem certeza que deseja excluir ?");
            
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                this.pDAO.delete(this.pClicked.getId());
                
                this.tblProduto.getItems().remove(this.pClicked);
            }
            
            this.tblProduto.getSelectionModel().clearSelection();
            pClicked = null;         
            
        } else {
                
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Ops!");
            alert.setHeaderText("Atenção");
            alert.setContentText("Você deve selecionar um registro antes de exclui-lo");
            
            alert.showAndWait();
        }
    }
}
