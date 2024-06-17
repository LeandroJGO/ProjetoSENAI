/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package connection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Gerencia a conexão com o banco de dados. Possui as instruções necessárias para
 * conectar o banco de dados.
 * @author leandro
 */
public class MySQLConnection {
    
    //Define strings de conexão com o banco.
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://172.16.0.30:3306/leandro_dukemarket";
    
    private static final String USER = "leandro";
    private static final String PASS = "12345";

    /**
     * Cria conexão com o banco de dados MySQL.
     * @return 
    */
    
    public static Connection getConnection() {
        try {       //Força a carga do Driver do MySQL que foi importado
            Class.forName(DRIVER);
            return DriverManager.getConnection(URL, USER, PASS); //retorna a Connection com os dados da URL, USER e PASS.
        } catch (SQLException ex) {
            throw new RuntimeException ("Erro na conexao com o BD. Verifique!", ex);
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException ("Falha na carga do driver", ex);
        }    
    }
    
    /**
     * Fecha a conexão com o BD.
     * @param conn Connection a ser fechada.
     */
    
    public static void closeConnection(Connection conn) { //Método recebe um objeto Connection
        try {
            if (conn != null) {
                conn.close(); //Força o fechamento da Connection
            }
        } catch (SQLException ex) {
            //Uso da classe Logger para gerar logs de diferentes níveis (TRACE, DEBUG, INFO,
            //WARN e ERROR) para melhor acompanhamento da aplicação. 
            Logger.getLogger(
                    MySQLConnection.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    public static void closeConnection(Connection conn, PreparedStatement stmt) {
        closeConnection(conn);
        
        try {
            if (stmt != null) {
                stmt.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(
                    MySQLConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
    public static void closeConnection(Connection conn, PreparedStatement stmt, ResultSet rs) {
        closeConnection(conn, stmt);
        
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(
                    MySQLConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
}


