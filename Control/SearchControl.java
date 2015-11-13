/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package univair.Control;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import univair.Entity.Flight;
import univair.Entity.Route;
import univair.Presentation.ErrorFrame;
import univair.Presentation.ResultPanel;

/**
 *
 * @author Michele
 */
public class SearchControl {
    
    public SearchControl(String dep, String des, String dat) {
        try {
            Route r = new Route(dep, des);
            GregorianCalendar gc = getDateFromString(dat);
            GregorianCalendar now = new GregorianCalendar();
            if(gc.before(now)) {
                throw new IllegalArgumentException("Selezionare una data adatta");
            }
            int id = r.getIdFromDB();
            ArrayList list = Flight.getFlightsFromRouteID(id);
            ResultPanel rp = new ResultPanel(list,r,dat);
        } catch (IllegalArgumentException | SQLException e) {
            new ErrorFrame(e.getMessage(), 0);
        }
    }
    
    private GregorianCalendar getDateFromString(String date) {
        GregorianCalendar gc = new GregorianCalendar();
        int year = Integer.parseInt(date.substring(0, 4));
        int month = Integer.parseInt(date.substring(5, 7))-1;
        int day = Integer.parseInt(date.substring(8, date.length()));
        gc.set(year, month, day);
        return gc;
    }



    
}
