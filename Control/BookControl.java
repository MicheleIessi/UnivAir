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
        
        try {
            Persona p = new Persona();
            String cf = (String) persona.get("codice fiscale");
            int ID = Persona.getIDIfExists(cf);
            if(cf.length()!=16) {
                throw new IllegalArgumentException("Il codice fiscale deve essere di 16 caratteri");
            }
            if(ID == 0) {
                String nome = (String)persona.get("nome");
                String cognome = (String)persona.get("cognome");
                Date d = ((Date)persona.get("datanascita"));
                GregorianCalendar gc = getGregorianCalendarFromDate(d);
                String sex = (String)persona.get("sesso");
                String email = (String)persona.get("email");
                String ldn = (String)persona.get("luogo di nascita");
                String cit = (String)persona.get("città di residenza");
                String via = (String)persona.get("via");
                String num = (String)persona.get("numero civico");
                if("Numero civico".equals(num)) { throw new IllegalArgumentException("Inserire un numero civico"); }
                if(!num.matches("^[0-9]{1,4}[a-zA-Z]?$")) { throw new IllegalArgumentException("Il numero civico può avere al massimo 5 cifre"); }
                String cap = (String)persona.get("cap");
                if("CAP".equals(cap)) throw new IllegalArgumentException("Inserire un CAP");
                if(!cap.matches("^[0-9]{5}$")) throw new IllegalArgumentException("Il CAP deve essere di 5 cifre");
                String pro = (String)persona.get("provincia");
                if(!pro.matches("^[a-zA-Z]{2}$")) throw new IllegalArgumentException("La provincia deve essere di 2 caratteri");
                Address a = new Address(cit, via, num, cap, pro);
                p = new Persona(nome, cognome, gc, sex, cf, email, ldn, a);
                p.store();
                ID = p.getIDFromDB();
            }
            // continuare da qui, todo: aggiungere il controllo sugli altri elementi e l'aggiunta della ennupla al DB
            
            
                
                
        } catch (IllegalArgumentException ex) {
            new MessageFrame(ex.getMessage(),0);
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
