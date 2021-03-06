/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package univair.Presentation;

import javax.swing.JOptionPane;
import javax.swing.JPanel;


/**
 * La classe MessageFrame crea una generica finestra contenente un
 * messaggio informativo, d'errore o d'avvertimento.
 * @author Michele
 */
public class MessageFrame {
    /**
     * Crea una Message Frame del tipo desiderato con una stringa per descrivere 
     * il tipo di messaggio
     * @param desc la descrizione del messaggio
     * @param errtype valore intero per specificare il tipo di messaggio <br>
     * 
     *  0 : messaggio di errore <br>
     *  1 : messaggio informativo <br>
     *  2 : messaggio di avvertimento 
     */
    public MessageFrame(String desc, int errtype) {
        String title = getMessageTitle(errtype);
        JPanel panel = new JPanel();
        JOptionPane.showMessageDialog(panel, desc, title, errtype);
    }
    /**
     * Il metodo getMessageTitle restituisce una stringa descrivente il tipo di 
     * messaggio nella MessageFrame in base al numero intero fornito in ingresso.
     * @param errtype Il numero intero fornito in ingresso
     * @return La stringa descrivente il tipo di messaggio
     */
    private String getMessageTitle(int errtype) {
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