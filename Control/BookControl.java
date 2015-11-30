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
import univair.Entity.Booking;
import univair.Entity.Flight;
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
            int IDPersona = Persona.getIDIfExists(cf);
            if(cf.length()!=16) {
                throw new IllegalArgumentException("Il codice fiscale deve essere di 16 caratteri");
            }
            if(IDPersona == 0) { //creo la persona se non è presente nel db
                String nome = (String)persona.get("nome");
                if("Nome".equals(nome)) { throw new IllegalArgumentException("Inserire il nome"); }
                String cognome = (String)persona.get("cognome");
                if("Cognome".equals(cognome)) { throw new IllegalArgumentException("Inserire il cognome"); }                
                Date d = ((Date)persona.get("datanascita"));
                GregorianCalendar gc = getGregorianCalendarFromDate(d);
                String sex = (String)persona.get("sesso");
                String email = (String)persona.get("email");
                if("E-Mail".equals(email)) { throw new IllegalArgumentException("Inserire l'e-mail"); }
                String ldn = (String)persona.get("luogo di nascita");
                if("Città di nascita".equals(ldn)) { throw new IllegalArgumentException("Inserire il luogo di nascita"); }
                String cit = (String)persona.get("città di residenza");
                if("Città di residenza".equals(cit)) { throw new IllegalArgumentException("Inserire la città di residenza"); }
                String via = (String)persona.get("via");
                if("Via".equals(via)) { throw new IllegalArgumentException("Inserire la via"); }
                String num = (String)persona.get("numero civico");
                if("Numero civico".equals(num) || !num.matches("^[0-9]{1,4}[a-zA-Z]?$")) { throw new IllegalArgumentException("Inserire un numero civico valido"); }
                String cap = (String)persona.get("cap");
                if("CAP".equals(cap) || !cap.matches("^[0-9]{5}$")) throw new IllegalArgumentException("Inserire un CAP valido");
                String pro = (String)persona.get("provincia");
                if("Provincia".equals(pro) || !pro.matches("^[a-zA-Z]{2}$")) throw new IllegalArgumentException("Inserire una provincia valida (2 caratteri al massimo)");
                Address a = new Address(cit, via, num, cap, pro);
                p = new Persona(nome, cognome, gc, sex, cf, email, ldn, a);
                p.store();
                IDPersona = p.getIDFromDB();
            }
            else {
                p.createFromDB(IDPersona);
            }
            String IDVolo = (String) prenotazione.get("idvolo");
            int id = Integer.parseInt(IDVolo);
            String dataVolo = (String) prenotazione.get("datavolo");
            GregorianCalendar data = getDateFromString(dataVolo);
            Flight f = new Flight();
            f.createFromDB(id, data);
            String classe = (String) prenotazione.get("classe");
            if("Prima classe".equals(classe)) 
                classe = "first";
            else 
                classe = "second";
            boolean pasto = (boolean) prenotazione.get("pasto");
            boolean animale = (boolean) prenotazione.get("animale");
            boolean bagaglio = (boolean) prenotazione.get("bagaglio");
            boolean riviste = (boolean) prenotazione.get("riviste");
            boolean reduction = false;
            GregorianCalendar calendar = new GregorianCalendar();
            calendar.add(1, -1910);
            if(p.getDDN().before(calendar))
                reduction = false;
            int sconto = 0;
            String prc = (String) prenotazione.get("prezzo");
            double prezzo = Double.parseDouble(prc);
            Booking book = new Booking(p, f, classe, reduction, pasto, animale, bagaglio, riviste, sconto);
            book.setPrice(prezzo);
            book.store();
            new MessageFrame("Prenotazione effettuata",1);
            
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
    private GregorianCalendar getDateFromString(String date) {
        int year = Integer.parseInt(date.substring(0, 4))-1900;
        int month = Integer.parseInt(date.substring(5, 7))-1;
        int day = Integer.parseInt(date.substring(8, 10));
        return new GregorianCalendar(year, month, day);
    }

}
