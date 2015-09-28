/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package univair;

import java.util.Date;
import java.util.GregorianCalendar;
import univair.Entity.Address;
import univair.Entity.Booking;
import univair.Entity.City;
import univair.Entity.Employer;
import univair.Entity.Flight;
import univair.Entity.Persona;
import univair.Entity.Route;

/**
 *
 * @author Michele
 */
public class UnivAir {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Address a = new Address("Casacanditella","Madonna", "184", "66010", "CH");
        Persona p = new Persona("michele", "iessi", new Date(92,10,6), "m", "SSIMHL92S06E243D", "iessimichele@gmail.com", "guardiagrele", a);
        Employer e1 = new Employer("123", "Pilot");
        Employer e2 = new Employer("124", "Copilot");
        Employer e3 = new Employer("456", "Hostess");
        Employer e4 = new Employer("164", "Hostess");
        Employer e5 = new Employer("735", "Hostess");
        Employer[] e = new Employer[] {e1,e2,e3,e4,e5};
    //    e[0] = e1; 
    //    e[1] = e2;
    //    e[2] = e3;
    //    e[3] = e4;
    //    e[4] = e5;


        Route AmsMad = new Route("Amsterdam", "Roma");
        Flight f1 = new Flight(AmsMad, "00123", e, 169, new GregorianCalendar(115,10,6));
        System.out.println(City.Amsterdam.lat());

        Booking boo = new Booking(p, f1, "first", true, true, true, true, true, 1);
        
        System.out.println(boo.computePrice(f1));
    
    }
    
}
