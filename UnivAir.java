/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package univair;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import univair.Entity.Address;
import univair.Entity.Booking;
import univair.Entity.City;
import univair.Entity.Employer;
import univair.Entity.Flight;
import univair.Entity.Persona;
import univair.Entity.Route;
import univair.Foundation.FConnect;

/**
 *
 * @author Michele
 */
public class UnivAir {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException {
        
        Route r = new Route();
        r.createFromDB(14);
        System.out.println(r.toString());
        
        
    }    
}
