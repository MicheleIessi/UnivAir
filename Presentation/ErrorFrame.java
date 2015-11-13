/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package univair.Presentation;

import javax.swing.JOptionPane;
import javax.swing.JPanel;


/**
 *
 * @author Michele
 */
public class ErrorFrame {
    
    public ErrorFrame(String desc, int errtype) {
        String title = getErrorTitle(errtype);
        JPanel panel = new JPanel();
        JOptionPane.showMessageDialog(panel, desc, title, errtype);
    }
    private String getErrorTitle(int errtype) {
        String res;
        switch(errtype) {
            case 0: res = "Messaggio di errore"; break;
            case 1: res = "Messaggio informativo"; break;
            case 2: res = "Messaggio di avvertimento"; break;
            default: res = "";
        }
        return res;
    }
}
