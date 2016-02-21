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
                new MessageFrame("Il codice fiscale deve essere di 16 caratteri",0);
            }
            else if(IDPersona == 0) { //creo la persona se non è presente nel db
                boolean continua = true;
                String nome = (String)persona.get("nome");
                if("Nome".equals(nome)) { error("Nome"); continua = false; }
                String cognome = (String)persona.get("cognome");
                if("Cognome".equals(cognome)) { error("Cognome"); continua = false; }                
                Date d = ((Date)persona.get("datanascita"));
                GregorianCalendar gc = getGregorianCalendarFromDate(d);
                String sex = (String)persona.get("sesso");
                String email = (String)persona.get("email");
                if("E-Mail".equals(email)) { error("Email"); continua = false; }
                String ldn = (String)persona.get("luogo di nascita");
                if("Città di nascita".equals(ldn)) { error("Citnas"); continua = false; }
                String cit = (String)persona.get("città di residenza");
                if("Città di residenza".equals(cit)) { error("Citres"); continua = false; }
                String via = (String)persona.get("via");
                if("Via".equals(via)) { error("Via"); continua = false; }
                String num = (String)persona.get("numero civico");
                if("Numero civico".equals(num) || !num.matches("^[0-9]{1,4}[a-zA-Z]?$")) { error("Numero"); continua = false; }
                String cap = (String)persona.get("cap");
                if("CAP".equals(cap) || !cap.matches("^[0-9]{5}$")) { error("CAP"); continua = false; }
                String pro = (String)persona.get("provincia");
                if("Provincia".equals(pro) || !pro.matches("^[a-zA-Z]{2}$")) {error("Provincia"); continua = false;}
                if(continua) {
                    Address a = new Address(cit, via, num, cap, pro);
                    p = new Persona(nome, cognome, gc, sex, cf, email, ldn, a);
                    p.store();
                    IDPersona = p.getIDFromDB();
                }
            }
            else {
                p.createFromDB(IDPersona);
            
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
            boolean reduction = true;
            GregorianCalendar calendar = new GregorianCalendar();
            calendar.add(1, -1910);
            if(p.getDDN().before(calendar))
                reduction = false;
            String dis = (String) prenotazione.get("sconto");
            int sconto = Integer.parseInt(dis);
            String prc = (String) prenotazione.get("prezzo");
            double prezzo = Double.parseDouble(prc);
            Booking book = new Booking(p, f, classe, reduction, pasto, animale, bagaglio, riviste, sconto);
            book.setPrice(prezzo);
            book.store();
            new MessageFrame("Prenotazione effettuata",1);
            
            }
        } catch (IllegalArgumentException ex) {
            new MessageFrame(ex.getMessage(),0);
        }
    }
    
    private void error(String s) {
        String message = "error in BookControl";
        switch(s) {
            case "Nome": message = "Inserire il nome"; break;
            case "Cognome": message = "Inserire il cognome"; break;
            case "Email": message = "Inserire l'email"; break;
            case "Citnas": message = "Inserire la città di nascita"; break;
            case "Citres": message = "Inserire la città di residenza"; break;
            case "Via": message = "Inserire la via di residenza"; break;
            case "Numero": message = "Inserire un numero civico valido"; break;
            case "CAP": message = "Inserire un CAP valido"; break;
            case "Provincia": message = "Inserire una provincia valida"; break;
            default: message = "Errore generico, controllare tutti gli input.";
        }
        new MessageFrame(message,0);
    }
        
    private GregorianCalendar getGregorianCalendarFromDate(Date d) {
        int year = d.getYear();
        int month = d.getMonth();
        int day = d.getDate();
        return new GregorianCalendar(year,month,day);
    }
    private GregorianCalendar getDateFromString(String date) {
        int year = Integer.parseInt(date.substring(0, 4))-1900;
        int month = Integer.parseInt(date.substring(5, 7))-1;
        int day = Integer.parseInt(date.substring(8, 10));
        return new GregorianCalendar(year, month, day);
    }

}
