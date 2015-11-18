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
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.UnsupportedLookAndFeelException;
import org.jdesktop.swingx.JXDatePicker;
import univair.Entity.Flight;
import univair.Entity.Route;

/**
 *
 * @author Michele
 */
public class BookPanel extends JFrame {
    
    public BookPanel(HashMap map){
        
        
        initComponents(map);
        this.setVisible(true);
    }
    
    public void initComponents(HashMap map) {
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
        int id = 0;
        Route r = new Route();
        try {
            id = Flight.getRouteID(Integer.parseInt((String)map.get("ID")));
            r.createFromDB(id);
        } catch (SQLException e) {}
        this.setTitle("Prenotazione: volo "+map.get("ID")+" "+r.getDeparture()+"-"+r.getDestination());
        try {
            this.setIconImage(ImageIO.read(new File("src\\univair\\Presentation\\Images\\airunivaqicon.png")));
        } catch(IOException ex) {
            System.out.println("Immagine non trovata - airunivaqicon.png");
        }
        this.setBounds(400, 70, 400, 600);
        this.setMinimumSize(new Dimension(550,600));
        this.setPreferredSize(new Dimension(600,768));
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
        centralPanel = new JPanel();
        centralPanel.setLayout(new BoxLayout(centralPanel, BoxLayout.Y_AXIS));
        centralPanel.setAlignmentX(CENTER_ALIGNMENT);        
        detailPanel = new JPanel();
        detailPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        detailPanel.setAlignmentX(CENTER_ALIGNMENT);
        detailPanel.setBorder(BorderFactory.createTitledBorder("Dettagli di prenotazione"));
        detailPanel.setPreferredSize(new Dimension(380, 120));
        detailPanel.setMaximumSize(new Dimension(380, 120));
        mealPanel = new JPanel();
        mealPanel.setAlignmentX(CENTER_ALIGNMENT);
        mealPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
        mealLabel = new JLabel("Pasto a bordo: ");
        mealLabel.setAlignmentX(CENTER_ALIGNMENT);
        mealBox = new JCheckBox();
        mealBox.setSelected(false);
        animalPanel = new JPanel();
        animalPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
        animalPanel.setAlignmentX(CENTER_ALIGNMENT);
        animalLabel = new JLabel("Servizio animali: ");
        animalLabel.setAlignmentX(CENTER_ALIGNMENT);
        animalBox = new JCheckBox();
        animalBox.setSelected(false);
        luggagePanel = new JPanel();
        luggagePanel.setLayout(new FlowLayout(FlowLayout.LEADING));
        luggagePanel.setAlignmentX(CENTER_ALIGNMENT);
        luggageLabel = new JLabel("Bagaglio addizionale: ");
        luggageLabel.setAlignmentX(CENTER_ALIGNMENT);
        luggageBox = new JCheckBox();
        luggageBox.setSelected(false);
        magazinePanel = new JPanel();
        magazinePanel.setLayout(new FlowLayout(FlowLayout.LEADING));
        magazinePanel.setAlignmentX(CENTER_ALIGNMENT);
        magazineLabel = new JLabel("Servizio animali: ");
        magazineLabel.setAlignmentX(CENTER_ALIGNMENT);
        magazineBox = new JCheckBox();
        magazineBox.setSelected(false);
        infoLabel = new JLabel("Dati prenotazione:");
        infoLabel.setAlignmentX(CENTER_ALIGNMENT);
        infoLabel.setFont(new Font("Times New Roman", Font.ITALIC, 14));
        infoLabel.setForeground(Color.blue);
                
        
        anagrafePanel = new JPanel();
        anagrafePanel.setLayout(new FlowLayout(FlowLayout.LEADING));
        
        personPanel1 = new JPanel();
        personPanel1.setLayout(new FlowLayout(FlowLayout.LEADING));
        personPanel1.setAlignmentX(TOP_ALIGNMENT);
        
        namePanel = new JPanel();
        namePanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        namePanel.setAlignmentX(CENTER_ALIGNMENT);
        
        nameLabel = new JLabel("Nome:");
        nameLabel.setFont(new Font("Times New Roman",Font.ITALIC,12));
        nameLabel.setForeground(Color.blue);
        nameLabel.setAlignmentX(RIGHT_ALIGNMENT);
        
        nameTextField = new JTextField("Nome",12);
        nameTextField.setForeground(Color.lightGray);
        nameTextField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if("Nome".equals(nameTextField.getText())) {
                    nameTextField.setText("");
                    nameTextField.setForeground(Color.black);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if("".equals(nameTextField.getText())) {
                    nameTextField.setText("Nome");
                    nameTextField.setForeground(Color.lightGray);
                }
            }
        });
        
        surnamePanel = new JPanel();
        surnamePanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        surnamePanel.setAlignmentX(CENTER_ALIGNMENT);
        
        surnameLabel = new JLabel("Cognome:");
        surnameLabel.setFont(new Font("Times New Roman",Font.ITALIC,12));
        surnameLabel.setForeground(Color.blue);

        surnameTextField = new JTextField("Cognome",12);
        surnameTextField.setForeground(Color.lightGray);
        surnameTextField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if("Cognome".equals(surnameTextField.getText())) {
                    surnameTextField.setText("");
                    surnameTextField.setForeground(Color.black);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if("".equals(surnameTextField.getText())) {
                    surnameTextField.setText("Cognome");
                    surnameTextField.setForeground(Color.lightGray);
                }
            }
        });

        birthCityPanel = new JPanel();
        birthCityPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        birthCityPanel.setAlignmentX(CENTER_ALIGNMENT);
        
        birthCityLabel = new JLabel("Città di nascita:");
        birthCityLabel.setFont(new Font("Times New Roman",Font.ITALIC,12));
        birthCityLabel.setForeground(Color.blue);
        
        birthCityTextField = new JTextField("Città di nascita",12);
        birthCityTextField.setForeground(Color.lightGray);
        birthCityTextField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
               if("Città di nascita".equals(birthCityTextField.getText())) {
                   birthCityTextField.setText("");
                   birthCityTextField.setForeground(Color.black);
               }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if("".equals(birthCityTextField.getText())) {
                    birthCityTextField.setText("Città di nascita");
                    birthCityTextField.setForeground(Color.lightGray);
                }
            }
        });
        
        labelPanel1 = new JPanel();
        labelPanel1.setLayout(new BoxLayout(labelPanel1,BoxLayout.Y_AXIS));
        labelPanel1.setAlignmentX(RIGHT_ALIGNMENT);
        
        textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel,BoxLayout.Y_AXIS));
        textPanel.setAlignmentX(LEFT_ALIGNMENT);
        
        personPanel2 = new JPanel();
        personPanel2.setLayout(new FlowLayout(FlowLayout.LEADING));
        personPanel2.setAlignmentX(TOP_ALIGNMENT);
        
        labelPanel2 = new JPanel();
        labelPanel2.setLayout(new BoxLayout(labelPanel2,BoxLayout.Y_AXIS));
        labelPanel2.setAlignmentX(RIGHT_ALIGNMENT);
        
        sexPanel = new JPanel();
        sexPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        sexPanel.setAlignmentX(CENTER_ALIGNMENT);
        
        sexLabel = new JLabel("Sesso:");
        sexLabel.setFont(new Font("Times New Roman",Font.ITALIC,12));
        sexLabel.setForeground(Color.blue);
        
        birthDatePanel = new JPanel();
        birthDatePanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        birthDatePanel.setAlignmentX(CENTER_ALIGNMENT);
        
        birthDateLabel = new JLabel("Data di nascita:");
        birthDateLabel.setFont(new Font("Times New Roman",Font.ITALIC,12));
        birthDateLabel.setForeground(Color.blue);
        
        mailPanel = new JPanel();
        mailPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        mailPanel.setAlignmentX(CENTER_ALIGNMENT);
        
        mailLabel = new JLabel("E-Mail:");
        mailLabel.setFont(new Font("Times New Roman",Font.ITALIC,12));
        mailLabel.setForeground(Color.blue);
        
        radioDateTextPanel = new JPanel();
        radioDateTextPanel.setLayout(new BoxLayout(radioDateTextPanel,BoxLayout.Y_AXIS));
        radioDateTextPanel.setAlignmentX(CENTER_ALIGNMENT);
        
        radioPanel = new JPanel();
        radioPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        radioPanel.setAlignmentX(CENTER_ALIGNMENT);

        maleRadio = new JRadioButton("M",true);
        femaleRadio = new JRadioButton("F",false);
        ButtonGroup mf = new ButtonGroup();
        mf.add(maleRadio);
        mf.add(femaleRadio);
        
        datePicker = new JXDatePicker(new Date());
        
        mailTextField = new JTextField("E-Mail");
        mailTextField.setForeground(Color.lightGray);
        mailTextField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if("E-Mail".equals(mailTextField.getText())) {
                    mailTextField.setText("");
                    mailTextField.setForeground(Color.black);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if("".equals(mailTextField.getText())) {
                    mailTextField.setText("E-Mail");
                    mailTextField.setForeground(Color.lightGray);
                }
            }
        });
        
        
        
        
        
        
        
        
        
        
        
        
        
        //</editor-fold>
        
        

        
        
        namePanel.add(nameLabel);
        surnamePanel.add(surnameLabel);
        birthCityPanel.add(birthCityLabel);
        labelPanel1.add(namePanel);
        labelPanel1.add(surnamePanel);
        labelPanel1.add(birthCityPanel);
        textPanel.add(nameTextField);
        textPanel.add(surnameTextField);
        textPanel.add(birthCityTextField);
        
        sexPanel.add(sexLabel);
        birthDatePanel.add(birthDateLabel);
        mailPanel.add(mailLabel);
        labelPanel2.add(sexPanel);
        labelPanel2.add(birthDatePanel);
        labelPanel2.add(mailPanel);
        radioPanel.add(maleRadio);
        radioPanel.add(femaleRadio);
        radioDateTextPanel.add(radioPanel);
        radioDateTextPanel.add(datePicker);
        radioDateTextPanel.add(mailTextField);
        
        personPanel1.add(labelPanel1);
        personPanel1.add(textPanel);
        personPanel2.add(labelPanel2);
        personPanel2.add(radioDateTextPanel);
        
        anagrafePanel.add(personPanel1);
        anagrafePanel.add(personPanel2);
        
        mealPanel.add(mealLabel);
        mealPanel.add(mealBox);
        animalPanel.add(animalLabel);
        animalPanel.add(animalBox);
        luggagePanel.add(luggageLabel);
        luggagePanel.add(luggageBox);
        magazinePanel.add(magazineLabel);
        magazinePanel.add(magazineBox);
        
        detailPanel.add(mealPanel);
        detailPanel.add(animalPanel);
        detailPanel.add(luggagePanel);
        detailPanel.add(magazinePanel);
        
        centralPanel.add(infoLabel);
        centralPanel.add(anagrafePanel);
        centralPanel.add(detailPanel);
        
        content.add(centralPanel,BorderLayout.CENTER);
        content.add(imagePanel,BorderLayout.NORTH);
        
        
    }
    
    
    
    
    
    
    
    private JPanel imagePanel;
    private JPanel centralPanel;
    private JPanel detailPanel;
    private JPanel mealPanel;
    private JPanel animalPanel;
    private JPanel luggagePanel;
    private JPanel magazinePanel;
    
    
    private JPanel anagrafePanel;   //flowlayout
        private JPanel personPanel1;     //flowlayout
            private JPanel labelPanel1;  //boxLayout
                private JPanel namePanel;       //flowlayout
                    private JLabel nameLabel;
                private JPanel surnamePanel;    //flowlayout
                    private JLabel surnameLabel;  
                private JPanel birthCityPanel;  //flowlayout
                    private JLabel birthCityLabel;
            private JPanel textPanel;                    
                private JTextField nameTextField;
                private JTextField surnameTextField;
                private JTextField birthCityTextField;
        private JPanel personPanel2;
            private JPanel labelPanel2;
                private JPanel sexPanel;
                    private JLabel sexLabel;
                private JPanel birthDatePanel;
                    private JLabel birthDateLabel;
                private JPanel mailPanel;
                    private JLabel mailLabel;
            private JPanel radioDateTextPanel;
                private JPanel radioPanel;
                    private JRadioButton maleRadio;
                    private JRadioButton femaleRadio;
                private JXDatePicker datePicker;
                private JTextField mailTextField;
                
    private JLabel infoLabel;
    private JLabel priceLabel;
    private JLabel mealLabel;
    private JLabel animalLabel;
    private JLabel luggageLabel;
    private JLabel magazineLabel;
    private JCheckBox mealBox;
    private JCheckBox animalBox;
    private JCheckBox luggageBox;
    private JCheckBox magazineBox;
    
    
    
    
}
