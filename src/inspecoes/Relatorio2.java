/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inspecoes;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import javax.swing.ImageIcon;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Detectop serviços
 */
public class Relatorio2 extends javax.swing.JFrame {

    Menu refer;
      DefaultTableModel REL1;
      
    public Relatorio2(Menu refer, String t, String mesi, String anoi, String mesf, String anof) throws SQLException {
        this.refer = refer;
         REL1 = new DefaultTableModel();
        initComponents();
        setLocationRelativeTo(null);
        setResizable(false);
        setLocationRelativeTo(null);
        setTitle("Relatório "+t+" entre " + mesi + "/" + anoi + " e " + mesf + "/" + anof);
        setVisible(true);
        relatorio2( mesi,  anoi,  mesf,  anof);
          this.setIconImage(new ImageIcon(getClass().getResource("icol.png")).getImage());
    } 
 private void relatorio2(String mesi, String anoi, String mesf, String anof) throws SQLException {
      

        int mesinicial = Integer.parseInt(mesi);
        int anoinicial = Integer.parseInt(anoi);
        int mesfinal = Integer.parseInt(mesf);
        int anofinal = Integer.parseInt(anof);

        System.out.println("hiuihdedfhehfu");
        // 
        
         REL1.addColumn("Mes/Ano");
          REL1.addColumn("Adequados");
          REL1.addColumn("Inadequados");
          
          
          
         ResultSet r;
         
        Vector row = new Vector();
        
        while (REL1.getRowCount() > 0) {
            REL1.removeRow(0);
        }

        for (int anoatual = anoinicial; anoatual <= anofinal; anoatual++) {
            if (anoinicial == anofinal) {

                for (int mesatual = mesinicial; mesatual <= mesfinal; mesatual++) {
                    System.out.println();
                    row = new Vector();
                    r = refer.dbi.Rel2_GetAdequadosMes(mesatual, anoatual);
                    row.add(mesatual + "/" + anoatual + "\n ");
                    r.next();
                    row.add((String) r.getString("qtd"));
                    r = refer.dbi.Rel2_GetInadequadosMes(mesatual, anoatual);
                    r.next();
                    row.add((String) r.getString("qtd"));
                     REL1.addRow(row);
                }

            } else if (anoinicial == anoatual) {

                for (int mesatual = mesinicial; mesatual <= 12; mesatual++) {
                    System.out.println();
                   
                   row = new Vector();
                          r = refer.dbi.Rel2_GetAdequadosMes(mesatual, anoatual);
                    row.add(mesatual + "/" + anoatual + "\n ");
                    r.next();
                    row.add((String) r.getString("qtd"));
                    r = refer.dbi.Rel2_GetInadequadosMes(mesatual, anoatual);
                    r.next();
                    row.add((String) r.getString("qtd"));
                     REL1.addRow(row);
                }

            } else if (anofinal == anoatual) {

                for (int mesatual = 01; mesatual <= mesfinal; mesatual++) {
                    System.out.println(mesatual + "/" + anoatual + "\n ");
                    row = new Vector();
                    
                         r = refer.dbi.Rel2_GetAdequadosMes(mesatual, anoatual);
                    row.add(mesatual + "/" + anoatual + "\n ");
                    r.next();
                    row.add((String) r.getString("qtd"));
                    r = refer.dbi.Rel2_GetInadequadosMes(mesatual, anoatual);
                    r.next();
                    row.add((String) r.getString("qtd"));
                     REL1.addRow(row);
                }

            } else {

                for (int mesatual = 01; mesatual <= 12; mesatual++) {
                    System.out.println();
                   row = new Vector();
                    r = refer.dbi.Rel2_GetAdequadosMes(mesatual, anoatual);
                    row.add(mesatual + "/" + anoatual + "\n ");
                    r.next();
                    row.add((String) r.getString("qtd"));
                    r = refer.dbi.Rel2_GetInadequadosMes(mesatual, anoatual);
                    r.next();
                    row.add((String) r.getString("qtd"));
                    REL1.addRow(row);
                }
            }
        }

      
        Tab2.removeAll();
        Tab2.setModel(REL1);

       
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        Tab2 = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        Tab2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(Tab2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 720, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 489, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Relatorio2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Relatorio2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Relatorio2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Relatorio2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                //new Relatorio().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable Tab2;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
