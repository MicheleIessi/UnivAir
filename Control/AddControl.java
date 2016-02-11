/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package univair.Control;

import java.sql.SQLException;
import java.util.GregorianCalendar;
import univair.Entity.Flight;
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
                throw new IllegalArgumentException("La tratta è già presente nel DB con ID " + i);
        } catch (SQLException | IllegalArgumentException e) {
            new MessageFrame(e.getMessage(),0);
        }
    }
    
    public AddControl(String dep, String des, String dat) {
        try {
            Route r = new Route(dep,des);
            Flight f = new Flight();
            GregorianCalendar d = getDateFromString(dat);
            f.setRoute(r);
            int i = r.getIdFromDB();
            if(i==0)
                throw new IllegalArgumentException("Errore, la tratta "+dep+"-"+des+" non è presente nel DB. È necessario aggiungerla.");
            f.setSeats(169);
            f.setDate(d);
            f.store();
            new MessageFrame("Volo " + dep + "-" + des + " aggiunto per il giorno " + dat + ".",1);
        } catch (SQLException | IllegalArgumentException e) {
            new MessageFrame(e.getMessage(),0);
        }
    }
    private GregorianCalendar getDateFromString(String date) {
        GregorianCalendar gc = new GregorianCalendar();
        int year = Integer.parseInt(date.substring(0, 4));
        int month = Integer.parseInt(date.substring(5, 6))-1;
        int day = Integer.parseInt(date.substring(7, date.length()));
        gc.set(year, month, day);
        return gc;
    }   

}
