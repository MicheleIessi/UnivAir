/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package univair.Control;

import java.sql.SQLException;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import univair.Entity.Address;
import univair.Entity.Persona;
import univair.Presentation.MessageFrame;

/**
 *
 * @author Michele
 */
public class BookControl {
    
    public BookControl(HashMap persona, HashMap prenotazione) throws SQLException {
        
        String cf = (String) persona.get("codice fiscale");
        
        if(Persona.getIDIfExists(cf) == 0) {
            Persona p = new Persona();
            p.setNome((String)persona.get("nome"));
            p.setCognome((String)persona.get("cognome"));
            Date d = ((Date)persona.get("datanascita"));
            GregorianCalendar gc = getGregorianCalendarFromDate(d);
            p.setDDN(gc);
            p.setSec((String)persona.get("sesso"));
            p.setCF(cf);
            p.SetEmail((String)persona.get("email"));
            p.setLDN((String)persona.get("luogo di nascita"));
            String cit = (String)persona.get("città di residenza");
            String via = (String)persona.get("via");
            String num = (String)persona.get("numero civico");
            String cap = (String)persona.get("cap");
            String pro = (String)persona.get("provincia");
            Address a = new Address(cit, via, num, cap, pro);
            p.setLDR(a);








        }
        
        
        
        
        
    }
    
    private GregorianCalendar getGregorianCalendarFromDate(Date d) {
        int year = d.getYear();
        int month = d.getMonth();
        int day = d.getDate();
        return new GregorianCalendar(year,month,day);
    }
    private String getStringFromDate(GregorianCalendar gc) {
        String dat = (gc.get(1)+1900) + "-" + (gc.get(2)+1) + "-" + gc.get(5);
        return dat;
    }
}
