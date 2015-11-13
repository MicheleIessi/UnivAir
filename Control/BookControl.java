/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package univair.Control;

import java.util.HashMap;
import univair.Presentation.ErrorFrame;
import univair.Presentation.BookPanel;

/**
 *
 * @author Michele
 */
public class BookControl {
    
    public BookControl(HashMap map) {
        try {
            if("0".equals(map.get("seats").toString()))
                throw new IllegalArgumentException("Il volo selezionato non ha posti disponibili");
        } catch (IllegalArgumentException e) {
            new ErrorFrame(e.getMessage(),0);
        }
        new BookPanel(map);
    }
}
