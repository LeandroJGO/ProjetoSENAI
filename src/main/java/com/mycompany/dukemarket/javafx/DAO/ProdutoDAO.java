/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.dukemarket.javafx.DAO;

/*import com.mycompany.dukemarket.bean.Produto;*/
import com.mycompany.dukemarket.javafx.model.Produto;
import connection.MySQLConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Aluno
 */
public class ProdutoDAO { //Insira as strings estáticas que irão armazenar as instruções do CRUD, conforme exemplo
                          //abaixo:
    public static final String SQL_INSERT = "INSERT INTO produto (codBarras, "
            + "descricao, Qtd, valorCompra, valorVenda) "
            + "VALUES (?, ?, ?, ?, ?)";
    public static final String SQL_SELECT_ALL = "SELECT * FROM produto";
    public static final String SQL_SELECT_ID = "SELECT * FROM produto "
            + "WHERE id = ?";
    public static final String SQL_UPDATE = "UPDATE produto SET codBarras = ?,"
            + "descricao = ?, Qtd = ?, valorCompra = ?, valorVenda = ? "
            + "WHERE id = ?";
    public static final String SQL_DELETE = "DELETE FROM produto WHERE id = ?";
    
    /**
     * Insere um usuario na base de dados produto
     * @param p
    */
    
    public void create(Produto p) {
        Connection conn = MySQLConnection.getConnection(); //Inicia uma conexão com a base de dados utilizando nossa classe MySQLConnection.
        PreparedStatement stmt = null; //declara o PreparedStatement que irá receber a instrução a ser executada no banco.
        
        try {
            stmt = conn.prepareStatement(SQL_INSERT); //Prepara a instrução dada na constante SQL_INSERT. Perceba que essa SQL possui
                                                      //uma série de “?”
            //substitui os “?” pelos atributos do produto recebido no método.                                          
            stmt.setString(1, p.getCodBarras());
            stmt.setString(2, p.getDescricao());
            stmt.setDouble(3, p.getQtd());
            stmt.setDouble(4, p.getValorCompra());
            stmt.setDouble(5, p.getValorVenda());
            
            //Executa a quary
            int auxRetorno = stmt.executeUpdate(); //executa a query e retorna um inteiro. O inteiro representa a quantidade de registros
                                                   //incluídos.
            
            Logger.getLogger(ProdutoDAO.class.getName()).log(Level.INFO, null, // gera um log INFO para informação.
                    "Inclusao: " + auxRetorno);
            
        } catch (SQLException sQLException) {
            Logger.getLogger(ProdutoDAO.class.getName()).log(Level.SEVERE, null, //gera um log SEVERE caso tenha algum erro.
                    sQLException);
            
        } finally {
            //Encerra a conexao com o banco e o Statement.
            MySQLConnection.closeConnection(conn, stmt); //Encerra os recursos de consulta ao banco.
        }
    }
    
    /**
     * Retorna todos os produtos da tabela produto
     * @return
     */
    public List<Produto> getResults() {
        Connection conn = MySQLConnection.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Produto p = null;
        List<Produto> listaProdutos = null;
        
        try {
            //Prepara a string de SELECT e exetuca a query.
            stmt = conn.prepareStatement(SQL_SELECT_ALL);
            rs = stmt.executeQuery(); //Executa a query e retorna um ResultSet. 
            
            //Carrega dos dados do ResultSet rs, converte em Produto e
            //adiciona na lista de retorno.
            listaProdutos = new ArrayList<>();
            //Varre o ResultSet(rs) utilizando o método rs.next
            while (rs.next()) {
                p = new Produto();
                p.setId(rs.getInt("id"));
                p.setCodBarras(rs.getString("codBarras"));
                p.setDescricao(rs.getString("descricao"));
                p.setQtd(rs.getDouble("Qtd"));
                p.setValorCompra(rs.getDouble("valorCompra"));
                p.setValorVenda(rs.getDouble("valorVenda"));
                p.setDataCadastro(rs.getString("dataCadastro"));
                listaProdutos.add(p);
            }
            
            } catch (SQLException ex) {
                Logger.getLogger(ProdutoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            return listaProdutos;
        }
    
    public Produto getResultById(int id) {
        
        Connection conn = MySQLConnection.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Produto p = null;
        
        try {
            stmt = conn.prepareStatement(SQL_SELECT_ID);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                p = new Produto();
                p.setId(rs.getInt("id"));
                p.setCodBarras(rs.getString("codBarras"));
                p.setDataCadastro(rs.getString("descricao"));
                p.setQtd(rs.getDouble("Qtd"));
                p.setValorCompra(rs.getDouble("valorVenda"));
                p.setValorVenda(rs.getDouble("valorVenda"));
                p.setDataCadastro(rs.getString("dataCadastro"));
            }
            
        } catch (SQLException sQLException) {
            Logger.getLogger(ProdutoDAO.class.getName()).log(Level.SEVERE,null,
                    sQLException);
        } finally {
            //Encerra a conexão com o banco e o statement.
            MySQLConnection.closeConnection(conn, stmt, rs);   
        }
     
        return p;
        
    }
    
    /**
     * Atualiza um registro na tabela produto.
     * @param p Produto a ser atualizado.
     */
    
    public void update (Produto p) {
        Connection conn = MySQLConnection.getConnection();
        PreparedStatement stmt = null;
        
        try {
            stmt = conn.prepareStatement(SQL_UPDATE);
            stmt.setString(1, p.getCodBarras());
            stmt.setString(2, p.getDescricao());
            stmt.setDouble(3, p.getQtd());
            stmt.setDouble(4, p.getValorCompra());
            stmt.setDouble(5, p.getValorVenda());
            stmt.setInt(6, p.getId());
            
            //Executa a query
            int auxRetorno = stmt.executeUpdate();
            
            Logger.getLogger(ProdutoDAO.class.getName()).log(Level.INFO, null,
                    "Update: " + auxRetorno);
        } catch (SQLException sQLException) {
            Logger.getLogger(ProdutoDAO.class.getName()).log(Level.SEVERE, null,
                    sQLException);
            
        } finally {
            //Encerra a conexão com o banco e o statement.
            MySQLConnection.closeConnection(conn, stmt);
        }     
    }
    
    /**
     * Exclui um cliente com base do ID fornecido.
     * @param id IDentificação do produto.
     */
    
    public void delete(int id) {
        Connection conn = MySQLConnection.getConnection();
        PreparedStatement stmt = null;
        
        try {
            stmt = conn.prepareStatement(SQL_DELETE);
            stmt.setInt(1, id);
            
            //Executa a query
            int auxRetorno = stmt.executeUpdate();
            
            Logger.getLogger(ProdutoDAO.class.getName()).log(Level.INFO, null,
                    "Delete: " + auxRetorno);
            
        } catch (SQLException sQLException) {
            Logger.getLogger(ProdutoDAO.class.getName()).log(Level.SEVERE, null,
                    sQLException);
            
        } finally {
            //Encerra a conexão com o banco e o statement.
            MySQLConnection.closeConnection(conn, stmt);
        } 
    }
}    


