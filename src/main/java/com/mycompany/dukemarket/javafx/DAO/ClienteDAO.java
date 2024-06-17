package com.mycompany.dukemarket.javafx.DAO;

import static com.mycompany.dukemarket.javafx.DAO.ProdutoDAO.SQL_DELETE;
import static com.mycompany.dukemarket.javafx.DAO.ProdutoDAO.SQL_SELECT_ALL;
import static com.mycompany.dukemarket.javafx.DAO.ProdutoDAO.SQL_SELECT_ID;
import static com.mycompany.dukemarket.javafx.DAO.ProdutoDAO.SQL_UPDATE;
import com.mycompany.dukemarket.javafx.model.Cliente;
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
public class ClienteDAO {
    
    public static final String SQL_INSERT = "INSERT INTO cliente (nome, "
            + "endereco, cidade, uf, cep) "
            + "VALUES (?, ?, ?, ?, ?)";
    public static final String SQL_SELECT_ALL = "SELECT * FROM cliente";
    public static final String SQL_SELECT_ID = "SELECT * FROM cliente "
            + "WHERE id = ?";
    public static final String SQL_UPDATE = "UPDATE cliente SET nome = ?,"
            + "endereco = ?, cidade = ?, uf = ?, cep = ? "
            + "WHERE id = ?";
    public static final String SQL_DELETE = "DELETE FROM cliente WHERE id = ?";
    
    
    /**
     * 
     * Insere um usuario na base de dados Cliente
     * @param c 
     */
    public void create(Cliente c) {
        Connection conn = MySQLConnection.getConnection(); //Inicia uma conexão com a base de dados utilizando nossa classe MySQLConnection.
        PreparedStatement stmt = null; //declara o PreparedStatement que irá receber a instrução a ser executada no banco.
        
        try {
            stmt = conn.prepareStatement(SQL_INSERT); //Prepara a instrução dada na constante SQL_INSERT. Perceba que essa SQL possui
                                                      //uma série de “?”
            //substitui os “?” pelos atributos do produto recebido no método.                                          
            stmt.setString(1, c.getNome());
            stmt.setString(2, c.getEndereco());
            stmt.setString(3, c.getCidade());
            stmt.setString(4, c.getUf());
            stmt.setString(5, c.getCep());
            
            //Executa a quary
            int auxRetorno = stmt.executeUpdate(); //executa a query e retorna um inteiro. O inteiro representa a quantidade de registros
                                                   //incluídos.
            
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.INFO, null, // gera um log INFO para informação.
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
     * Retorna todos os produtos da tabela produtos
     * @return 
     */
    public List<Cliente> getResults() {
        Connection conn = MySQLConnection.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Cliente c = null;
        List<Cliente> listaClientes = null;
        
        try {
            //Prepara a string de SELECT e exetuca a query.
            stmt = conn.prepareStatement(SQL_SELECT_ALL);
            rs = stmt.executeQuery(); //Executa a query e retorna um ResultSet. 
            
            //Carrega dos dados do ResultSet rs, converte em Produto e
            //adiciona na lista de retorno.
            listaClientes = new ArrayList<>();
            //Varre o ResultSet(rs) utilizando o método rs.next
            while (rs.next()) {
                c = new Cliente();
                c.setId(rs.getInt("id"));
                c.setNome(rs.getString("nome"));
                c.setEndereco(rs.getString("endereco"));
                c.setCidade(rs.getString("cidade"));
                c.setUf(rs.getString("uf"));
                c.setCep(rs.getString("cep"));
                listaClientes.add(c);
            }
            
            } catch (SQLException ex) {
                Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            return listaClientes;
        }
    
    public Cliente getResultById(int id) {
        
        Connection conn = MySQLConnection.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Cliente c = null;
        
        try {
            stmt = conn.prepareStatement(SQL_SELECT_ID);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                c = new Cliente();
                c.setId(rs.getInt("id"));
                c.setNome(rs.getString("nome"));
                c.setEndereco(rs.getString("endereco"));
                c.setCidade(rs.getString("cidade"));
                c.setUf(rs.getString("uf"));
                c.setCep(rs.getString("cep"));
            }
            
        } catch (SQLException sQLException) {
            Logger.getLogger(ProdutoDAO.class.getName()).log(Level.SEVERE,null,
                    sQLException);
        }finally {
            //Encerra a conexão com o banco e o statement.
            MySQLConnection.closeConnection(conn, stmt, rs);   
        }
     
        return c;
    }
    /**
     * Atualiza um registro na tabela cliente.
     * @param p Produto a ser atualizado.
     */
    
    public void update (Cliente p) {
        Connection conn = MySQLConnection.getConnection();
        PreparedStatement stmt = null;
        
        try {
            stmt = conn.prepareStatement(SQL_UPDATE);
            stmt.setString(1, p.getNome());
            stmt.setString(2, p.getEndereco());
            stmt.setString(3, p.getCidade());
            stmt.setString(4, p.getUf());
            stmt.setString(5, p.getCep());
            stmt.setInt(6, p.getId());
            
            //Executa a query
            int auxRetorno = stmt.executeUpdate();
            
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.INFO, null,
                    "Update: " + auxRetorno);
        } catch (SQLException sQLException) {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null,
                    sQLException);
            
        } finally {
            //Encerra a conexão com o banco e o statement.
            MySQLConnection.closeConnection(conn, stmt);
        }       
    }
    /**
     * Exclui um produto com base do ID fornecido.
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
            
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.INFO, null,
                    "Delete: " + auxRetorno);
            
        } catch (SQLException sQLException) {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null,
                    sQLException);
            
        } finally {
            //Encerra a conexão com o banco e o statement.
            MySQLConnection.closeConnection(conn, stmt);
        } 
    }
    
}
