/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inspecoes;

import java.sql.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Vector;
import javax.swing.JLabel;

public class Database {

    Connection c;
    PreparedStatement stmt;
    String sql;

    public ResultSet retornaConsulta(String tabela) throws SQLException {

        stmt = c.prepareStatement("SELECT * FROM " + tabela + ";");
        ResultSet resultado = stmt.executeQuery();

        return resultado;

    }
    
     public int Rel3GetQtdPostesPorCaixa(int etiqueta) throws SQLException {

        stmt = c.prepareStatement("SELECT count (*) AS qtd FROM poste  WHERE poste.caixa_etiqueta ="+etiqueta+" ;");
        ResultSet resultado = stmt.executeQuery();
 resultado.next();
        return Integer.parseInt(resultado.getString("qtd"));

    }
     
    
     
       public int [] Rel3_Get (String caixa, String data_inicio, String data_fim) throws SQLException {

        int qtdProblemas = 0; 
        
      stmt = c.prepareStatement("SELECT count (*) AS estado FROM inspecoes INNER JOIN poste ON (poste.etiqueta = inspecoes.poste) WHERE poste.caixa_etiqueta = "+ caixa +" AND inspecoes.estado = 'Inadequado' AND inspecoes.data BETWEEN '"+data_inicio+"' AND '"+data_fim+"'");
        ResultSet resultado = stmt.executeQuery();
       resultado.next();
        qtdProblemas += Integer.parseInt(resultado.getString("estado"));
        
        
        
        stmt = c.prepareStatement("SELECT count (*) AS fiacao FROM inspecoes INNER JOIN poste ON (poste.etiqueta = inspecoes.poste) WHERE poste.caixa_etiqueta = "+ caixa +" AND inspecoes.fiacao = 'Irregular' AND inspecoes.data BETWEEN '"+data_inicio+"' AND '"+data_fim+"';");
        resultado = stmt.executeQuery();
        resultado.next();
        qtdProblemas += Integer.parseInt(resultado.getString("fiacao"));
        
        stmt = c.prepareStatement("SELECT count (*) AS prumo FROM inspecoes INNER JOIN poste ON (poste.etiqueta = inspecoes.poste) WHERE poste.caixa_etiqueta = "+ caixa +" AND inspecoes.prumo = 'Irregular' AND inspecoes.data BETWEEN '"+data_inicio+"' AND '"+data_fim+"';");
        resultado = stmt.executeQuery();
        resultado.next();
        qtdProblemas += Integer.parseInt(resultado.getString("prumo"));
        
        
        int [] rset = new int [2];
        
        
          rset[0]=qtdProblemas;
          rset[1]=Rel3GetQtdPostesPorCaixa(Integer.parseInt(caixa))*3;
        return rset;

    }

        public ResultSet Rel3GetNomesCaixas() throws SQLException {

        stmt = c.prepareStatement("SELECT etiqueta FROM caixa");
        ResultSet resultado = stmt.executeQuery();

        return resultado;

    }
        
    public ResultSet Rel1_GetNaoInspecionadosMes(int mes, int ano) throws SQLException {
        String compos = ano + "-" + mes + "-01";
        stmt = c.prepareStatement("SELECT poste.etiqueta AS qtd FROM poste WHERE poste.etiqueta"
                + " NOT IN (SELECT inspecoes.poste FROM inspecoes WHERE inspecoes.data = ?) ORDER BY poste.etiqueta; ");
        stmt.setString(1, compos);
        stmt = c.prepareStatement(stmt.toString());
        ResultSet resultado = stmt.executeQuery();

        return resultado;

    }

    public void Cadastra_Ponto_Distribuicao(Menu f, JLabel ds, String etiqueta, String capacidade, String latitude, String longitude, String tipo) throws SQLException {
        try {
            sql = "INSERT INTO caixa (etiqueta,capacidade,latitude,longitude,tipo) VALUES (?,?,?,?,?);";
            stmt = c.prepareStatement(sql);
            stmt.setInt(1, Integer.parseInt(etiqueta));
            stmt.setInt(2, Integer.parseInt(capacidade));
            stmt.setString(3, latitude);
            stmt.setString(4, longitude);
            stmt.setString(5, tipo);
            stmt.executeUpdate();

            ds.setText("Ponto de etiqueta " + etiqueta + " cadastrado com sucesso");
            f.update_Text_AdicionaPonto();

        } catch (SQLException e) {
            ds.setText("Erro\n");

            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.out.println("Erro PD");

        }
    }

    public void Cadastra_Poste(Menu f, JLabel ds, String etiqueta, String altura, String latitude, String longitude, String caixa, String material) throws SQLException {
        try {
            sql = "INSERT INTO poste (etiqueta,altura,latitude,longitude,caixa_etiqueta, material) VALUES (?,?,?,?,?,?);";

            stmt = c.prepareStatement(sql);
            stmt.setInt(1, Integer.parseInt(etiqueta));
            stmt.setFloat(2, Float.parseFloat(altura));
            stmt.setString(3, latitude);
            stmt.setString(4, longitude);
            stmt.setInt(5, Integer.parseInt(caixa));
            stmt.setString(6, material);

            stmt.executeUpdate();

            ds.setText("Poste de etiqueta " + etiqueta + " cadastrado com sucesso");
            f.update_Text_AdicionaPoste();

        } catch (SQLException e) {
            ds.setText("Erro\n");

            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.out.println("Erro PST");

        }
    }

    public void Cadastra_inspec(Menu f, JLabel ds, String poste, String mes, String ano, String prumo, String fiacao, String estado) throws SQLException, Exception {

        try {
           
            String data = ano + "-" + mes + "-01"; //1999-01-08 January 8 in any mode    

            sql = "INSERT INTO inspecoes (poste,data,prumo,fiacao,estado) VALUES (?,?,?,?,?);";
            stmt = c.prepareStatement(sql);
            stmt.setInt(1, Integer.parseInt(poste));
            stmt.setString(2, data);
            stmt.setString(3, prumo);
            stmt.setString(4, fiacao);
            stmt.setString(5, estado);
            stmt = c.prepareStatement(stmt.toString());
            stmt.executeUpdate();

            ds.setText("Inspeção do poste " + poste + " em " + mes + "/" + ano + " cadastrada com sucesso");
            f.update_Text_InspecaoAdd();

        } catch (SQLException e) {
            ds.setText("Erro\n");

            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.out.println("Erro INSP");

        }
    }

    public void Remove_inspec(Menu f, JLabel ds, String poste, String mes, String ano) {
        try {
            if (Integer.parseInt(mes) > 12) {
                throw new SQLException();
            }

            String data = ano + "-" + mes + "-01"; //1999-01-08 January 8 in any mode    

            sql = "DELETE FROM inspecoes WHERE poste = ? AND data = ?;";
            stmt = c.prepareStatement(sql);
            stmt.setInt(1, Integer.parseInt(poste));
            stmt.setString(2, data);
            stmt = c.prepareStatement(stmt.toString());
            stmt.executeUpdate();

            ds.setText("Inspeção do poste " + poste + " em " + mes + "/" + ano + " removida com sucesso (Se ele existiu");
            f.update_Text_InspecaoRemove();

        } catch (SQLException e) {
            ds.setText("Erro\n");

            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.out.println("deumerda");

        }
    }
    /////////////////////////////////////////////// RELATORIO 2

    public ResultSet Rel2_GetAdequadosMes(int mes, int ano) throws SQLException {

        String compos = ano + "-" + mes + "-01";
        stmt = c.prepareStatement("SELECT count (*) AS qtd FROM inspecoes WHERE inspecoes.data = ? AND inspecoes.estado = 'Adequado'");
        stmt.setString(1, compos);
        stmt = c.prepareStatement(stmt.toString());
        ResultSet a = stmt.executeQuery();
        return a;

    }

    public ResultSet Rel2_GetInadequadosMes(int mes, int ano) throws SQLException {
        String compos = ano + "-" + mes + "-01";
        stmt = c.prepareStatement("SELECT count (*) AS qtd FROM inspecoes WHERE inspecoes.data = ? AND inspecoes.estado = 'Inadequado';");
        stmt.setString(1, compos);
        stmt = c.prepareStatement(stmt.toString());
        
        int g = 0;
        ResultSet b = stmt.executeQuery();
        return b;
    }

   
  //////////////////////////////////////INICIALIZAÇÃO
    public Boolean ConectaDB() throws SQLException {
        // test();
     
        
        
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5432/inspecoesdb",
                            "postgres", "123456");

          
            return true;

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());

        }

        return false;
    }

}
