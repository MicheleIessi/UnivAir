/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package univair.Presentation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import org.jdesktop.swingx.JXDatePicker;
import univair.Control.SearchControl;
import univair.Entity.City;

/**
 *
 * @author Michele
 */
public class HomePanel extends javax.swing.JFrame {
    
    public HomePanel() {
        initComponents();
        this.setVisible(true);
    }

    public void initComponents() {
        // <editor-fold desc="Definisco le proprietà della JFrame">
        
        try {
            for(javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if("Nimbus".equals(info.getName())){
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch(ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(HomePanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        this.setTitle("UnivAir");
        try {
            this.setIconImage(ImageIO.read(new File("src\\univair\\Presentation\\Images\\airunivaqicon.png")));
        } catch(IOException ex) {
            System.out.println("Immagine non trovata - airunivaqicon.png");
        }
        this.setBounds(283, 84, 800, 600);
        this.setMinimumSize(new Dimension(420,420));
        this.setPreferredSize(new Dimension(420,500));
        this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        BorderLayout border = new BorderLayout();
        Container content = this.getContentPane();
        content.setLayout(border);
        
        // </editor-fold>
        
        // <editor-fold desc="Creo gli elementi da inserire nella JFrame e ne definisco le proprietà">
        
        // north
        /* immagine */
        imagePanel = new JPanel();
        try {
            BufferedImage image = ImageIO.read(new File("src\\univair\\Presentation\\Images\\airunivaq.png"));
            JLabel picLabel = new JLabel(new ImageIcon(image));
            imagePanel.add(picLabel);
        } catch (IOException ex) { 
            System.out.println("Immagine non trovata - airunivaq.png"); 
        }        
        // center
        /* primo jpanel */
        centralPanel = new JPanel();
        centralPanel.setLayout(new BoxLayout(centralPanel, BoxLayout.Y_AXIS));
        centralPanel.setAlignmentX(CENTER_ALIGNMENT);        
        /* prima label */
        titleLabel = new JLabel("UnivAir");
        titleLabel.setFont(new Font("Arial",Font.BOLD,35));
        titleLabel.setForeground(Color.blue);
        titleLabel.setAlignmentX(CENTER_ALIGNMENT);
        /* seconda label */
        descriptionLabel = new JLabel("Servizio per la prenotazione di biglietti aerei");
        descriptionLabel.setAlignmentX(CENTER_ALIGNMENT);
        descriptionLabel.setFont(new Font("Serif",Font.ITALIC,20));
        descriptionLabel.setForeground(Color.blue);
        /* date picker */
        datePicker = new JXDatePicker(new Date());
        /* terza label */
        promptLabel = new JLabel("Selezionare la citta' di partenza e di arrivo");
        promptLabel.setAlignmentX(CENTER_ALIGNMENT);
        promptLabel.setFont(infoFont);
        promptLabel.setForeground(Color.blue);        
        /* secondo jpanel */
        depPanel = new JPanel();
        depPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        depPanel.setAlignmentX(CENTER_ALIGNMENT);
        /* quarta label */
        depLabel = new JLabel("Città di partenza:");
        depLabel.setAlignmentX(CENTER_ALIGNMENT);
        depLabel.setFont(infoFont);
        depLabel.setForeground(Color.blue);
        /* prima combobox */
        depBox = new JComboBox<>();
        depBox.setModel(new DefaultComboBoxModel<>(City.values()));
        depBox.setAlignmentX(CENTER_ALIGNMENT);
        depBox.setMaximumRowCount(5);
        /* terzo jpanel */
        desPanel = new JPanel();
        desPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        desPanel.setAlignmentX(CENTER_ALIGNMENT);
        /* quinta label */
        desLabel = new JLabel("Città di arrivo:");
        desLabel.setAlignmentX(CENTER_ALIGNMENT);
        desLabel.setFont(infoFont);
        desLabel.setForeground(Color.blue);
        /* seconda combobox */
        desBox = new JComboBox<>();
        desBox.setModel(new DefaultComboBoxModel<>(City.values()));
        desBox.setSelectedIndex(1);
        desBox.setAlignmentX(CENTER_ALIGNMENT);
        desBox.setMaximumRowCount(5);
        /* sesta label */
        dateLabel = new JLabel("Selezionare la data:");
        dateLabel.setAlignmentX(CENTER_ALIGNMENT);
        dateLabel.setFont(infoFont);
        dateLabel.setForeground(Color.blue);
        /* primo button */
        searchButton = new JButton("Cerca volo");
        searchButton.setAlignmentX(CENTER_ALIGNMENT);
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchButtonAction(e);
            }
        });
        /* secondo button */
        loginButton = new JButton("Login");
        loginButton.setAlignmentX(CENTER_ALIGNMENT);
        //</editor-fold>
            
        //<editor-fold desc="Inserisco gli elementi appena creati nella JFrame">
        
        depPanel.add(depLabel);
        depPanel.add(depBox);
        desPanel.add(desLabel);
        desPanel.add(desBox);
        centralPanel.add(descriptionLabel);
        centralPanel.add(Box.createVerticalStrut(30));
        centralPanel.add(promptLabel);
        centralPanel.add(depPanel);
        centralPanel.add(desPanel);
        centralPanel.add(dateLabel);
        centralPanel.add(datePicker);
        centralPanel.add(Box.createVerticalGlue());
        centralPanel.add(searchButton);
        centralPanel.add(loginButton);
        
        content.add(imagePanel,BorderLayout.NORTH);        
        content.add(centralPanel, BorderLayout.CENTER);
        content.add(new JPanel(), BorderLayout.SOUTH);
        //</editor-fold>
        
        pack();
    }
    /* ACTION LISTENERS */
    
    private void searchButtonAction(java.awt.event.ActionEvent evt) {
        Date dat = datePicker.getDate();
        
        String sdat = (dat.getYear()+1900)+"-"+(dat.getMonth()+1)+"-"+dat.getDate();
        String dep = depBox.getSelectedItem().toString();
        String des = desBox.getSelectedItem().toString();
        
        new SearchControl(dep, des, sdat);
    }
    
    /* attributi di swing */    
    private JComboBox<City> depBox;
    private JComboBox<City> desBox;
    private JXDatePicker datePicker;
    private JPanel imagePanel;
    private JPanel centralPanel;
    private JPanel depPanel;
    private JPanel desPanel;
    private JLabel titleLabel;
    private JLabel descriptionLabel;
    private JLabel promptLabel;
    private JLabel depLabel;
    private JLabel desLabel;
    private JLabel dateLabel;
    private JButton searchButton;
    private JButton loginButton;
    private final Font infoFont = new Font("Times New Roman",Font.ITALIC, 16);
}