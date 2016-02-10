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
import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UnsupportedLookAndFeelException;
import univair.Control.LoginControl;

/**
 *
 * @author Michele
 */
public class LoginPanel extends JFrame {
    
    public LoginPanel() {
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
        this.setTitle("Univair: accesso richiesto");
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
        centralPanel.setLayout(new BoxLayout(centralPanel, BoxLayout.Y_AXIS));
        centralPanel.setAlignmentX(CENTER_ALIGNMENT);
        usernamePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        usernamePanel.setAlignmentX(CENTER_ALIGNMENT);
        usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(labelFont);
        usernameLabel.setForeground(Color.blue);
        usernameTF = new JTextField("Username",20);
        usernameTF.setForeground(Color.lightGray);
        usernameTF.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if("Username".equals(usernameTF.getText())) {
                    usernameTF.setForeground(Color.black);
                    usernameTF.setText("");
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if("".equals(usernameTF.getText())) {
                    usernameTF.setForeground(Color.lightGray);
                    usernameTF.setText("Username");
                }
            }
        });
        passwordPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        passwordPanel.setAlignmentX(CENTER_ALIGNMENT);
        passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(labelFont);
        passwordLabel.setForeground(Color.blue);
        passwordTF = new JPasswordField("Password",20);
        passwordTF.setForeground(Color.lightGray);
        passwordTF.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if("Password".equals(passwordTF.getText())) {
                    passwordTF.setForeground(Color.black);
                    passwordTF.setText("");
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if("".equals(passwordTF.getText())) {
                    passwordTF.setForeground(Color.lightGray);
                    passwordTF.setText("Password");
                }
            }
        });
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setAlignmentX(CENTER_ALIGNMENT);
        loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginButtonAction(e);
            }
        });
        cancelButton = new JButton("Annulla");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cancelButtonAction(e);
            }
        });
        //</editor-fold>
        
        //<editor-fold desc="Inserisco gli elementi appena creati nella JFrame">
        usernamePanel.add(usernameLabel);
        usernamePanel.add(usernameTF);
        passwordPanel.add(passwordLabel);
        passwordPanel.add(passwordTF);
        buttonPanel.add(loginButton);
        buttonPanel.add(cancelButton);
        centralPanel.add(usernamePanel);
        centralPanel.add(passwordPanel);
        centralPanel.add(buttonPanel);
        
        content.add(imagePanel,BorderLayout.NORTH);
        content.add(centralPanel,BorderLayout.CENTER);
        content.add(centralPanel,BorderLayout.CENTER);
        //</editor-fold>
        pack();
    }
    
    /* ACTION LISTENERS */
        private void loginButtonAction(java.awt.event.ActionEvent evt) {
            String username = usernameTF.getText();
            String password = passwordTF.getText();
            try {
                new LoginControl(username, password);
                this.dispose();
            } catch(Exception e) {
                new MessageFrame(e.getMessage(),0);
            }
        }
        private void cancelButtonAction(java.awt.event.ActionEvent evt) {
            super.dispose();
        }

    private JPanel imagePanel;
    private JPanel centralPanel;
        private JPanel usernamePanel;
            private JLabel usernameLabel;
            private JTextField usernameTF;
        private JPanel passwordPanel;
            private JLabel passwordLabel;
            private JPasswordField passwordTF;
    private JPanel buttonPanel;
        private JButton loginButton;
        private JButton cancelButton;
        
    private Font labelFont = new Font("Times New Roman", Font.ITALIC, 12);
}