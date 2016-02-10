/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package univair.Control;

import java.sql.SQLException;
import univair.Entity.Route;
import univair.Presentation.MessageFrame;

/**
 *
 * @author Michele
 */
public class AddControl {
    public AddControl(String dep, String des, int id) {
        Route r = new Route(dep,des);
        try {
            int i = r.getIdFromDB();
            if(i == 0) {
                r.store(id);
                new MessageFrame("Tratta " + dep + "-" + des + " aggiunta con ID " + id, 1);
            }
            else 
                r.createFromDB(id);
                new MessageFrame("La tratta è già presente nel DB con ID " + i, 2);
        } catch (SQLException | IllegalArgumentException e) {
            new MessageFrame(e.getMessage(),0);
        }
    }
}
