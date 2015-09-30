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
        
        Address add = new Address("Casacanditella", "Madonna", "19", "66010", "CH");
        GregorianCalendar date = new GregorianCalendar(115, 8, 30);
        System.out.println(date.get(2));
        
        String dat = "2015-10-01";
        System.out.println(dat.substring(5, 7));
        
        Persona dummy = new Persona();
        List<Map<String,Object>> l = dummy.retrieve(10003);
        GregorianCalendar id = (GregorianCalendar) l.get(0).get("ddn");

        FConnect con = new FConnect();
        String[] keys = new String[2]; 
        keys[0] = "idtratta";
        keys[1] = "posti";
        String[] cond = new String[2];
        cond[0] = "14";
        cond[1] = "169";
        ResultSet rs = con.search("volo", keys, cond);
        System.out.println();
        while(rs.next()) {
            for(int i=0; i<rs.getMetaData().getColumnCount();i++) {
                System.out.println(rs.getString(i+1));
            }
        }
        
    }
    
}
