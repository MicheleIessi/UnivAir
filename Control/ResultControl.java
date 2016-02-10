/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package univair.Control;

import java.util.HashMap;
import univair.Presentation.MessageFrame;
import univair.Presentation.BookPanel;

/**
 *
 * @author Michele
 */
public class ResultControl {
    
    public ResultControl(HashMap map) {
        try {
            if("0".equals(map.get("seats").toString()))
                throw new IllegalArgumentException("Il volo selezionato non ha posti disponibili");
            new BookPanel(map);
        } catch (IllegalArgumentException e) {
            new MessageFrame(e.getMessage(),0);
        }
    }
}
