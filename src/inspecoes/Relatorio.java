/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inspecoes;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.ImageIcon;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Detectop serviços
 */
public class Relatorio extends javax.swing.JFrame {

    DefaultTableModel REL1;
    Menu refer;

    public Relatorio(Menu refer, String mesi, String anoi, String mesf, String anof) throws SQLException {
        this.refer = refer;
        initComponents();
        setIconImage(new ImageIcon(getClass().getResource("icol.png")).getImage());
        REL1 = new DefaultTableModel();
        Relatorio1Tab.setRowSelectionAllowed(false);
        Relatorio1Tab.setColumnSelectionAllowed(true);
        Relatorio1Tab.setAutoResizeMode(Relatorio1Tab.AUTO_RESIZE_OFF);
  this.setIconImage(new ImageIcon(getClass().getResource("icol.png")).getImage());
        setLocationRelativeTo(null);
        setResizable(false);
        setTitle("Postes Não Inspecionados  [" + mesi + "/" + anoi + " - " + mesf + "/" + anof + "]");
        Rel_Desc.setText("Postes não inspecionados por mês entre " + mesi + "/" + anoi + " e " + mesf + "/" + anof);
        relatorio1(mesi, anoi, mesf, anof);
    }

    private void relatorio1(String mesi, String anoi, String mesf, String anof) throws SQLException {
        ResultSet r;

        int mesinicial = Integer.parseInt(mesi);
        int anoinicial = Integer.parseInt(anoi);
        int mesfinal = Integer.parseInt(mesf);
        int anofinal = Integer.parseInt(anof);

        ArrayList<Vector> El;
        El = new ArrayList();

        Vector ElRow = new Vector();

        for (int anoatual = anoinicial; anoatual <= anofinal; anoatual++) {
            if (anoinicial == anofinal) {

                for (int mesatual = mesinicial; mesatual <= mesfinal; mesatual++) {
                    System.out.println(mesatual + "/" + anoatual + "\n ");
                    ElRow = new Vector();

                    ElRow.add(mesatual + "/" + anoatual);
                    r = refer.dbi.Rel1_GetNaoInspecionadosMes(mesatual, anoatual);
                   // r.next();
                    while (r.next()) {
                        ElRow.add((String) r.getString("qtd"));
                        System.out.println(r.getString("qtd"));
                    }

                    El.add(ElRow);
                }

            } else if (anoinicial == anoatual) {

                for (int mesatual = mesinicial; mesatual <= 12; mesatual++) {
                    System.out.println(mesatual + "/" + anoatual + "\n ");
                    ElRow = new Vector();
                    ElRow.add(mesatual + "/" + anoatual);
                    r = refer.dbi.Rel1_GetNaoInspecionadosMes(mesatual, anoatual);
                   // r.next();
                    while (r.next()) {
                        ElRow.add((String) r.getString("qtd"));
                        System.out.println(r.getString("qtd"));
                    }

                    El.add(ElRow);
                }

            } else if (anofinal == anoatual) {

                for (int mesatual = 01; mesatual <= mesfinal; mesatual++) {
                    System.out.println(mesatual + "/" + anoatual + "\n ");
                    ElRow = new Vector();
                    ElRow.add(mesatual + "/" + anoatual);
                    r = refer.dbi.Rel1_GetNaoInspecionadosMes(mesatual, anoatual);

                  //  r.next();
                    while (r.next()) {
                        ElRow.add((String) r.getString("qtd"));
                        System.out.println(r.getString("qtd"));
                    }

                    El.add(ElRow);
                }

            } else {

                for (int mesatual = 01; mesatual <= 12; mesatual++) {
                    System.out.println(mesatual + "/" + anoatual + "\n ");
                    ElRow = new Vector();
                    ElRow.add(mesatual + "/" + anoatual);
                    r = refer.dbi.Rel1_GetNaoInspecionadosMes(mesatual, anoatual);
                   // r.next();

                    while (r.next()) {
                        ElRow.add((String) r.getString("qtd"));
                    }

                    El.add(ElRow);
                }
            }

        }

        Vector[] RESULTSET = El.toArray(new Vector[El.size()]);

        // REL1.setColumnCount(45);
        while (REL1.getRowCount() > 0) {
            REL1.removeRow(0);
        }

        //GUARDARÁ O NUMERO DE ELEMENTOS PERCORRIVEIS POR INDICE NO VETOR RESULTSET PARA NAO HAVER NULLPOINTER 
        ArrayList<Integer> Temp_QTD_LINHAS_POR_MES = new ArrayList();
        int maior = 0;
        for (int a = 0; a < RESULTSET.length; a++) {
            Temp_QTD_LINHAS_POR_MES.add(RESULTSET[a].size());
            if (RESULTSET[a].size() > maior) {
                maior = RESULTSET[a].size();
            }
        }
        Integer[] QTD_LINHAS_POR_MES;
        QTD_LINHAS_POR_MES = Temp_QTD_LINHAS_POR_MES.toArray(new Integer[Temp_QTD_LINHAS_POR_MES.size()]);

        for (int a = 0; a < RESULTSET.length; a++) {
            REL1.addColumn(RESULTSET[a].elementAt(0).toString());

        }

        //ADICIONA LINHAS
        String f = "Poste ";
        Vector row = new Vector();
        for (int i = 1; i < maior; i++) { //PERCORRE CADA LINHA
            for (int a = 0; a < RESULTSET.length; a++) { //ADICIONA ELEMENTOS EM TODAS AS COLUNAS DA LINHA
                if (i < QTD_LINHAS_POR_MES[a]) //EVITA NULLPOINTER POIS TEM MESES COM MENOS LINHAS 
                {
                    row.add(f + RESULTSET[a].elementAt(i));
                } else {
                    row.add("---");
                }

            }
            REL1.addRow(row);
            row = new Vector();
        }

        Relatorio1Tab.removeAll();
        Relatorio1Tab.setModel(REL1);
        //  Relatorio1Tab.getTableHeader().setUI(null);

        System.out.println("colunas" + REL1.getColumnCount());
    }

    private void relatorio1f(String mesi, String anoi, String mesf, String anof) throws SQLException {
        ResultSet r;

        int mesinicial = Integer.parseInt(mesi);
        int anoinicial = Integer.parseInt(anoi);
        int mesfinal = Integer.parseInt(mesf);
        int anofinal = Integer.parseInt(anof);

        // REL1.setColumnCount(45);
        while (REL1.getRowCount() > 0) {
            REL1.removeRow(0);
        }

        Vector row = new Vector();

        for (int anoatual = anoinicial; anoatual <= anofinal; anoatual++) {
            if (anoinicial == anofinal) {

                for (int mesatual = mesinicial; mesatual <= mesfinal; mesatual++) {
                    System.out.println(mesatual + "/" + anoatual + "\n ");
                    row = new Vector();

                    row.add(mesatual + "/" + anoatual);
                    r = refer.dbi.Rel1_GetNaoInspecionadosMes(mesatual, anoatual);
                    r.next();
                    while (r.next()) {
                        row.add((String) r.getString("qtd"));
                        System.out.println(r.getString("qtd"));
                    }

                    REL1.addRow(row);
                }

            } else if (anoinicial == anoatual) {

                for (int mesatual = mesinicial; mesatual <= 12; mesatual++) {
                    System.out.println(mesatual + "/" + anoatual + "\n ");
                    row = new Vector();
                    row.add(mesatual + "/" + anoatual);
                    r = refer.dbi.Rel1_GetNaoInspecionadosMes(mesatual, anoatual);
                    r.next();
                    while (r.next()) {
                        row.add((String) r.getString("qtd"));
                        System.out.println(r.getString("qtd"));
                    }

                    REL1.addRow(row);
                }

            } else if (anofinal == anoatual) {

                for (int mesatual = 01; mesatual <= mesfinal; mesatual++) {
                    System.out.println(mesatual + "/" + anoatual + "\n ");
                    row = new Vector();
                    row.add(mesatual + "/" + anoatual);
                    r = refer.dbi.Rel1_GetNaoInspecionadosMes(mesatual, anoatual);

                    r.next();
                    while (r.next()) {
                        row.add((String) r.getString("qtd"));
                        System.out.println(r.getString("qtd"));
                    }

                    REL1.addRow(row);
                }

            } else {

                for (int mesatual = 01; mesatual <= 12; mesatual++) {
                    System.out.println(mesatual + "/" + anoatual + "\n ");
                    row = new Vector();
                    row.add(mesatual + "/" + anoatual);
                    r = refer.dbi.Rel1_GetNaoInspecionadosMes(mesatual, anoatual);
                    r.next();

                    while (r.next()) {
                        row.add((String) r.getString("qtd"));
                    }

                    REL1.addRow(row);
                }
            }

        }

        Relatorio1Tab.removeAll();
        Relatorio1Tab.setModel(REL1);
        Relatorio1Tab.getTableHeader().setUI(null);

        System.out.println("colunas" + REL1.getColumnCount());
    }

    private void relatorio1x(String mesi, String anoi, String mesf, String anof) throws SQLException {
        ResultSet r;

        int mesinicial = Integer.parseInt(mesi);
        int anoinicial = Integer.parseInt(anoi);
        int mesfinal = Integer.parseInt(mesf);
        int anofinal = Integer.parseInt(anof);

        while (REL1.getRowCount() > 0) {
            REL1.removeRow(0);
        }

        REL1.setColumnCount(0);
        REL1.addColumn("Mês/Ano");
        REL1.addColumn("Postes não inspecionados");

        Vector row = new Vector();

        for (int anoatual = anoinicial; anoatual <= anofinal; anoatual++) {
            if (anoinicial == anofinal) {

                for (int mesatual = mesinicial; mesatual <= mesfinal; mesatual++) {
                    System.out.println(mesatual + "/" + anoatual + "\n ");
                    row = new Vector();
                    row.add(mesatual + "/" + anoatual);
                    r = refer.dbi.Rel1_GetNaoInspecionadosMes(mesatual, anoatual);

                    r.next();
                    row.add((String) r.getString("qtd"));
                    REL1.addRow(row);
                }

            } else if (anoinicial == anoatual) {

                for (int mesatual = mesinicial; mesatual <= 12; mesatual++) {
                    System.out.println(mesatual + "/" + anoatual + "\n ");
                    row = new Vector();
                    row.add(mesatual + "/" + anoatual);
                    r = refer.dbi.Rel1_GetNaoInspecionadosMes(mesatual, anoatual);

                    r.next();
                    row.add((String) r.getString("qtd"));
                    REL1.addRow(row);
                }

            } else if (anofinal == anoatual) {

                for (int mesatual = 01; mesatual <= mesfinal; mesatual++) {
                    System.out.println(mesatual + "/" + anoatual + "\n ");
                    row = new Vector();
                    row.add(mesatual + "/" + anoatual);
                    r = refer.dbi.Rel1_GetNaoInspecionadosMes(mesatual, anoatual);
                    r.next();
                    row.add((String) r.getString("qtd"));
                    REL1.addRow(row);
                }

            } else {

                for (int mesatual = 01; mesatual <= 12; mesatual++) {
                    System.out.println(mesatual + "/" + anoatual + "\n ");
                    row = new Vector();
                    row.add(mesatual + "/" + anoatual);
                    r = refer.dbi.Rel1_GetNaoInspecionadosMes(mesatual, anoatual);
                    r.next();
                    row.add((String) r.getString("qtd"));
                    REL1.addRow(row);
                }
            }

        }

        Relatorio1Tab.removeAll();
        Relatorio1Tab.setModel(REL1);

        // Relatorio.setLocationRelativeTo(null);
        //Relatorio.
        //Relatorio.setVisible(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Rel_Desc = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Relatorio1Tab = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(740, 511));
        setMinimumSize(new java.awt.Dimension(740, 511));

        Rel_Desc.setText("Desc");

        jScrollPane1.setPreferredSize(new java.awt.Dimension(720, 480));

        Relatorio1Tab.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(Relatorio1Tab);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(Rel_Desc)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Rel_Desc)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 469, javax.swing.GroupLayout.PREFERRED_SIZE)
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
            java.util.logging.Logger.getLogger(Relatorio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Relatorio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Relatorio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Relatorio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                //new Relatorio().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Rel_Desc;
    private javax.swing.JTable Relatorio1Tab;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
