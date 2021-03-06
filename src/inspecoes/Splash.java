/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inspecoes;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import static java.lang.Thread.sleep;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;

/**
 *
 * @author Detectop serviços
 */
public class Splash extends javax.swing.JFrame {

  Database db;
  
    public Splash() throws SQLException, InterruptedException {
        setUndecorated(true);
        
         try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }

        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
         
        initComponents();
        setLocationRelativeTo(null);
        setResizable(false);
        setTitle("Sistema Integrado de Inspeção | Xthora");
        setVisible(true);
        db = new Database();
         this.setIconImage(new ImageIcon(getClass().getResource("icol.png")).getImage());
        
        // Cria.setVisible(false);
         
        FEC.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
             
                System.exit(0);
            }

        });
        
         Cria.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                      
                    cria();
                    
                } catch (SQLException ex) {
                    Logger.getLogger(Splash.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Splash.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        });

        

        
        

    }
    
    public void conectar () throws InterruptedException, SQLException
    {
        if (!db.ConectaDB()) {
            jLabel1.setText("Erro");
            System.out.println("Não conseguiu se conectar");
        }else{
             jLabel1.setText("Conectado com sucesso");
         Thread.sleep(1000);
         new Menu(db).setVisible(true);
         this.dispose();
        }
    }
    
    public void cria() throws SQLException, InterruptedException {
      
       System.out.println("Criando DB");
        GerenciadorDB cr = new GerenciadorDB();
        if (cr.Criador()) {
            System.out.println("Criou");
        } else {
            System.out.println("Não criou");
        }
        conectar ();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel4 = new javax.swing.JLabel();
        SPLASHER = new javax.swing.JPanel();
        FEC = new javax.swing.JButton();
        Cria = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inspecoes/32.png"))); // NOI18N
        jLabel4.setText("jLabel4");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setForeground(java.awt.Color.red);
        setMaximumSize(new java.awt.Dimension(500, 362));
        setMinimumSize(new java.awt.Dimension(500, 362));

        SPLASHER.setBackground(new java.awt.Color(81, 81, 81));
        SPLASHER.setMaximumSize(new java.awt.Dimension(500, 362));
        SPLASHER.setMinimumSize(new java.awt.Dimension(500, 362));
        SPLASHER.setPreferredSize(new java.awt.Dimension(500, 362));
        SPLASHER.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        FEC.setBackground(new java.awt.Color(81, 81, 81));
        FEC.setText("Fechar");
        SPLASHER.add(FEC, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 320, -1, -1));

        Cria.setBackground(new java.awt.Color(81, 81, 81));
        Cria.setText("Criar DB");
        SPLASHER.add(Cria, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 320, 80, -1));

        jLabel1.setForeground(new java.awt.Color(255, 120, 0));
        jLabel1.setText("Inicializando...");
        SPLASHER.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 190, -1, -1));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inspecoes/xths.png"))); // NOI18N
        SPLASHER.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 240, -1, -1));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 120, 0));
        jLabel3.setText("Sistema Integrado de Inspeção");
        SPLASHER.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 140, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(SPLASHER, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(SPLASHER, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
            java.util.logging.Logger.getLogger(Splash.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Splash.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Splash.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Splash.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                // new Splash().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Cria;
    private javax.swing.JButton FEC;
    private javax.swing.JPanel SPLASHER;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    // End of variables declaration//GEN-END:variables
}
