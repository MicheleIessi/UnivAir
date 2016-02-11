/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package univair.Presentation;

import java.awt.BorderLayout;
import java.awt.Color;
import static java.awt.Component.CENTER_ALIGNMENT;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UnsupportedLookAndFeelException;
import org.jdesktop.swingx.JXDatePicker;
import univair.Control.AddControl;
import univair.Entity.City;

/**
 *
 * @author Michele
 */
public class AdminPanel extends JFrame {
    public AdminPanel(String n) {
        nome = n;
        initComponents();
        this.setVisible(true);
    }
    
    private void initComponents() {
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
        this.setTitle("Univair: benvenuto " + nome);
        try {
            this.setIconImage(ImageIO.read(new File("src\\univair\\Presentation\\Images\\airunivaqicon.png")));
        } catch(IOException ex) {
            System.out.println("Immagine non trovata - airunivaqicon.png");
        }
        this.setBounds(473, 214, 420, 300);
        this.setMinimumSize(new Dimension(420,300));
        this.setPreferredSize(new Dimension(420,300));
        this.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        BorderLayout border = new BorderLayout();
        Container content = this.getContentPane();
        content.setLayout(border);
        //</editor-fold>
        
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
        //center
        centralPanel = new JPanel();
        centralPanel.setLayout(new BoxLayout(centralPanel,BoxLayout.Y_AXIS));
        centralPanel.setAlignmentX(CENTER_ALIGNMENT);
        
        routeButton = new JButton("Aggiungi rotta");
        routeButton.setAlignmentX(CENTER_ALIGNMENT);
        routeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                redrawRouteAction();
            }
        });
        flightButton = new JButton("Aggiungi volo");
        flightButton.setAlignmentX(CENTER_ALIGNMENT);
        flightButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                redrawFlightAction();
            }
        });
        detailButton = new JButton("Dettagli su una rotta/volo");
        detailButton.setAlignmentX(CENTER_ALIGNMENT);
        detailButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                redrawDetailAction();
            }
        });
        cancelButton.setAlignmentX(CENTER_ALIGNMENT);
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cancelAction(0);
            }
        });
        //</editor-fold>
        centralPanel.add(routeButton);
        centralPanel.add(flightButton);
        centralPanel.add(detailButton);
        centralPanel.add(new JPanel());
        centralPanel.add(cancelButton);
        content.add(imagePanel,BorderLayout.NORTH);
        content.add(centralPanel,BorderLayout.CENTER);
        
        initializeOthers();
        pack();
    }
    /**
     * Il metodo initializeOthers inizializza tutti i componenti che potrebbero
     * venire agganciati al container 'centralPanel' tramite una delle 3 scelte.
     */
    private void initializeOthers() {
        
        depBox = new JComboBox<>();
        depBox.setModel(new DefaultComboBoxModel<>(City.values()));
        depBox.setAlignmentX(CENTER_ALIGNMENT);
        depBox.setMaximumRowCount(5);
        desBox = new JComboBox<>();
        desBox.setModel(new DefaultComboBoxModel<>(City.values()));
        desBox.setAlignmentX(CENTER_ALIGNMENT);
        desBox.setSelectedIndex(1);
        desBox.setMaximumRowCount(5);
        idPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        idPanel.setAlignmentX(CENTER_ALIGNMENT);
        idTF = new JTextField("ID",10);
        idTF.setForeground(Color.lightGray);
        idTF.addFocusListener(new FocusListener() {
        @Override
        public void focusGained(FocusEvent e) {
            if("ID".equals(idTF.getText())) {
                idTF.setText("");
                idTF.setForeground(Color.black);
                }
            }
        @Override
        public void focusLost(FocusEvent e) {
            if("".equals(idTF.getText())) {
                idTF.setText("ID");
                idTF.setForeground(Color.lightGray);
                }
            }
        });
        idTF.addKeyListener(new KeyListener() {
        @Override
        public void keyTyped(KeyEvent e) {
            if(!Character.isDigit(e.getKeyChar())) {
                e.consume();
            }
        }
        @Override
            public void keyPressed(KeyEvent e) {}
        @Override
            public void keyReleased(KeyEvent e) {}
        });
        datePicker = new JXDatePicker(new Date());
        infoLabel1 = new JLabel("Partenza:");
        infoLabel1.setForeground(Color.blue);
        infoLabel1.setFont(infoFont);
        infoLabel2 = new JLabel("Destinazione:");
        infoLabel2.setForeground(Color.blue);
        infoLabel2.setFont(infoFont);
        infoLabel3 = new JLabel("ID:");
        infoLabel3.setForeground(Color.blue);
        infoLabel3.setFont(infoFont);
        cancelButton1.setAlignmentX(CENTER_ALIGNMENT);
        cancelButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cancelAction(1);
                }
            });   
        choicePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        choicePanel.setAlignmentX(CENTER_ALIGNMENT);
        
    }
    
    private void redrawRouteAction() {
        
        this.setTitle("Aggiunta rotta");
        centralPanel.removeAll();
        confirmButton = new JButton("Aggiungi rotta");
        confirmButton.setAlignmentX(CENTER_ALIGNMENT);
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                routeConfirmAction();
                }
            });
        choicePanel.add(infoLabel1);
        choicePanel.add(depBox);
        choicePanel.add(infoLabel2);
        choicePanel.add(desBox);
        centralPanel.add(choicePanel);
        idPanel.add(infoLabel3);
        idPanel.add(idTF);
        centralPanel.add(idPanel);
        centralPanel.add(confirmButton);
        centralPanel.add(cancelButton1);
        this.pack();
        
    }
    private void redrawFlightAction() {
        
        this.setTitle("Aggiunta volo");
        centralPanel.removeAll();
        confirmButton1 = new JButton("Aggiungi volo");
        confirmButton1.setAlignmentX(CENTER_ALIGNMENT);
        confirmButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                flightConfirmAction();
            }
        });        
        choicePanel.add(infoLabel1);
        choicePanel.add(depBox);
        choicePanel.add(infoLabel2);
        choicePanel.add(desBox);
        centralPanel.add(choicePanel);
        infoLabel3.setText("Data:");
        idPanel.add(infoLabel3);
        idPanel.add(datePicker);
        centralPanel.add(choicePanel);
        centralPanel.add(idPanel);
        centralPanel.add(confirmButton1);
        centralPanel.add(cancelButton1);
        this.pack();
        
    }
    private void redrawDetailAction() {
        centralPanel.removeAll();
    }
    private void cancelAction(int i) {
        if(i==1) {
            centralPanel.removeAll();
            initComponents();
        }
        else this.dispose();
    }
    /* METODI PER AGGIUNTA DI ROTTE/VOLI */
    private void routeConfirmAction() {
        try {
            if(isInteger(idTF.getText()))
                new AddControl(depBox.getSelectedItem().toString(),desBox.getSelectedItem().toString(),Integer.parseInt(idTF.getText()));
            else throw new IllegalArgumentException("Inserire un ID valido");
        } catch (IllegalArgumentException e) {
            new MessageFrame(e.getMessage(),0);
        }
    }
    
    private void flightConfirmAction() {
        try {
            Date dat = datePicker.getDate();
            String sdat = (dat.getYear()+1900)+"-"+(dat.getMonth()+1)+"-"+dat.getDate();
            new AddControl(depBox.getSelectedItem().toString(),desBox.getSelectedItem().toString(),sdat);
        } catch(IllegalArgumentException e) {
            new MessageFrame(e.getMessage(),0);
        }
    }
    /* metodo isInteger */
    private static boolean isInteger(String str) {
        if (str == null) {
            return false;
        }
        int length = str.length();
        if (length == 0) {
            return false;
        }
        int i = 0;
        if (str.charAt(0) == '-') {
            if (length == 1) {
                return false;
            }
            i = 1;
        }
        for (; i < length; i++) {
            char c = str.charAt(i);
            if (c < '0' || c > '9') {
                return false;
            }
        }
        return true;
    }
    
    private final String nome;
    private JPanel imagePanel;
    private JPanel centralPanel;
    //caso generale
        private JLabel infoLabel1;
        private JLabel infoLabel2;
        private JLabel infoLabel3;
        private JButton routeButton;
        private JButton flightButton;
        private JButton detailButton;
        private JButton confirmButton;
        private JButton confirmButton1;
        private final JButton cancelButton = new JButton("Annulla");
        private final JButton cancelButton1= new JButton("Annulla");
        
    //aggiunta rotta
        private JTextField idTF;
        private JPanel idPanel;
        private JPanel choicePanel;
            private JComboBox<City> depBox;
            private JComboBox<City> desBox;
    //aggiunta volo
        private JXDatePicker datePicker;
        
        
        
    private final Font infoFont = new Font("Times New Roman",Font.ITALIC, 16);    
}
