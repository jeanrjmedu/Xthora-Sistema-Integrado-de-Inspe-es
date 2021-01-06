/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inspecoes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Detectop serviços
 */
public class GerenciadorDB {

    Connection c;
   // Statement stmt;
    PreparedStatement stmt;
    String sql;

     
     
    public Boolean Criador() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5432/",
                            "postgres", "123456");
          

            sql = "CREATE DATABASE inspecoesdb";
            stmt = c.prepareStatement(sql);
            stmt.executeUpdate();
            
    
            c = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5432/inspecoesdb",
                            "postgres", "123456");
        
            

            sql = "CREATE TABLE caixa "
                    + "(etiqueta INT PRIMARY KEY NOT NULL,"
                    + " capacidade INT NOT NULL, "
                    + " latitude  VARCHAR(255) NOT NULL, "
                    + " longitude VARCHAR(255) NOT NULL,"
                    + " tipo VARCHAR(255) NOT NULL CHECK (tipo != 'Tipo'))";
              stmt = c.prepareStatement(sql);
             stmt.executeUpdate();

            sql = "CREATE TABLE poste "
                    + "(etiqueta INT PRIMARY KEY NOT NULL,"
                    + " altura NUMERIC (5,2) NOT NULL, "
                    + " latitude VARCHAR(255) NOT NULL, "
                    + " longitude VARCHAR(255), "
                    + " caixa_etiqueta INT NOT NULL, "
                    + " material VARCHAR(255) NOT NULL CHECK (material != 'Material'))";
              stmt = c.prepareStatement(sql);
               stmt.executeUpdate();
//airregular, adequado
            sql = "CREATE TABLE inspecoes "
                    + "(poste INT NOT NULL,"
                    + " data DATE NOT NULL, "
                    + " prumo  VARCHAR(255) NOT NULL CHECK (prumo != 'Selecione'), "
                    + " fiacao  VARCHAR(255) NOT NULL CHECK (fiacao != 'Selecione'), "
                    + " estado  VARCHAR(255) NOT NULL CHECK (estado != 'Selecione'), "
             + "PRIMARY KEY(poste, data))";
              stmt = c.prepareStatement(sql);
               stmt.executeUpdate();
               
            sql = "ALTER TABLE poste ADD FOREIGN KEY (caixa_etiqueta) REFERENCES caixa(etiqueta);";
              stmt = c.prepareStatement(sql);
               stmt.executeUpdate();
          
            
            sql = "ALTER TABLE inspecoes ADD FOREIGN KEY (poste) REFERENCES poste(etiqueta);";
              stmt = c.prepareStatement(sql);
               stmt.executeUpdate();
            
            c.close();
            return true;

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        
           
           System.exit(0);
        }
        return false;
    }
    
   
}
