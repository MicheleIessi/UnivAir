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
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.UnsupportedLookAndFeelException;
import org.jdesktop.swingx.JXDatePicker;
import univair.Control.BookControl;
import univair.Entity.Booking;
import univair.Entity.Flight;
import univair.Entity.Route;

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
        this.setBounds(400, 0, 400, 600);
        this.setMinimumSize(new Dimension(560,730));
        this.setPreferredSize(new Dimension(560,768));
        this.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        BorderLayout border = new BorderLayout();
        Container content = this.getContentPane();
        content.setLayout(border);
        volo = new Flight();
        book = new Booking();
        String idvolo = (String) map.get("ID");
        String dat = (String) map.get("date");
        GregorianCalendar gc = getDateFromString(dat);
        try {
            volo.createFromDB(Integer.parseInt(idvolo), gc);
        } catch (SQLException ex) {throw new IllegalArgumentException("Errore imprevisto");}
        book.setClss("first");
        book.setDiscount(0);
        book.setLuggageSupp(false);
        book.setMagazinesSupp(false);
        book.setMealSupp(false);
        book.setPetSupp(false);
        book.setPriceRed(false);
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
        infoLabel = new JLabel("Dati prenotazione:");
        infoLabel.setAlignmentX(CENTER_ALIGNMENT);
        infoLabel.setFont(new Font("Times New Roman", Font.ITALIC, 14));
        infoLabel.setForeground(Color.blue);                
        /* ANAGRAFE PANEL */
        anagrafePanel = new JPanel(new WrapLayout(FlowLayout.CENTER));
        anagrafePanel.setBorder(BorderFactory.createTitledBorder("Dettagli anagrafici"));      
        personPanel1 = new JPanel(new FlowLayout(FlowLayout.LEADING));
        personPanel1.setAlignmentX(CENTER_ALIGNMENT);    
        namePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
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
        surnamePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
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
        birthCityPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
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
        textPanel1 = new JPanel();
        textPanel1.setLayout(new BoxLayout(textPanel1,BoxLayout.Y_AXIS));
        textPanel1.setAlignmentX(LEFT_ALIGNMENT);      
        personPanel2 = new JPanel(new FlowLayout(FlowLayout.LEADING));
        personPanel2.setAlignmentX(CENTER_ALIGNMENT);       
        labelPanel2 = new JPanel();
        labelPanel2.setLayout(new BoxLayout(labelPanel2,BoxLayout.Y_AXIS));
        labelPanel2.setAlignmentX(RIGHT_ALIGNMENT);      
        sexPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        sexPanel.setAlignmentX(CENTER_ALIGNMENT);       
        sexLabel = new JLabel("Sesso:");
        sexLabel.setFont(labelFont);
        sexLabel.setForeground(Color.blue);      
        birthDatePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        birthDatePanel.setAlignmentX(CENTER_ALIGNMENT);      
        birthDateLabel = new JLabel("Data di nascita:");
        birthDateLabel.setFont(labelFont);
        birthDateLabel.setForeground(Color.blue);      
        CFPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        CFPanel.setAlignmentX(CENTER_ALIGNMENT);      
        CFLabel = new JLabel("Codice Fiscale:");
        CFLabel.setFont(labelFont);
        CFLabel.setForeground(Color.blue);       
        radioDateTextPanel = new JPanel();
        radioDateTextPanel.setLayout(new BoxLayout(radioDateTextPanel,BoxLayout.Y_AXIS));
        radioDateTextPanel.setAlignmentX(CENTER_ALIGNMENT);      
        radioSexPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        radioSexPanel.setAlignmentX(CENTER_ALIGNMENT);
        maleRadio = new JRadioButton("M",true);
        femaleRadio = new JRadioButton("F",false);
        ButtonGroup mf = new ButtonGroup();
        mf.add(maleRadio);
        mf.add(femaleRadio);    
        Date d = new Date();
        d.setYear(80);
        datePicker = new JXDatePicker(d);    
        CFTextField = new JTextField("Codice Fiscale");
        CFTextField.setForeground(Color.lightGray);
        CFTextField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if("Codice Fiscale".equals(CFTextField.getText())) {
                    CFTextField.setText("");
                    CFTextField.setForeground(Color.black);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if("".equals(CFTextField.getText())) {
                    CFTextField.setText("Codice Fiscale");
                    CFTextField.setForeground(Color.lightGray);
                }
            }
        });
        CFTextField.addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {}
            @Override
            public void keyReleased(KeyEvent e) {}
            @Override
            public void keyTyped(KeyEvent e) {
                if(CFTextField.getText().length()>=16) {e.consume();}
            }
        });   
        codiceFiscaleLabel = new JLabel("Se hai già usato questo servizio in precedenza, puoi inserire solo il codice fiscale");
        codiceFiscaleLabel.setFont(labelFont);
        codiceFiscaleLabel.setForeground(Color.red);
        
        /* RESIDENZA PANEL */   
        residenzaPanel = new JPanel(new WrapLayout(FlowLayout.CENTER));
        residenzaPanel.setBorder(BorderFactory.createTitledBorder("Dettagli sulla residenza"));
        resPanel1 = new JPanel(new FlowLayout(FlowLayout.LEADING));
        resPanel1.setAlignmentX(TOP_ALIGNMENT);
        labelPanel3 = new JPanel();
        labelPanel3.setLayout(new BoxLayout(labelPanel3,BoxLayout.Y_AXIS));
        labelPanel3.setAlignmentX(RIGHT_ALIGNMENT);
        viaPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        viaPanel.setAlignmentX(CENTER_ALIGNMENT);
        viaLabel = new JLabel("Via:");
        viaLabel.setFont(labelFont);
        viaLabel.setForeground(Color.blue);
        cittàPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        cittàPanel.setAlignmentX(CENTER_ALIGNMENT);
        cittàLabel = new JLabel("Città di residenza:");
        cittàLabel.setFont(labelFont);
        cittàLabel.setForeground(Color.blue);
        mailPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        mailPanel.setAlignmentX(CENTER_ALIGNMENT);
        mailLabel = new JLabel("E-Mail:");
        mailLabel.setFont(labelFont);
        mailLabel.setForeground(Color.blue);
        textPanel2 =new JPanel();
        textPanel2.setLayout(new BoxLayout(textPanel2,BoxLayout.Y_AXIS));
        textPanel2.setAlignmentX(LEFT_ALIGNMENT);
        viaTextField = new JTextField("Via",15);
        viaTextField.setForeground(Color.lightGray);
        viaTextField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if("Via".equals(viaTextField.getText())) {
                    viaTextField.setText("");
                    viaTextField.setForeground(Color.black);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if("".equals(viaTextField.getText())) {
                    viaTextField.setText("Via");
                    viaTextField.setForeground(Color.lightGray);
                }
            }
        });
        cittàTextField = new JTextField("Città di residenza");
        cittàTextField.setForeground(Color.lightGray);
        cittàTextField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if("Città di residenza".equals(cittàTextField.getText())) {
                    cittàTextField.setText("");
                    cittàTextField.setForeground(Color.black);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if("".equals(cittàTextField.getText())) {
                    cittàTextField.setText("Città di residenza");
                    cittàTextField.setForeground(Color.lightGray);
                }
            }
        });
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
        resPanel2 = new JPanel(new FlowLayout(FlowLayout.LEADING));
        resPanel2.setAlignmentX(TOP_ALIGNMENT);
        labelPanel4 = new JPanel();
        labelPanel4.setLayout(new BoxLayout(labelPanel4,BoxLayout.Y_AXIS));
        labelPanel4.setAlignmentX(RIGHT_ALIGNMENT);
        numeroPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        numeroPanel.setAlignmentX(CENTER_ALIGNMENT);
        numeroLabel = new JLabel("Numero:");
        numeroLabel.setFont(labelFont);
        numeroLabel.setForeground(Color.blue);
        provinciaPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        provinciaPanel.setAlignmentX(CENTER_ALIGNMENT);
        provinciaLabel = new JLabel("Provincia:");
        provinciaLabel.setFont(labelFont);
        provinciaLabel.setForeground(Color.blue);
        capPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        capPanel.setAlignmentX(CENTER_ALIGNMENT);
        capLabel = new JLabel("CAP:");
        capLabel.setFont(labelFont);
        capLabel.setForeground(Color.blue);
        textPanel3 = new JPanel();
        textPanel3.setLayout(new BoxLayout(textPanel3,BoxLayout.Y_AXIS));
        textPanel3.setAlignmentX(LEFT_ALIGNMENT);
        numeroTextField = new JTextField("Numero civico");
        numeroTextField.setForeground(Color.lightGray);
        numeroTextField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if("Numero civico".equals(numeroTextField.getText())) {
                    numeroTextField.setText("");
                    numeroTextField.setForeground(Color.black);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if("".equals(numeroTextField.getText())) {
                    numeroTextField.setText("Numero civico");
                    numeroTextField.setForeground(Color.lightGray);
                }
            }
        });
        provinciaTextField = new JTextField("Provincia");
        provinciaTextField.setForeground(Color.lightGray);
        provinciaTextField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if("Provincia".equals(provinciaTextField.getText())) {
                    provinciaTextField.setText("");
                    provinciaTextField.setForeground(Color.black);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if("".equals(provinciaTextField.getText())) {
                    provinciaTextField.setText("Provincia");
                    provinciaTextField.setForeground(Color.lightGray);
                }
            }
        });
        capTextField = new JTextField("CAP");
        capTextField.setForeground(Color.lightGray);
        capTextField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if("CAP".equals(capTextField.getText())) {
                    capTextField.setText("");
                    capTextField.setForeground(Color.black);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if("".equals(capTextField.getText())) {
                    capTextField.setText("CAP");
                    capTextField.setForeground(Color.lightGray);
                }
            }
        });
        
        /* DETAIL PANEL */
        detailPanel = new JPanel(new WrapLayout(FlowLayout.CENTER));
        detailPanel.setBorder(BorderFactory.createTitledBorder("Dettagli sulla prenotazione"));        
        detPanel = new JPanel();
        detPanel.setLayout(new BoxLayout(detPanel,BoxLayout.Y_AXIS));
        detPanel.setAlignmentX(TOP_ALIGNMENT);
        radioClassPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        radioClassPanel.setAlignmentX(LEFT_ALIGNMENT);
        firstRadio = new JRadioButton("Prima classe",true);
        firstRadio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                book.setClss("first");
                aggiornaPrezzo(0);
            }
        });
        firstRadio.setFont(labelFont);
        firstRadio.setForeground(Color.blue);
        secondRadio = new JRadioButton("Seconda classe");
        secondRadio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                book.setClss("second");
                aggiornaPrezzo(0);
            }
        });
        secondRadio.setFont(labelFont);
        secondRadio.setForeground(Color.blue);
        ButtonGroup fs = new ButtonGroup();
        fs.add(firstRadio);
        fs.add(secondRadio);
        mealBox = new JCheckBox("Servizio ristorazione");
        mealBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(mealBox.isSelected())
                    book.setMealSupp(true);
                else book.setMealSupp(false);
                aggiornaPrezzo(0);
            }
        });
        mealBox.setFont(labelFont);
        mealBox.setForeground(Color.blue);
        animalBox = new JCheckBox("Animale a bordo");
        animalBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(animalBox.isSelected())
                    book.setPetSupp(true);
                else book.setPetSupp(false);
                aggiornaPrezzo(0);
            }
        });
        animalBox.setFont(labelFont);
        animalBox.setForeground(Color.blue);
        luggageBox = new JCheckBox("Bagaglio addizionale");
        luggageBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(luggageBox.isSelected())
                    book.setLuggageSupp(true);
                else book.setLuggageSupp(false);
                aggiornaPrezzo(0);
            }
        });
        luggageBox.setFont(labelFont);
        luggageBox.setForeground(Color.blue);
        magazineBox = new JCheckBox("Riviste a scelta");
        magazineBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(magazineBox.isSelected())
                    book.setMagazinesSupp(true);
                else book.setMagazinesSupp(false);
                aggiornaPrezzo(0);
            }
        });
        magazineBox.setFont(labelFont);
        magazineBox.setForeground(Color.blue);
        discountPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        discountPanel.setAlignmentX(TOP_ALIGNMENT);
        discountLabel = new JLabel("Sconto:");
        discountLabel.setFont(labelFont);
        discountLabel.setForeground(Color.blue);
        discountTextField = new JTextField("Sconto %",20);
        discountTextField.setForeground(Color.lightGray);
        discountTextField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if("Sconto %".equals(discountTextField.getText())) {
                    discountTextField.setText("");
                    discountTextField.setForeground(Color.black);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if("".equals(discountTextField.getText())) {
                    discountTextField.setText("Sconto %");
                    discountTextField.setForeground(Color.lightGray);
                }
            }
        });
        discountTextField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                if(!Character.isDigit(e.getKeyChar()))
                    e.consume();
            }
            @Override
            public void keyPressed(KeyEvent e) { }
            @Override
            public void keyReleased(KeyEvent e) {
                try {
                    if(Double.parseDouble(discountTextField.getText())>100) {
                    discountTextField.setText("100");
                    }
                } catch (NumberFormatException ex) {
                    discountTextField.setText("");
                }
                double disc = 0;
                try {
                    disc = Double.parseDouble(discountTextField.getText());
                } catch(NumberFormatException ex) {}
                book.setDiscount(disc);
                aggiornaPrezzo(disc);
                book.setPrice(Double.parseDouble(priceLabel.getText()));
                //System.out.println(disc);
                //System.out.println(book.getPrice());
            }
        });
        
        /* PRICE PANEL */
        pricePanel = new JPanel(new WrapLayout(FlowLayout.CENTER));
        pricePanel.setAlignmentX(CENTER_ALIGNMENT);
        priceInfoLabel = new JLabel("Prezzo: € ");
        priceInfoLabel.setFont(new Font("Times New Roman",Font.ITALIC,14));
        priceInfoLabel.setForeground(Color.blue);
        priceLabel = new JLabel("00.00");
        priceLabel.setText(Double.toString(book.computePrice(volo,0)));
        priceLabel.setFont(new Font("Times New Roman",Font.BOLD,14));
        
        /* PRENOTAZIONE */
        buttonPanel = new JPanel(new WrapLayout(FlowLayout.CENTER));
        buttonPanel.setAlignmentX(CENTER_ALIGNMENT);
        bookButton = new JButton("Prenota");
        bookButton.setSize(50, 20);
        bookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HashMap<String,Object> persona = new HashMap();
                persona.put("nome", nameTextField.getText());
                persona.put("cognome", surnameTextField.getText());
                persona.put("datanascita", datePicker.getDate()); //è una data da formattare nel formato yyyy-mm-dd
                String sex = "";
                for(Enumeration<AbstractButton> buttons = mf.getElements(); buttons.hasMoreElements();) {
                    AbstractButton button = buttons.nextElement();
                    if(button.isSelected()) 
                        sex = button.getText();
                }
                persona.put("sesso", sex);
                persona.put("codice fiscale", CFTextField.getText());
                persona.put("email",mailTextField.getText());
                persona.put("luogo di nascita", birthCityTextField.getText());
                persona.put("città di residenza", cittàTextField.getText());
                persona.put("via", viaTextField.getText());
                persona.put("numero civico", numeroTextField.getText());
                persona.put("cap", capTextField.getText());
                persona.put("provincia", provinciaTextField.getText()); //persona contiene 12 objects

                HashMap<String,Object> prenotazione = new HashMap();
                prenotazione.put("idvolo", map.get("ID"));
                prenotazione.put("datavolo", map.get("date")); //date è una stringa già formattata yyyy-mm-dd
                String classe = "";
                for(Enumeration<AbstractButton> buttons = fs.getElements(); buttons.hasMoreElements();) {
                    AbstractButton button = buttons.nextElement();
                    if(button.isSelected()) 
                        classe = button.getText();
                }
                prenotazione.put("classe",classe);
                prenotazione.put("pasto",mealBox.isSelected());
                prenotazione.put("animale",animalBox.isSelected());
                prenotazione.put("bagaglio",luggageBox.isSelected());
                prenotazione.put("riviste",magazineBox.isSelected()); 
                prenotazione.put("prezzo",priceLabel.getText());        
                prenotazione.put("sconto",discountTextField.getText());         //prenotazione contiene 9 objects
                bookButtonAction(persona,prenotazione);
            }
        });
        
        //</editor-fold>
        
        //<<editor-fold desc="Costruisco la JFrame inserendo gli elementi tra loro">
       
        /* ANAGRAFE */
        namePanel.add(nameLabel);
        surnamePanel.add(surnameLabel);
        birthCityPanel.add(birthCityLabel);
        labelPanel1.add(namePanel);
        labelPanel1.add(surnamePanel);
        labelPanel1.add(birthCityPanel);
        textPanel1.add(nameTextField);
        textPanel1.add(surnameTextField);
        textPanel1.add(birthCityTextField);        
        sexPanel.add(sexLabel);
        birthDatePanel.add(birthDateLabel);
        CFPanel.add(CFLabel);
        labelPanel2.add(sexPanel);
        labelPanel2.add(birthDatePanel);
        labelPanel2.add(CFPanel);
        radioSexPanel.add(maleRadio);
        radioSexPanel.add(femaleRadio);
        radioDateTextPanel.add(radioSexPanel);
        radioDateTextPanel.add(datePicker);
        radioDateTextPanel.add(CFTextField);        
        personPanel1.add(labelPanel1);
        personPanel1.add(textPanel1);
        personPanel2.add(labelPanel2);
        personPanel2.add(radioDateTextPanel);        
        anagrafePanel.add(personPanel1);
        anagrafePanel.add(personPanel2);
        anagrafePanel.add(codiceFiscaleLabel);
        /* RESIDENZA */
        viaPanel.add(viaLabel);
        cittàPanel.add(cittàLabel);
        mailPanel.add(mailLabel);
        labelPanel3.add(viaPanel);        
        labelPanel3.add(cittàPanel);        
        labelPanel3.add(mailPanel);        
        textPanel2.add(viaTextField);
        textPanel2.add(cittàTextField);
        textPanel2.add(mailTextField);        
        resPanel1.add(labelPanel3);
        resPanel1.add(textPanel2);        
        numeroPanel.add(numeroLabel);
        provinciaPanel.add(provinciaLabel);
        capPanel.add(capLabel);
        labelPanel4.add(numeroPanel);
        labelPanel4.add(provinciaPanel);
        labelPanel4.add(capPanel);        
        textPanel3.add(numeroTextField);
        textPanel3.add(provinciaTextField);
        textPanel3.add(capTextField);
        resPanel2.add(labelPanel4);
        resPanel2.add(textPanel3);
        residenzaPanel.add(resPanel1);
        residenzaPanel.add(resPanel2);
        /* DETTAGLI DI PRENOTAZIONE */
        radioClassPanel.add(firstRadio);
        radioClassPanel.add(secondRadio);
        detPanel.add(radioClassPanel);
        detPanel.add(mealBox);
        detPanel.add(animalBox);
        detPanel.add(luggageBox);
        detPanel.add(magazineBox);
        discountPanel.add(discountLabel);
        discountPanel.add(discountTextField);
        detPanel.add(discountPanel);
        detailPanel.add(detPanel);
        /* DETTAGLI SUL PREZZO */
        pricePanel.add(priceInfoLabel);
        pricePanel.add(priceLabel);
        /* BUTTON DI CONFERMA */
        buttonPanel.add(bookButton);
        /* ASSEMBLAGGIO FINALE */
        centralPanel.add(infoLabel);
        centralPanel.add(anagrafePanel);
        centralPanel.add(residenzaPanel);
        centralPanel.add(detailPanel);
        centralPanel.add(pricePanel);
        centralPanel.add(buttonPanel);

        content.add(imagePanel,BorderLayout.NORTH);        
        content.add(centralPanel,BorderLayout.CENTER);
    //</editor-fold>
        
        
    }
    private void aggiornaPrezzo(double dis) {
        priceLabel.setText(Double.toString(book.computePrice(volo,dis)));
    }
    private void bookButtonAction(HashMap persona, HashMap prenotazione) {
        try {
            new BookControl(persona,prenotazione);
            this.dispose();
        } catch (SQLException e) {
            new MessageFrame(e.getMessage(), 0);
        }
    }
    private GregorianCalendar getDateFromString(String date) {
        int year = Integer.parseInt(date.substring(0, 4))-1900;
        int month = Integer.parseInt(date.substring(5, 7))-1;
        int day = Integer.parseInt(date.substring(8, 10));
        return new GregorianCalendar(year, month, day);
    }
    
    private JPanel imagePanel;
    private JPanel centralPanel;
        private JLabel infoLabel;
        private JPanel anagrafePanel;               //wraplayout
            private JPanel personPanel1;            //flowlayout
                private JPanel labelPanel1;         //boxlayout
                    private JPanel namePanel;       //flowlayout
                        private JLabel nameLabel;
                    private JPanel surnamePanel;    //flowlayout
                        private JLabel surnameLabel;  
                    private JPanel birthCityPanel;  //flowlayout
                        private JLabel birthCityLabel;
                private JPanel textPanel1;          //boxlayout
                    private JTextField nameTextField;
                    private JTextField surnameTextField;
                    private JTextField birthCityTextField;
            private JPanel personPanel2;            //flowlayout
                private JPanel labelPanel2;         //boxlayout
                    private JPanel sexPanel;        //flowlayout
                        private JLabel sexLabel;
                    private JPanel birthDatePanel;  //flowlayout
                        private JLabel birthDateLabel;
                    private JPanel CFPanel;
                        private JLabel CFLabel;     //flowlayout
                private JPanel radioDateTextPanel;  //boxlayout
                    private JPanel radioSexPanel;   //flowlayout
                        private JRadioButton maleRadio;
                        private JRadioButton femaleRadio;
                    private JXDatePicker datePicker;
                    private JTextField CFTextField;
            private JLabel codiceFiscaleLabel;
        private JPanel residenzaPanel;              //wrapLayout
            private JPanel resPanel1;               //flowlayout
                private JPanel labelPanel3;         //boxlayout
                    private JPanel viaPanel;        //flowlayout
                        private JLabel viaLabel;
                    private JPanel cittàPanel;      //flowlayout
                        private JLabel cittàLabel;
                    private JPanel mailPanel;       //flowlayout
                        private JLabel mailLabel;
                private JPanel textPanel2;          //boxlayout
                    private JTextField viaTextField;
                    private JTextField cittàTextField;
                    private JTextField mailTextField;
            private JPanel resPanel2;               //flowlayout
                private JPanel labelPanel4;         //boxlayout
                    private JPanel numeroPanel;     //flowlayout
                        private JLabel numeroLabel;
                    private JPanel provinciaPanel;  //flowlayout
                        private JLabel provinciaLabel;
                    private JPanel capPanel;        //flowlayout
                        private JLabel capLabel;
                private JPanel textPanel3;          //boxlayout
                    private JTextField numeroTextField;
                    private JTextField provinciaTextField;
                    private JTextField capTextField;
        private JPanel detailPanel;                 //wraplayout
            private JPanel detPanel;                //boxlayout
                private JPanel radioClassPanel;     //flowlayout
                    private JRadioButton firstRadio;
                    private JRadioButton secondRadio;
                private JCheckBox mealBox;
                private JCheckBox animalBox;
                private JCheckBox luggageBox;
                private JCheckBox magazineBox;
                private JPanel discountPanel;       //flowlayout
                    private JLabel discountLabel;
                    private JTextField discountTextField;
        private JPanel pricePanel;                  //wraplayout
            private JLabel priceInfoLabel;
            private JLabel priceLabel;
        private JPanel buttonPanel;                 //wraplayout
            private JButton bookButton;
     
    private Booking book;
    private Flight volo;
    
    //font per le label
    private Font labelFont = new Font("Times New Roman",Font.ITALIC,12);   
}