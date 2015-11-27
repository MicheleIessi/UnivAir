/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package univair.Presentation;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;
import javax.imageio.ImageIO;
import javax.swing.*;
import univair.Control.ResultControl;
import univair.Entity.Route;

/**
 *
 * @author Michele
 */
public class ResultPanel extends JFrame {
    
    public ResultPanel(ArrayList list, Route r, String d) {
        dep = r.getDeparture();
        des = r.getDestination();
        initComponents(list);
        setVisible(true);
    }
    
    public void initComponents(ArrayList lst) {
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
        this.setTitle("Risultati ricerca");
        try {
            this.setIconImage(ImageIO.read(new File("src\\univair\\Presentation\\Images\\airunivaqicon.png")));
        } catch(IOException ex) {
            System.out.println("Immagine non trovata - airunivaqicon.png");
        }
        this.setBounds(283, 84, 800, 600);
        this.setMinimumSize(new Dimension(620,420));
        this.setPreferredSize(new Dimension(620,500));
        this.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        BorderLayout border = new BorderLayout();
        Container content = this.getContentPane();
        content.setLayout(border);
        //</editor-fold>
        
        //<editor-fold desc="Creo gli elementi da inserire nella JFrame e ne definisco le proprietà">
        
        //north
        /* immagine */
        imagePanel = new JPanel();
        try {
            BufferedImage image = ImageIO.read(new File("src\\univair\\Presentation\\Images\\airunivaq.png"));
            JLabel picLabel = new JLabel(new ImageIcon(image));
            imagePanel.add(picLabel);
        } catch (IOException ex) { 
            System.out.println("Immagine non trovata - airunivaq.png"); 
        }        
        //center
        /* primo jpanel*/
        centralPanel = new JPanel();
        centralPanel.setAlignmentX(CENTER_ALIGNMENT);
        centralPanel.setLayout(new BoxLayout(centralPanel,BoxLayout.Y_AXIS));
        /* prima label */
        detailLabel = new JLabel("Risultati ricerca: voli da " + dep + " a " + des + ":");
        detailLabel.setAlignmentX(CENTER_ALIGNMENT);
        detailLabel.setFont(new Font("Arial",Font.ITALIC,20));
        /* prima jlist */
        scrollPane = new JScrollPane();
        resultList = new JList(new Vector<HashMap>(lst));
        resultList.setVisibleRowCount(10);
        resultList.setSize(500, 300);
        resultList.setToolTipText("Selezionare un'opzione e premere il tasto \"Prenota\"");
        resultList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                Component renderer = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if(renderer instanceof JLabel && value instanceof HashMap) {
                    HashMap v = (HashMap) value;
                    String desc = "Volo n°: " + v.get("ID").toString() + " " + dep + " - " + des + ", " + v.get("seats") + " posti disponibili";
                    ((JLabel) renderer).setText(desc);
                }                 
                return renderer;
            }
        });
        resultList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        resultList.setLayoutOrientation(JList.VERTICAL);
        resultList.setFont(new Font("Times New Roman",Font.PLAIN,20));
        resultList.setSelectedIndex(0);
        scrollPane.setViewportView(resultList);
        //south
        bookButton = new JButton("Prenota");
        bookButton.setAlignmentX(CENTER_ALIGNMENT);
        bookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bookButtonAction(e);
            }
        });
        
        //</editor-fold>
        
        //<editor-fold desc="Inserisco gli elementi appena creati nella jframe">
        
        centralPanel.add(detailLabel);
        centralPanel.add(resultList);
        content.add(imagePanel, BorderLayout.NORTH);
        content.add(centralPanel, BorderLayout.CENTER);
        content.add(bookButton, BorderLayout.SOUTH);
        
        //</editor-fold>
    }
    
    public void bookButtonAction(java.awt.event.ActionEvent evt) {
        HashMap map = (HashMap) resultList.getSelectedValue();
        
        new ResultControl(map);
    }
        /* attributi di swing */
    private JPanel imagePanel;
    private JPanel centralPanel;
    private JLabel detailLabel;
    private JList resultList;
    private String dep,des;
    private JScrollPane scrollPane;
    private JButton bookButton;

}
