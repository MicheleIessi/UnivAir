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
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.Border;
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
        this.setMinimumSize(new Dimension(400,600));
        this.setPreferredSize(new Dimension(512,768));
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
        /* primo JPanel */
        centralPanel = new JPanel();
        centralPanel.setLayout(new BoxLayout(centralPanel, BoxLayout.Y_AXIS));
        centralPanel.setAlignmentX(CENTER_ALIGNMENT);        
        /* secondo JPanel */
        detailPanel = new JPanel();
        detailPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        detailPanel.setAlignmentX(CENTER_ALIGNMENT);
        detailPanel.setBorder(BorderFactory.createTitledBorder("Dettagli di prenotazione"));
        detailPanel.setPreferredSize(new Dimension(380, 120));
        detailPanel.setMaximumSize(new Dimension(380, 120));
        /* terzo JPanel */
        mealPanel = new JPanel();
        mealPanel.setAlignmentX(CENTER_ALIGNMENT);
        mealPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
        /* prima JLabel */
        mealLabel = new JLabel("Pasto a bordo: ");
        mealLabel.setAlignmentX(CENTER_ALIGNMENT);
        /* prima CheckBox */
        mealBox = new JCheckBox();
        mealBox.setSelected(false);
        /* quarto JPanel */
        animalPanel = new JPanel();
        animalPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
        animalPanel.setAlignmentX(CENTER_ALIGNMENT);
        /* seconda JLabel */
        animalLabel = new JLabel("Servizio animali: ");
        animalLabel.setAlignmentX(CENTER_ALIGNMENT);
        /* seconda CheckBox */
        animalBox = new JCheckBox();
        animalBox.setSelected(false);
        /* quinto JPanel */
        luggagePanel = new JPanel();
        luggagePanel.setLayout(new FlowLayout(FlowLayout.LEADING));
        luggagePanel.setAlignmentX(CENTER_ALIGNMENT);
        /* terza JLabel */
        luggageLabel = new JLabel("Bagaglio addizionale: ");
        luggageLabel.setAlignmentX(CENTER_ALIGNMENT);
        /* terza CheckBox */
        luggageBox = new JCheckBox();
        luggageBox.setSelected(false);
        /* sesto JPanel */
        magazinePanel = new JPanel();
        magazinePanel.setLayout(new FlowLayout(FlowLayout.LEADING));
        magazinePanel.setAlignmentX(CENTER_ALIGNMENT);
        /* quarta JLabel */
        magazineLabel = new JLabel("Servizio animali: ");
        magazineLabel.setAlignmentX(CENTER_ALIGNMENT);
        /* quarto CheckBox */
        magazineBox = new JCheckBox();
        magazineBox.setSelected(false);
        /* quinta JLabel */
        infoLabel = new JLabel("Dati prenotazione:");
        infoLabel.setAlignmentX(CENTER_ALIGNMENT);
        infoLabel.setFont(new Font("Times New Roman", Font.ITALIC, 14));
        infoLabel.setForeground(Color.blue);
                
        
        anagrafePanel = new JPanel();
        anagrafePanel.setLayout(new FlowLayout(FlowLayout.LEADING));
        anagrafePanel.setAlignmentX(TOP_ALIGNMENT);
        
        personPanel = new JPanel();
        personPanel.setLayout(new BoxLayout(personPanel, BoxLayout.Y_AXIS));
        personPanel.setAlignmentX(TOP_ALIGNMENT);
        
        namePanel = new JPanel();
        namePanel.setLayout(new FlowLayout(FlowLayout.LEADING));
        namePanel.setAlignmentX(TOP_ALIGNMENT);
        
        nameLabel = new JLabel("Nome: ");
        nameLabel.setFont(new Font("Times New Roman",Font.ITALIC,16));
        nameLabel.setForeground(Color.blue);
        
        nameTextField = new JTextField("",12);
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
        



        //</editor-fold>
        
        
        namePanel.add(nameLabel);
        namePanel.add(nameTextField);
        personPanel.add(namePanel);
        anagrafePanel.add(personPanel);
        
        
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
        private JPanel personPanel;     //boxlayout
            private JPanel namePanel;       //flowlayout
                private JLabel nameLabel;
                private JTextField nameTextField;
            private JPanel surnamePanel;    //flowlayout
                private JLabel surnameLabel;  
                private JTextField surnameTextField;
            private JPanel birthCityPanel;  //flowlayout
                private JLabel birthCityLabel;
                private JTextField birthCityTextField;
    
    private JXDatePicker datePicker; 
    private JRadioButton maleRadio;
    private JRadioButton femaleRadio;
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
