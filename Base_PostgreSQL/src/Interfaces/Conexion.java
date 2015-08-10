/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;


public class Conexion {
    Connection conec=null;
    
    public Connection conectar(){
        String url = "jdbc:postgresql://localhost:5432/facturacion";
        String password = "123456";
        try {
            Class.forName("org.postgresql.Driver");
            conec = DriverManager.getConnection(url, "postgres", password);
           // JOptionPane.showMessageDialog(null, "Conexión Exitosa ");
        } catch (Exception e) {
             JOptionPane.showMessageDialog(null, "Conexión Erronea "+e);
        }
        return conec;
    }
    
    
    
    
}
