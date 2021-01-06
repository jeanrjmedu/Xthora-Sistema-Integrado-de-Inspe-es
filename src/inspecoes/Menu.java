/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inspecoes;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Detectop serviços
 */
public class Menu extends javax.swing.JFrame {

    public Database dbi;
    Menu menu;
    DefaultTableModel POSTES;
    DefaultTableModel DISTR;
    DefaultTableModel INSP;
    
    String pd;

    /**
     * Creates new form Menu
     */
    public Menu(Database dbi) throws SQLException {
        pd = "Ponto de Distribuição";
        this.setResizable(false);
        this.dbi = dbi;
        menu = this;
        setTitle("Sistema Integrado de Inspeção | Xthora");

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
         sob.setUndecorated(true);
        setLocationRelativeTo(null);

        PreparaTabelasCombos_Inicial();
        BotõesMenuPrincipal();
        CadastrarPostes();
        MostrarPostes();
        CadastrarPontos();
        MostrarPontos();
        GeraRelatorio();
        AdicionaInspecao();
        RemoveInspecao();
        Sobre();

        this.setIconImage(new ImageIcon(getClass().getResource("icol.png")).getImage());
    }

  
    //COMPORTAMNETO DOS ELEMENTOS NAS JANELAS DE DIALOGO
    private void BotõesMenuPrincipal() {
        Menu_Botao_Relatorio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Manager_GerarRelatorio();

            }
        });

        Mostra_insp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    Manager_mostrarInsp();
                } catch (SQLException ex) {
                    Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        Menu_Botao_mostrarPostes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Manager_mostrarPST();
                } catch (SQLException ex) {
                    Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });

        Menu_Botao_adicionarPoste.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Manager_PosteCadastro();

            }
        });

        Menu_Botao_mostrarPontos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Manager_mostrarPD();
                } catch (SQLException ex) {
                    Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });

        Menu_Botao_AdicionarPonto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Manager_PontoCadastro();
            }
        });

        MenuBotao_Sobre.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
              Manager_Sobre ();
            }

        });
    }

    
    private void GeraRelatorio() {
        DialogoGerarRelatorio_Botao_Gerar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (SeletorRelatorio.getSelectedItem().equals("Postes não Inspecionados")) {
                    try {
                        new Relatorio (menu, MesInicial.getText(), AnoInicial.getText(), MesFinal.getText(), AnoFinal.getText()).setVisible(true);
                        //relatorio1(MesInicial.getText(), AnoInicial.getText(), MesFinal.getText(), AnoFinal.getText());
                    } catch (SQLException ex) {
                        Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
                    }

                } else if (SeletorRelatorio.getSelectedItem().equals("Quantitativo Adequados/Inadequados")) {
                    try {
                        new Relatorio2(menu, SeletorRelatorio.getSelectedItem().toString(), MesInicial.getText(), AnoInicial.getText(), MesFinal.getText(), AnoFinal.getText());
                    } catch (SQLException ex) {
                        Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
                    }
       
                } else if (SeletorRelatorio.getSelectedItem().equals("Saude das Caixas")) {
                    try {
                        new Relatorio3(menu, MesInicial.getText(), AnoInicial.getText(), MesFinal.getText(), AnoFinal.getText());
                    } catch (SQLException ex) {
                        Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
                    }
                   
                }
                update_Text_GeraRelatorio();
                GerarRelatorio_Dialogo.setVisible(false);
            }
        });

          AnoFinal.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {

                //checa se tecla pressionada é numero senao ignora
                int i = 0;
                try {
                    i = Integer.parseInt(String.valueOf(e.getKeyChar()));
                } catch (Exception es) {
                    System.out.println("invalido");
                    e.consume();
                }
                //limita espaço
                if (AnoFinal.getText().length() >= 4) {
                    e.consume();
                }
            }
        });
           AnoInicial.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {

                //checa se tecla pressionada é numero senao ignora
                int i = 0;
                try {
                    i = Integer.parseInt(String.valueOf(e.getKeyChar()));
                } catch (Exception es) {
                    System.out.println("invalido");
                    e.consume();
                }
                //limita espaço
                if (AnoInicial.getText().length() >= 4) {
                    e.consume();
                }
            }
        });

        MesInicial.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {

                //checa se tecla pressionada é numero senao ignora
                int i = 0;
                try {
                    i = Integer.parseInt(String.valueOf(e.getKeyChar()));
                } catch (Exception es) {
                    System.out.println("invalido");
                    e.consume();
                }
                //limita espaço
                if (MesInicial.getText().length() >= 2) {
                    e.consume();
                }
            }
        });
        
         MesFinal.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {

                //checa se tecla pressionada é numero senao ignora
                int i = 0;
                try {
                    i = Integer.parseInt(String.valueOf(e.getKeyChar()));
                } catch (Exception es) {
                    System.out.println("invalido");
                    e.consume();
                }
                //limita espaço
                if (MesFinal.getText().length() >= 2) {
                    e.consume();
                }
            }
        });
    }

    private void MostrarPostes() {
        Dialogo_Botao_AdicionarPoste.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Manager_PosteCadastro();
            }
        });
    }

    private void CadastrarPostes() {
        Dialogo_Botao_CadastrarPoste.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    dbi.Cadastra_Poste(menu, ds1, PSTAdd_Etiqueta.getText(), PST_Altura.getText(), PSTAdd_Latit.getText(), PSTAdd_Longi.getText(), PSTAdd_PontoDist.getText(), PSTAdd_Selector.getSelectedItem().toString());
                    update_tabelagrafica_Postes();

                } catch (Exception ex) {
                    Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
                    System.out.println("");
                }

            }
        });
        PSTAdd_Etiqueta.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {

                //checa se tecla pressionada é numero senao ignora
                int i = 0;
                try {
                    i = Integer.parseInt(String.valueOf(e.getKeyChar()));
                } catch (Exception es) {
                    System.out.println("invalido");
                    e.consume();
                }
                //limita espaço
                if (PSTAdd_Etiqueta.getText().length() >= 8) {
                    e.consume();
                }
            }
        });

        PSTAdd_PontoDist.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {

                //checa se tecla pressionada é numero senao ignora
                int i = 0;
                try {
                    i = Integer.parseInt(String.valueOf(e.getKeyChar()));
                } catch (Exception es) {
                    System.out.println("invalido");
                    e.consume();
                }
                //limita espaço
                if (PSTAdd_PontoDist.getText().length() >= 8) {
                    e.consume();
                }
            }
        });

        PST_Altura.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                //ADICIONA PONTO  NA 3a CASA DESDE QUE NA 2a NÃO HAJA  
                if (PST_Altura.getText().length() > 1) {

                    if (PST_Altura.getText().length() == 2 && PST_Altura.getText().charAt(1) != '.') {
                        PST_Altura.setText(PST_Altura.getText() + ".");

                    }
                }
                
                //checa se tecla pressionada é numero senao ignora
                int i = 0;
                try {
                    i = Integer.parseInt(String.valueOf(e.getKeyChar()));
                } catch (Exception es) {
                     //checa se tecla é ponto e se está na 2a posicao senao ignora
                    if (PST_Altura.getText().length() == 1 && (e.getKeyChar() == ',' || e.getKeyChar() == '.')) {
                        e.setKeyChar('.');

                    } else {
                        System.out.println("invalido");
                        e.consume();
                    }

                }
                
                //LIMITACAO DEPENDENDO DA POSICAO DO PONTO
                if (PST_Altura.getText().length() > 1) {
                    if (PST_Altura.getText().charAt(1) == '.' && PST_Altura.getText().length() >= 4) {
                        e.consume();
                    } else if (PST_Altura.getText().length() >= 5) {

                        e.consume();
                    }
                }

            }
        });

    }

    private void CadastrarPontos() {
        CampoTexto_PDAdd_Capacidade.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {

                //checa se tecla pressionada é numero senao ignora
                int i = 0;
                try {
                    i = Integer.parseInt(String.valueOf(e.getKeyChar()));
                } catch (Exception es) {
                    System.out.println("invalido");
                    e.consume();
                }
            }
        });
        CampoTexto_PDAdd_Etiqueta.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {

                //checa se tecla pressionada é numero senao ignora
                int i = 0;
                try {
                    i = Integer.parseInt(String.valueOf(e.getKeyChar()));
                } catch (Exception es) {
                    System.out.println("invalido");
                    e.consume();
                }
                //limita espaço
                if (CampoTexto_PDAdd_Etiqueta.getText().length() >= 8) {
                    e.consume();
                }
            }
        });

        Dialogo_Botao_CadastrarPonto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    dbi.Cadastra_Ponto_Distribuicao(menu, ds, CampoTexto_PDAdd_Etiqueta.getText(), CampoTexto_PDAdd_Capacidade.getText(), PDAdd__Latit.getText(), PDAdd__Longi.getText(), PDCombo.getSelectedItem().toString());
                    update_tabelagrafica_Pontos();

                } catch (Exception ex) {
                    Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
                    System.out.println("");
                }

            }

        });

    }

    private void MostrarPontos() {
        Dialogo_Botao_AdicionarPonto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Manager_PontoCadastro();
            }
        });

    }

    private void AdicionaInspecao() {
        AddInspecao.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    dbi.Cadastra_inspec(menu, ds2, EtiquetaAdd.getText(), MesAdd.getText(), AnoAdd.getText(), Prumo.getSelectedItem().toString(), Fiacao.getSelectedItem().toString(), Estado.getSelectedItem().toString());
                    update_tabelagrafica_Inspecoes();
                } catch (Exception ex) {
                    Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
                    System.out.println("");

                }
            }
        });

        EtiquetaAdd.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {

                //checa se tecla pressionada é numero senao ignora
                int i = 0;
                try {
                    i = Integer.parseInt(String.valueOf(e.getKeyChar()));
                } catch (Exception es) {
                    System.out.println("invalido");
                    e.consume();
                }
                //limita espaço
                if (EtiquetaAdd.getText().length() >= 8) {
                    e.consume();
                }
            }
        });

        AnoAdd.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {

                //checa se tecla pressionada é numero senao ignora
                int i = 0;
                try {
                    i = Integer.parseInt(String.valueOf(e.getKeyChar()));
                } catch (Exception es) {
                    System.out.println("invalido");
                    e.consume();
                }
                //limita espaço
                if (AnoAdd.getText().length() >= 4) {
                    e.consume();
                }
            }
        });

        MesAdd.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {

                //checa se tecla pressionada é numero senao ignora
                int i = 0;
                try {
                    i = Integer.parseInt(String.valueOf(e.getKeyChar()));
                } catch (Exception es) {
                    System.out.println("invalido");
                    e.consume();
                }
                //limita espaço
                if (MesAdd.getText().length() >= 2) {
                    e.consume();
                }
            }
        });
    }

    private void RemoveInspecao() {
        Remove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    dbi.Remove_inspec(menu, ds3, EtiquetaAdd1.getText(), MesAdd1.getText(), AnoAdd1.getText());
                    update_tabelagrafica_Inspecoes();

                } catch (Exception ex) {
                    Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
                    System.out.println("");

                }
            }
        });

        EtiquetaAdd1.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {

                //checa se tecla pressionada é numero senao ignora
                int i = 0;
                try {
                    i = Integer.parseInt(String.valueOf(e.getKeyChar()));
                } catch (Exception es) {
                    System.out.println("invalido");
                    e.consume();
                }
                //limita espaço
                if (EtiquetaAdd1.getText().length() >= 8) {
                    e.consume();
                }
            }
        });

        AnoAdd1.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {

                //checa se tecla pressionada é numero senao ignora
                int i = 0;
                try {
                    i = Integer.parseInt(String.valueOf(e.getKeyChar()));
                } catch (Exception es) {
                    System.out.println("invalido");
                    e.consume();
                }
                //limita espaço
                if (AnoAdd1.getText().length() >= 4) {
                    e.consume();
                }
            }
        });

        MesAdd1.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {

                //checa se tecla pressionada é numero senao ignora
                int i = 0;
                try {
                    i = Integer.parseInt(String.valueOf(e.getKeyChar()));
                } catch (Exception es) {
                    System.out.println("invalido");
                    e.consume();
                }
                //limita espaço
                if (MesAdd1.getText().length() >= 2) {
                    e.consume();
                }
            }
        });

    }

    private void Sobre() {
        Fecha.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
              //  sob.setVisible(false);
                sob.setVisible(false);
            }

        });
    }

    private void PreparaTabelasCombos_Inicial() throws SQLException {

        POSTES = new DefaultTableModel();
        DISTR = new DefaultTableModel();
        INSP = new DefaultTableModel();
      

        update_Text_InspecaoAdd();
        update_Text_InspecaoRemove();

///TABELAS
        POSTES.addColumn("Etiqueta");
        POSTES.addColumn("Altura (m)");
        POSTES.addColumn("Latitude");
        POSTES.addColumn("Longitude");
        POSTES.addColumn("Ponto de distribuição");
        POSTES.addColumn("Material");
        DISTR.addColumn("Etiqueta");
        DISTR.addColumn("Capacidade");
        DISTR.addColumn("Latitude");
        DISTR.addColumn("Longitude");

        INSP.addColumn("Etiqueta");
        INSP.addColumn("Data");
        INSP.addColumn("Prumo");
        INSP.addColumn("Fiação");
        INSP.addColumn("Estado");

        jLabel11.setText(pd);
        ShowPDTable.setModel(DISTR);
        ShowPSTable.setModel(POSTES);
        ShowPDTable.setRowSelectionAllowed(true);
        ShowPDTable.setColumnSelectionAllowed(false);
        ShowPSTable.setRowSelectionAllowed(true);
        ShowPSTable.setColumnSelectionAllowed(false);

        //COMBOS
        Prumo.removeAllItems();
        Prumo.addItem("Selecione");
        Prumo.addItem("Irregular");
        Prumo.addItem("Regular");

        Fiacao.removeAllItems();
        Fiacao.addItem("Selecione");
        Fiacao.addItem("Irregular");
        Fiacao.addItem("Regular");

        Estado.removeAllItems();
        Estado.addItem("Selecione");
        Estado.addItem("Inadequado");
        Estado.addItem("Adequado");
        
         PDCombo.removeAllItems();
        PDCombo.addItem("Tipo");
        PDCombo.addItem("Exposto");
        PDCombo.addItem("Subterraneo");

        SeletorRelatorio.removeAllItems();
        SeletorRelatorio.addItem("Selecione");
        SeletorRelatorio.addItem("Postes não Inspecionados");
        SeletorRelatorio.addItem("Quantitativo Adequados/Inadequados");
        SeletorRelatorio.addItem("Saude das Caixas");
        PSTAdd_Selector.removeAllItems();
        PSTAdd_Selector.addItem("Material");
        PSTAdd_Selector.addItem("Concreto");
        PSTAdd_Selector.addItem("Metal");
        PSTAdd_Selector.addItem("Madeira");

        EtiquetaAdd.setText("");
        MesAdd.setText("MM");
        AnoAdd.setText("AAAA");
        Fiacao.setSelectedIndex(0);
        Estado.setSelectedIndex(0);
        Prumo.setSelectedIndex(0);
        ds2.setText("");
        ds3.setText("");
        EtiquetaAdd1.setText("");
        MesAdd1.setText("MM");
        AnoAdd1.setText("AAAA");
    }

   
    //ATUALIZA E PREPARA DADOS DAS TABELAS GRAFICAS
    private void update_tabelagrafica_Pontos() throws SQLException {

        ResultSet r;
        r = dbi.retornaConsulta("caixa");

        while (DISTR.getRowCount() > 0) {
            DISTR.removeRow(0);
        }

        while (r.next()) {
            Vector row = new Vector();
            row.add((String) r.getString("etiqueta"));
            row.add((String) r.getString("capacidade"));
            row.add((String) r.getString("latitude"));
            row.add((String) r.getString("longitude"));
            DISTR.addRow(row);
        }

        ShowPDTable.removeAll();
        ShowPDTable.setModel(DISTR);
    }

    private void update_tabelagrafica_Inspecoes() throws SQLException {

        ResultSet r;
        r = dbi.retornaConsulta("inspecoes");

        while (INSP.getRowCount() > 0) {
            INSP.removeRow(0);
        }

        while (r.next()) {
            Vector row = new Vector();
            row.add((String) r.getString("poste"));
            row.add((String) r.getString("data"));
            row.add((String) r.getString("prumo"));
            row.add((String) r.getString("fiacao"));
            row.add((String) r.getString("estado"));
            INSP.addRow(row);
        }

        ShowInsp.removeAll();
        ShowInsp.setModel(INSP);
    }

    private void update_tabelagrafica_Postes() throws SQLException {

        ResultSet r;
        r = dbi.retornaConsulta("poste");

        while (POSTES.getRowCount() > 0) {
            POSTES.removeRow(0);
        }

        while (r.next()) {
            Vector row = new Vector();
            row.add((String) r.getString("etiqueta"));
            row.add((String) r.getString("altura"));
            row.add((String) r.getString("latitude"));
            row.add((String) r.getString("longitude"));
            row.add((String) r.getString("caixa_etiqueta"));
            row.add((String) r.getString("material"));
            POSTES.addRow(row);
        }

        ShowPDTable.removeAll();
        ShowPDTable.setModel(POSTES);
    }

    //ATUALIZA TEXTOS ANTES DE ABRIR DIALOGO
    public void update_Text_InspecaoAdd() {
        EtiquetaAdd.setText("");
        MesAdd.setText("MM");
        AnoAdd.setText("AAAA");
        Fiacao.setSelectedIndex(0);
        Estado.setSelectedIndex(0);
        Prumo.setSelectedIndex(0);
    }

    public void update_Text_InspecaoRemove() {
        EtiquetaAdd1.setText("");
        MesAdd1.setText("MM");
        AnoAdd1.setText("AAAA");
    }

    private void update_Text_GeraRelatorio() {
        MesInicial.setText("MM");
        AnoInicial.setText("AAAA");
        SeletorRelatorio.setSelectedIndex(0);
        MesFinal.setText("MM");
        AnoFinal.setText("AAAA");
    }

    public void update_Text_AdicionaPonto() {
        CampoTexto_PDAdd_Etiqueta.setText("");
        CampoTexto_PDAdd_Capacidade.setText("");
        PDAdd__Longi.setText("");
        PDAdd__Latit.setText("");
        PDCombo.setSelectedIndex(0);
    }

    public void update_Text_AdicionaPoste() {
        PSTAdd_Etiqueta.setText("");
        PSTAdd_PontoDist.setText("");
        PSTAdd_Longi.setText("");
        PSTAdd_Latit.setText("");
        PSTAdd_Selector.setSelectedIndex(0);
        PST_Altura.setText("");

    }

    //PREPARA DIALOGO ANTES DE ABRIR
    private void Manager_Sobre ()
    {
          sob.setLocationRelativeTo(null);
               
                sob.setResizable(false);
                sob.setTitle("Sobre");
                sob.setVisible(true);
    }
    
    private void Manager_GerarRelatorio() {

        GerarRelatorio_Dialogo.setLocationRelativeTo(null);
        GerarRelatorio_Dialogo.setTitle("Gerar Relatório");
        update_Text_GeraRelatorio();
        GerarRelatorio_Dialogo.setVisible(true);
    }

    private void Manager_mostrarPST() throws SQLException {

        update_tabelagrafica_Postes();
        Mostrar_PSTs_Dialogo.setResizable(false);
        Mostrar_PSTs_Dialogo.setLocationRelativeTo(null);
        Mostrar_PSTs_Dialogo.setTitle("Postes");
        Mostrar_PSTs_Dialogo.setVisible(true);

    }

    private void Manager_mostrarInsp() throws SQLException {

        update_tabelagrafica_Inspecoes();
        Mostrar_Inspecoes.setResizable(false);
        Mostrar_Inspecoes.setLocationRelativeTo(null);
        Mostrar_Inspecoes.setTitle("Inspeções");
        Mostrar_Inspecoes.setVisible(true);

    }

    private void Manager_mostrarPD() throws SQLException {

        update_tabelagrafica_Pontos();
        Mostrar_PDs_Dialogo.setResizable(false);
        Mostrar_PDs_Dialogo.setLocationRelativeTo(null);
        Mostrar_PDs_Dialogo.setTitle("Pontos de Distribuição");
        Mostrar_PDs_Dialogo.setVisible(true);
    }

    private void Manager_PosteCadastro() {
        ds1.setText("");
        update_Text_AdicionaPoste();
        AddPoste.setResizable(false);
        AddPoste.setLocationRelativeTo(null);
        AddPoste.setTitle("Cadastrar Poste");
        AddPoste.setVisible(true);
    }

    private void Manager_PontoCadastro() {
        ds.setText("");
        update_Text_AdicionaPonto();
        AddPD.setResizable(false);
        AddPD.setLocationRelativeTo(null);
        AddPD.setTitle("Cadastrar " + pd);
        AddPD.setVisible(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        GerarRelatorio_Dialogo = new javax.swing.JDialog();
        jPanel1 = new javax.swing.JPanel();
        SeletorRelatorio = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        MesInicial = new javax.swing.JTextField();
        AnoInicial = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        MesFinal = new javax.swing.JTextField();
        AnoFinal = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        DialogoGerarRelatorio_Botao_Gerar = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        AddPoste = new javax.swing.JDialog();
        jLabel11 = new javax.swing.JLabel();
        PSTAdd_PontoDist = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        PSTAdd_Longi = new javax.swing.JTextField();
        PSTAdd_Etiqueta = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        PSTAdd_Selector = new javax.swing.JComboBox<>();
        PSTAdd_Latit = new javax.swing.JTextField();
        Dialogo_Botao_CadastrarPoste = new javax.swing.JButton();
        PST_Altura = new javax.swing.JTextField();
        ds1 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        AddPD = new javax.swing.JDialog();
        jLabel13 = new javax.swing.JLabel();
        CampoTexto_PDAdd_Capacidade = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        PDAdd__Longi = new javax.swing.JTextField();
        CampoTexto_PDAdd_Etiqueta = new javax.swing.JTextField();
        PDAdd__Latit = new javax.swing.JTextField();
        Dialogo_Botao_CadastrarPonto = new javax.swing.JButton();
        ds = new javax.swing.JLabel();
        PDCombo = new javax.swing.JComboBox<>();
        jLabel40 = new javax.swing.JLabel();
        Mostrar_PDs_Dialogo = new javax.swing.JDialog();
        jScrollPane2 = new javax.swing.JScrollPane();
        ShowPDTable = new javax.swing.JTable();
        Dialogo_Botao_AdicionarPonto = new javax.swing.JButton();
        jLabel27 = new javax.swing.JLabel();
        Mostrar_PSTs_Dialogo = new javax.swing.JDialog();
        Dialogo_Botao_AdicionarPoste = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        ShowPSTable = new javax.swing.JTable();
        jLabel26 = new javax.swing.JLabel();
        sob = new javax.swing.JDialog();
        jPanel3 = new javax.swing.JPanel();
        jLabel29 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        Fecha = new javax.swing.JButton();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        Mostrar_Inspecoes = new javax.swing.JDialog();
        jScrollPane3 = new javax.swing.JScrollPane();
        ShowInsp = new javax.swing.JTable();
        jLabel36 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        Tabs = new javax.swing.JTabbedPane();
        Inspecoes = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        EtiquetaAdd = new javax.swing.JTextField();
        MesAdd = new javax.swing.JTextField();
        AnoAdd = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        Prumo = new javax.swing.JComboBox<>();
        Fiacao = new javax.swing.JComboBox<>();
        Estado = new javax.swing.JComboBox<>();
        AddInspecao = new javax.swing.JButton();
        ds2 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        AnoAdd1 = new javax.swing.JTextField();
        MesAdd1 = new javax.swing.JTextField();
        EtiquetaAdd1 = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        Remove = new javax.swing.JButton();
        ds3 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        Insp = new javax.swing.JMenu();
        Mostra_insp = new javax.swing.JMenuItem();
        Menu_Botao_Relatorio = new javax.swing.JMenuItem();
        SisEletrico = new javax.swing.JMenu();
        Posts = new javax.swing.JMenu();
        Menu_Botao_mostrarPostes = new javax.swing.JMenuItem();
        Menu_Botao_adicionarPoste = new javax.swing.JMenuItem();
        Menu_PontosIlum = new javax.swing.JMenu();
        Menu_Botao_mostrarPontos = new javax.swing.JMenuItem();
        Menu_Botao_AdicionarPonto = new javax.swing.JMenuItem();
        Sup = new javax.swing.JMenu();
        MenuBotao_Sobre = new javax.swing.JMenuItem();

        GerarRelatorio_Dialogo.setMinimumSize(new java.awt.Dimension(225, 280));
        GerarRelatorio_Dialogo.setResizable(false);

        jPanel1.setMaximumSize(new java.awt.Dimension(245, 230));
        jPanel1.setMinimumSize(new java.awt.Dimension(245, 230));
        jPanel1.setPreferredSize(new java.awt.Dimension(245, 230));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        SeletorRelatorio.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        SeletorRelatorio.setMaximumSize(new java.awt.Dimension(221, 20));
        SeletorRelatorio.setMinimumSize(new java.awt.Dimension(221, 20));
        SeletorRelatorio.setPreferredSize(new java.awt.Dimension(221, 20));
        jPanel1.add(SeletorRelatorio, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 200, -1));

        jLabel1.setText("Data inicial");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, -1, -1));

        MesInicial.setText("MM");
        MesInicial.setMaximumSize(new java.awt.Dimension(28, 20));
        MesInicial.setMinimumSize(new java.awt.Dimension(28, 20));
        MesInicial.setName(""); // NOI18N
        MesInicial.setPreferredSize(new java.awt.Dimension(28, 20));
        MesInicial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MesInicialActionPerformed(evt);
            }
        });
        jPanel1.add(MesInicial, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, -1, -1));

        AnoInicial.setText("YYYY");
        AnoInicial.setMaximumSize(new java.awt.Dimension(37, 20));
        AnoInicial.setMinimumSize(new java.awt.Dimension(37, 20));
        AnoInicial.setName(""); // NOI18N
        AnoInicial.setPreferredSize(new java.awt.Dimension(37, 20));
        AnoInicial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AnoInicialActionPerformed(evt);
            }
        });
        jPanel1.add(AnoInicial, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 80, -1, -1));

        jLabel2.setText("Data final");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 60, -1, -1));

        MesFinal.setText("MM");
        MesFinal.setMaximumSize(new java.awt.Dimension(28, 20));
        MesFinal.setMinimumSize(new java.awt.Dimension(28, 20));
        MesFinal.setName(""); // NOI18N
        MesFinal.setPreferredSize(new java.awt.Dimension(28, 20));
        MesFinal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MesFinalActionPerformed(evt);
            }
        });
        jPanel1.add(MesFinal, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 80, -1, -1));

        AnoFinal.setText("YYYY");
        AnoFinal.setMaximumSize(new java.awt.Dimension(37, 20));
        AnoFinal.setMinimumSize(new java.awt.Dimension(37, 20));
        AnoFinal.setName(""); // NOI18N
        AnoFinal.setPreferredSize(new java.awt.Dimension(37, 20));
        AnoFinal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AnoFinalActionPerformed(evt);
            }
        });
        jPanel1.add(AnoFinal, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 80, -1, -1));

        jLabel7.setText("/");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 83, 20, -1));

        DialogoGerarRelatorio_Botao_Gerar.setText("Gerar");
        DialogoGerarRelatorio_Botao_Gerar.setMaximumSize(new java.awt.Dimension(59, 25));
        DialogoGerarRelatorio_Botao_Gerar.setMinimumSize(new java.awt.Dimension(59, 25));
        DialogoGerarRelatorio_Botao_Gerar.setPreferredSize(new java.awt.Dimension(59, 25));
        DialogoGerarRelatorio_Botao_Gerar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DialogoGerarRelatorio_Botao_GerarActionPerformed(evt);
            }
        });
        jPanel1.add(DialogoGerarRelatorio_Botao_Gerar, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 200, -1, -1));

        jLabel3.setText("Tipo de relatório");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 110, -1));

        jLabel4.setText("/");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 83, 10, -1));

        jLabel5.setText("até");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 83, -1, -1));

        javax.swing.GroupLayout GerarRelatorio_DialogoLayout = new javax.swing.GroupLayout(GerarRelatorio_Dialogo.getContentPane());
        GerarRelatorio_Dialogo.getContentPane().setLayout(GerarRelatorio_DialogoLayout);
        GerarRelatorio_DialogoLayout.setHorizontalGroup(
            GerarRelatorio_DialogoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        GerarRelatorio_DialogoLayout.setVerticalGroup(
            GerarRelatorio_DialogoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(GerarRelatorio_DialogoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        AddPoste.setMinimumSize(new java.awt.Dimension(350, 300));
        AddPoste.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel11.setText("Ponto de distribuição");
        AddPoste.getContentPane().add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 20, 110, -1));

        PSTAdd_PontoDist.setText("jTextField1");
        PSTAdd_PontoDist.setMaximumSize(new java.awt.Dimension(70, 20));
        PSTAdd_PontoDist.setMinimumSize(new java.awt.Dimension(70, 20));
        PSTAdd_PontoDist.setPreferredSize(new java.awt.Dimension(70, 20));
        PSTAdd_PontoDist.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PSTAdd_PontoDistActionPerformed(evt);
            }
        });
        AddPoste.getContentPane().add(PSTAdd_PontoDist, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 40, -1, -1));

        jLabel8.setText("Etiqueta");
        AddPoste.getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, -1, -1));

        jLabel10.setText("Latitude");
        AddPoste.getContentPane().add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, -1, -1));

        jLabel12.setText(" Longitude");
        AddPoste.getContentPane().add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 70, 60, -1));

        PSTAdd_Longi.setText("-00.000");
        PSTAdd_Longi.setMaximumSize(new java.awt.Dimension(70, 20));
        PSTAdd_Longi.setMinimumSize(new java.awt.Dimension(70, 20));
        PSTAdd_Longi.setPreferredSize(new java.awt.Dimension(70, 20));
        PSTAdd_Longi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PSTAdd_LongiActionPerformed(evt);
            }
        });
        AddPoste.getContentPane().add(PSTAdd_Longi, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 90, -1, -1));

        PSTAdd_Etiqueta.setText("et");
        PSTAdd_Etiqueta.setMinimumSize(new java.awt.Dimension(70, 20));
        PSTAdd_Etiqueta.setPreferredSize(new java.awt.Dimension(70, 20));
        AddPoste.getContentPane().add(PSTAdd_Etiqueta, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, -1, -1));

        jLabel9.setText("Material");
        AddPoste.getContentPane().add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, -1, -1));

        PSTAdd_Selector.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        PSTAdd_Selector.setMaximumSize(new java.awt.Dimension(70, 20));
        PSTAdd_Selector.setMinimumSize(new java.awt.Dimension(70, 20));
        PSTAdd_Selector.setPreferredSize(new java.awt.Dimension(70, 20));
        PSTAdd_Selector.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PSTAdd_SelectorActionPerformed(evt);
            }
        });
        AddPoste.getContentPane().add(PSTAdd_Selector, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 140, -1, -1));

        PSTAdd_Latit.setText("-00.000");
        PSTAdd_Latit.setMaximumSize(new java.awt.Dimension(70, 20));
        PSTAdd_Latit.setMinimumSize(new java.awt.Dimension(70, 20));
        PSTAdd_Latit.setPreferredSize(new java.awt.Dimension(70, 20));
        AddPoste.getContentPane().add(PSTAdd_Latit, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, -1, -1));

        Dialogo_Botao_CadastrarPoste.setText("Cadastrar");
        Dialogo_Botao_CadastrarPoste.setMaximumSize(new java.awt.Dimension(81, 25));
        Dialogo_Botao_CadastrarPoste.setMinimumSize(new java.awt.Dimension(81, 25));
        Dialogo_Botao_CadastrarPoste.setPreferredSize(new java.awt.Dimension(81, 25));
        AddPoste.getContentPane().add(Dialogo_Botao_CadastrarPoste, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 230, -1, -1));

        PST_Altura.setText("jTextField1");
        PST_Altura.setMaximumSize(new java.awt.Dimension(70, 20));
        PST_Altura.setMinimumSize(new java.awt.Dimension(70, 20));
        PST_Altura.setPreferredSize(new java.awt.Dimension(70, 20));
        AddPoste.getContentPane().add(PST_Altura, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 140, 70, -1));

        ds1.setText("jLabel6");
        AddPoste.getContentPane().add(ds1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 180, -1, -1));

        jLabel6.setText("Altura (m)");
        AddPoste.getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 120, -1, -1));

        AddPD.setMinimumSize(new java.awt.Dimension(350, 300));
        AddPD.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel13.setText("Capacidade (W)");
        AddPD.getContentPane().add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 30, 110, -1));

        CampoTexto_PDAdd_Capacidade.setText("jTextField1");
        CampoTexto_PDAdd_Capacidade.setMaximumSize(new java.awt.Dimension(70, 20));
        CampoTexto_PDAdd_Capacidade.setMinimumSize(new java.awt.Dimension(70, 20));
        CampoTexto_PDAdd_Capacidade.setPreferredSize(new java.awt.Dimension(70, 20));
        AddPD.getContentPane().add(CampoTexto_PDAdd_Capacidade, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 50, -1, -1));

        jLabel14.setText("Etiqueta");
        AddPD.getContentPane().add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, -1, -1));

        jLabel15.setText("Latitude");
        AddPD.getContentPane().add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, -1, -1));

        jLabel16.setText(" Longitude");
        AddPD.getContentPane().add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 80, 60, -1));

        PDAdd__Longi.setText("-00.000");
        PDAdd__Longi.setMaximumSize(new java.awt.Dimension(70, 20));
        PDAdd__Longi.setMinimumSize(new java.awt.Dimension(70, 20));
        PDAdd__Longi.setPreferredSize(new java.awt.Dimension(70, 20));
        PDAdd__Longi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PDAdd__LongiActionPerformed(evt);
            }
        });
        AddPD.getContentPane().add(PDAdd__Longi, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 100, -1, -1));

        CampoTexto_PDAdd_Etiqueta.setText("et");
        CampoTexto_PDAdd_Etiqueta.setMinimumSize(new java.awt.Dimension(70, 20));
        CampoTexto_PDAdd_Etiqueta.setPreferredSize(new java.awt.Dimension(70, 20));
        AddPD.getContentPane().add(CampoTexto_PDAdd_Etiqueta, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, -1, -1));

        PDAdd__Latit.setText("-00.000");
        PDAdd__Latit.setMaximumSize(new java.awt.Dimension(70, 20));
        PDAdd__Latit.setMinimumSize(new java.awt.Dimension(70, 20));
        PDAdd__Latit.setPreferredSize(new java.awt.Dimension(70, 20));
        AddPD.getContentPane().add(PDAdd__Latit, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 100, -1, -1));

        Dialogo_Botao_CadastrarPonto.setText("Cadastrar");
        Dialogo_Botao_CadastrarPonto.setMaximumSize(new java.awt.Dimension(81, 25));
        Dialogo_Botao_CadastrarPonto.setMinimumSize(new java.awt.Dimension(81, 25));
        Dialogo_Botao_CadastrarPonto.setPreferredSize(new java.awt.Dimension(81, 25));
        AddPD.getContentPane().add(Dialogo_Botao_CadastrarPonto, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 230, -1, -1));

        ds.setText("jLabel6");
        AddPD.getContentPane().add(ds, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 190, -1, -1));

        PDCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        AddPD.getContentPane().add(PDCombo, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 150, 70, -1));

        jLabel40.setText("Tipo");
        AddPD.getContentPane().add(jLabel40, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 130, -1, -1));

        Mostrar_PDs_Dialogo.setMinimumSize(new java.awt.Dimension(720, 480));
        Mostrar_PDs_Dialogo.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        ShowPDTable.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(ShowPDTable);

        Mostrar_PDs_Dialogo.getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, 680, 310));

        Dialogo_Botao_AdicionarPonto.setText("Adicionar novo");
        Mostrar_PDs_Dialogo.getContentPane().add(Dialogo_Botao_AdicionarPonto, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, -1, -1));

        jLabel27.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel27.setText("Pontos de Distribuição");
        Mostrar_PDs_Dialogo.getContentPane().add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, -1, -1));

        Mostrar_PSTs_Dialogo.setMinimumSize(new java.awt.Dimension(720, 480));
        Mostrar_PSTs_Dialogo.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Dialogo_Botao_AdicionarPoste.setText("Adicionar novo");
        Mostrar_PSTs_Dialogo.getContentPane().add(Dialogo_Botao_AdicionarPoste, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, -1, -1));

        ShowPSTable.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(ShowPSTable);

        Mostrar_PSTs_Dialogo.getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, 680, 310));

        jLabel26.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel26.setText("Postes de Energia");
        Mostrar_PSTs_Dialogo.getContentPane().add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, -1, -1));

        sob.setBackground(new java.awt.Color(81, 81, 81));
        sob.setForeground(new java.awt.Color(255, 120, 0));
        sob.setMinimumSize(new java.awt.Dimension(500, 262));
        sob.setModal(true);
        sob.setResizable(false);
        sob.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel3.setBackground(new java.awt.Color(81, 81, 81));
        jPanel3.setMaximumSize(new java.awt.Dimension(512, 594));
        jPanel3.setMinimumSize(new java.awt.Dimension(512, 594));
        jPanel3.setPreferredSize(new java.awt.Dimension(512, 594));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel29.setForeground(new java.awt.Color(255, 120, 0));
        jLabel29.setText("Xthora é uma denominação comercial de Jean Ricardo Jaques Moraes e Ivan Dias de Jesus Filho. ");
        jPanel3.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(23, 125, -1, -1));

        jLabel35.setForeground(new java.awt.Color(255, 255, 255));
        jPanel3.add(jLabel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 150, -1, 40));

        jLabel30.setForeground(new java.awt.Color(255, 120, 0));
        jLabel30.setText("JEAN RICARDO JAQUES MORAES");
        jPanel3.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(22, 55, -1, -1));

        jLabel31.setForeground(new java.awt.Color(255, 120, 0));
        jLabel31.setText("IVAN DIAS DE JESUS FILHO");
        jPanel3.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(22, 70, -1, -1));

        jLabel32.setForeground(new java.awt.Color(255, 120, 0));
        jLabel32.setText("Desenvolvido por Xthora:");
        jPanel3.add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(22, 40, -1, -1));

        jLabel33.setBackground(new java.awt.Color(81, 81, 81));
        jLabel33.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inspecoes/xths.png"))); // NOI18N
        jLabel33.setMaximumSize(new java.awt.Dimension(317, 224));
        jLabel33.setMinimumSize(new java.awt.Dimension(317, 224));
        jLabel33.setPreferredSize(new java.awt.Dimension(317, 224));
        jPanel3.add(jLabel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 90, -1, -1));

        Fecha.setBackground(new java.awt.Color(81, 81, 81));
        Fecha.setText("Fechar");
        jPanel3.add(Fecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 220, -1, -1));

        jLabel38.setForeground(new java.awt.Color(255, 120, 0));
        jLabel38.setText("Para suporte e sujestões envie um email para:");
        jPanel3.add(jLabel38, new org.netbeans.lib.awtextra.AbsoluteConstraints(22, 160, -1, -1));

        jLabel39.setForeground(new java.awt.Color(255, 120, 0));
        jLabel39.setText("xthoracorporation@gmail.com");
        jPanel3.add(jLabel39, new org.netbeans.lib.awtextra.AbsoluteConstraints(22, 175, -1, -1));

        jLabel37.setForeground(new java.awt.Color(255, 120, 0));
        jLabel37.setText("© 2018 Jean Ricardo Jaques Moraes e Ivan Dias de Jesus Filho. ");
        jPanel3.add(jLabel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, -1, -1));

        sob.getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 770, 290));

        Mostrar_Inspecoes.setMinimumSize(new java.awt.Dimension(720, 480));
        Mostrar_Inspecoes.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        ShowInsp.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane3.setViewportView(ShowInsp);

        Mostrar_Inspecoes.getContentPane().add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, 680, 310));

        jLabel36.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel36.setText("Inspeções");
        Mostrar_Inspecoes.getContentPane().add(jLabel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, -1, -1));

        jLabel34.setForeground(new java.awt.Color(240, 151, 0));
        jLabel34.setText("© 2018 Jean Ricardo Jaques Moraes.");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(399, 465));
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel28.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel28.setText("Inspeções");
        getContentPane().add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, -1, -1));

        Tabs.setMaximumSize(new java.awt.Dimension(730, 426));
        Tabs.setMinimumSize(new java.awt.Dimension(730, 426));
        Tabs.setPreferredSize(new java.awt.Dimension(375, 326));

        Inspecoes.setMaximumSize(new java.awt.Dimension(680, 390));
        Inspecoes.setMinimumSize(new java.awt.Dimension(680, 390));
        Inspecoes.setPreferredSize(new java.awt.Dimension(680, 390));
        Inspecoes.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel17.setText("Etiqueta (poste)");
        Inspecoes.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 15, -1, -1));

        jLabel18.setText("Data");
        Inspecoes.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 15, -1, -1));

        EtiquetaAdd.setText("jTextField1");
        EtiquetaAdd.setPreferredSize(new java.awt.Dimension(89, 20));
        Inspecoes.add(EtiquetaAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, -1, -1));

        MesAdd.setText("MM");
        MesAdd.setMaximumSize(new java.awt.Dimension(28, 20));
        MesAdd.setMinimumSize(new java.awt.Dimension(28, 20));
        MesAdd.setName(""); // NOI18N
        MesAdd.setPreferredSize(new java.awt.Dimension(28, 20));
        MesAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MesAddActionPerformed(evt);
            }
        });
        Inspecoes.add(MesAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 30, -1, -1));

        AnoAdd.setText("YYYY");
        AnoAdd.setMaximumSize(new java.awt.Dimension(37, 20));
        AnoAdd.setMinimumSize(new java.awt.Dimension(37, 20));
        AnoAdd.setName(""); // NOI18N
        AnoAdd.setPreferredSize(new java.awt.Dimension(37, 20));
        AnoAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AnoAddActionPerformed(evt);
            }
        });
        Inspecoes.add(AnoAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 30, 50, -1));

        jLabel22.setText("/");
        Inspecoes.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 30, 30, 20));

        jLabel19.setText("Prumo");
        Inspecoes.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 55, -1, -1));

        jLabel20.setText("Estado ");
        Inspecoes.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 55, -1, -1));

        jLabel21.setText("Fiação");
        Inspecoes.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 55, -1, -1));

        Prumo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        Prumo.setPreferredSize(new java.awt.Dimension(89, 20));
        Inspecoes.add(Prumo, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, -1, -1));

        Fiacao.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        Fiacao.setPreferredSize(new java.awt.Dimension(89, 20));
        Inspecoes.add(Fiacao, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 70, -1, -1));

        Estado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        Estado.setMaximumSize(new java.awt.Dimension(89, 20));
        Estado.setMinimumSize(new java.awt.Dimension(89, 20));
        Estado.setPreferredSize(new java.awt.Dimension(89, 20));
        Inspecoes.add(Estado, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 70, -1, -1));

        AddInspecao.setText("Registrar");
        Inspecoes.add(AddInspecao, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 260, -1, -1));

        ds2.setText("jLabel26");
        Inspecoes.add(ds2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, -1, -1));

        Tabs.addTab("Registrar inspeção", Inspecoes);

        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel23.setText("/");
        jPanel2.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 30, 30, 20));

        AnoAdd1.setText("YYYY");
        AnoAdd1.setMaximumSize(new java.awt.Dimension(37, 20));
        AnoAdd1.setMinimumSize(new java.awt.Dimension(37, 20));
        AnoAdd1.setName(""); // NOI18N
        AnoAdd1.setPreferredSize(new java.awt.Dimension(37, 20));
        AnoAdd1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AnoAdd1ActionPerformed(evt);
            }
        });
        jPanel2.add(AnoAdd1, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 30, 50, -1));

        MesAdd1.setText("MM");
        MesAdd1.setMaximumSize(new java.awt.Dimension(28, 20));
        MesAdd1.setMinimumSize(new java.awt.Dimension(28, 20));
        MesAdd1.setName(""); // NOI18N
        MesAdd1.setPreferredSize(new java.awt.Dimension(28, 20));
        MesAdd1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MesAdd1ActionPerformed(evt);
            }
        });
        jPanel2.add(MesAdd1, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 30, -1, -1));

        EtiquetaAdd1.setText("jTextField1");
        EtiquetaAdd1.setPreferredSize(new java.awt.Dimension(89, 20));
        jPanel2.add(EtiquetaAdd1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, -1, -1));

        jLabel24.setText("Etiqueta (poste)");
        jPanel2.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 15, -1, -1));

        jLabel25.setText("Data");
        jPanel2.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 15, -1, -1));

        Remove.setText("Remover");
        Remove.setMaximumSize(new java.awt.Dimension(77, 23));
        Remove.setMinimumSize(new java.awt.Dimension(77, 23));
        Remove.setPreferredSize(new java.awt.Dimension(77, 23));
        jPanel2.add(Remove, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 260, -1, -1));

        ds3.setText("jLabel26");
        jPanel2.add(ds3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, -1, -1));

        Tabs.addTab("Remover inspeção", jPanel2);

        getContentPane().add(Tabs, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, -1, -1));
        Tabs.getAccessibleContext().setAccessibleName("Postes");

        Insp.setText("Inspeções");

        Mostra_insp.setText("Relação de Inspeções");
        Insp.add(Mostra_insp);

        Menu_Botao_Relatorio.setText("Gerar Relatório");
        Insp.add(Menu_Botao_Relatorio);

        jMenuBar1.add(Insp);

        SisEletrico.setText("Rede Elétrica");

        Posts.setText("Postes");

        Menu_Botao_mostrarPostes.setText("Relação de Postes");
        Posts.add(Menu_Botao_mostrarPostes);

        Menu_Botao_adicionarPoste.setText("Cadastrar ");
        Posts.add(Menu_Botao_adicionarPoste);

        SisEletrico.add(Posts);

        Menu_PontosIlum.setText("Pontos de Distribuição");

        Menu_Botao_mostrarPontos.setText("Relação de Pontos");
        Menu_PontosIlum.add(Menu_Botao_mostrarPontos);

        Menu_Botao_AdicionarPonto.setText("Cadastrar");
        Menu_PontosIlum.add(Menu_Botao_AdicionarPonto);

        SisEletrico.add(Menu_PontosIlum);

        jMenuBar1.add(SisEletrico);

        Sup.setText("Ajuda");

        MenuBotao_Sobre.setText("Sobre");
        Sup.add(MenuBotao_Sobre);

        jMenuBar1.add(Sup);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void AnoInicialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AnoInicialActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_AnoInicialActionPerformed

    private void MesInicialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MesInicialActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_MesInicialActionPerformed

    private void AnoFinalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AnoFinalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_AnoFinalActionPerformed

    private void MesFinalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MesFinalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_MesFinalActionPerformed

    private void PSTAdd_LongiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PSTAdd_LongiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PSTAdd_LongiActionPerformed

    private void PSTAdd_SelectorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PSTAdd_SelectorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PSTAdd_SelectorActionPerformed

    private void PDAdd__LongiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PDAdd__LongiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PDAdd__LongiActionPerformed

    private void DialogoGerarRelatorio_Botao_GerarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DialogoGerarRelatorio_Botao_GerarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_DialogoGerarRelatorio_Botao_GerarActionPerformed

    private void PSTAdd_PontoDistActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PSTAdd_PontoDistActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PSTAdd_PontoDistActionPerformed

    private void MesAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MesAddActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_MesAddActionPerformed

    private void AnoAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AnoAddActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_AnoAddActionPerformed

    private void AnoAdd1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AnoAdd1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_AnoAdd1ActionPerformed

    private void MesAdd1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MesAdd1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_MesAdd1ActionPerformed

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
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                //new Menu().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AddInspecao;
    private javax.swing.JDialog AddPD;
    private javax.swing.JDialog AddPoste;
    private javax.swing.JTextField AnoAdd;
    private javax.swing.JTextField AnoAdd1;
    private javax.swing.JTextField AnoFinal;
    private javax.swing.JTextField AnoInicial;
    private javax.swing.JTextField CampoTexto_PDAdd_Capacidade;
    private javax.swing.JTextField CampoTexto_PDAdd_Etiqueta;
    private javax.swing.JButton DialogoGerarRelatorio_Botao_Gerar;
    private javax.swing.JButton Dialogo_Botao_AdicionarPonto;
    private javax.swing.JButton Dialogo_Botao_AdicionarPoste;
    private javax.swing.JButton Dialogo_Botao_CadastrarPonto;
    private javax.swing.JButton Dialogo_Botao_CadastrarPoste;
    private javax.swing.JComboBox<String> Estado;
    private javax.swing.JTextField EtiquetaAdd;
    private javax.swing.JTextField EtiquetaAdd1;
    private javax.swing.JButton Fecha;
    private javax.swing.JComboBox<String> Fiacao;
    private javax.swing.JDialog GerarRelatorio_Dialogo;
    private javax.swing.JMenu Insp;
    private javax.swing.JPanel Inspecoes;
    private javax.swing.JMenuItem MenuBotao_Sobre;
    private javax.swing.JMenuItem Menu_Botao_AdicionarPonto;
    private javax.swing.JMenuItem Menu_Botao_Relatorio;
    private javax.swing.JMenuItem Menu_Botao_adicionarPoste;
    private javax.swing.JMenuItem Menu_Botao_mostrarPontos;
    private javax.swing.JMenuItem Menu_Botao_mostrarPostes;
    private javax.swing.JMenu Menu_PontosIlum;
    private javax.swing.JTextField MesAdd;
    private javax.swing.JTextField MesAdd1;
    private javax.swing.JTextField MesFinal;
    private javax.swing.JTextField MesInicial;
    private javax.swing.JMenuItem Mostra_insp;
    private javax.swing.JDialog Mostrar_Inspecoes;
    private javax.swing.JDialog Mostrar_PDs_Dialogo;
    private javax.swing.JDialog Mostrar_PSTs_Dialogo;
    private javax.swing.JTextField PDAdd__Latit;
    private javax.swing.JTextField PDAdd__Longi;
    private javax.swing.JComboBox<String> PDCombo;
    private javax.swing.JTextField PSTAdd_Etiqueta;
    private javax.swing.JTextField PSTAdd_Latit;
    private javax.swing.JTextField PSTAdd_Longi;
    private javax.swing.JTextField PSTAdd_PontoDist;
    private javax.swing.JComboBox<String> PSTAdd_Selector;
    private javax.swing.JTextField PST_Altura;
    private javax.swing.JMenu Posts;
    private javax.swing.JComboBox<String> Prumo;
    private javax.swing.JButton Remove;
    private javax.swing.JComboBox<String> SeletorRelatorio;
    private javax.swing.JTable ShowInsp;
    private javax.swing.JTable ShowPDTable;
    private javax.swing.JTable ShowPSTable;
    private javax.swing.JMenu SisEletrico;
    private javax.swing.JMenu Sup;
    private javax.swing.JTabbedPane Tabs;
    private javax.swing.JLabel ds;
    private javax.swing.JLabel ds1;
    private javax.swing.JLabel ds2;
    private javax.swing.JLabel ds3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JDialog sob;
    // End of variables declaration//GEN-END:variables
}
