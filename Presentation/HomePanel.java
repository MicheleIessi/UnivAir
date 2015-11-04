/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package univair.Presentation;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EtchedBorder;

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
        /* Crea e imposta le proprietà della JFrame */
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
        this.setBounds(283, 84, 800, 600);
        this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        BorderLayout border = new BorderLayout();
        Container content = this.getContentPane();
        content.setLayout(border);
        EtchedBorder edge = new EtchedBorder(EtchedBorder.RAISED);
        /* Crea e imposta gli elementi della JFrame */
        JPanel imagePanel = new JPanel();
        JButton button;
        try {
            BufferedImage image = ImageIO.read(new File(""));
            JLabel picLabel = new JLabel(new ImageIcon(image));
            imagePanel.add(picLabel);
        } catch (IOException ex) {
            
        }
        content.add(imagePanel,BorderLayout.NORTH);
    }
    
    
}