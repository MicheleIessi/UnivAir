/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package univair.Entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import univair.Foundation.FConnect;

/**
 *
 * @author Michele
 */
public class Flight {
    /* costruttori */
    public Flight() {};
    public Flight(Route r, String id, Employer[] e, int s, GregorianCalendar d) {
        controlCrew(e);
        this.route = r;
        this.ID = id;
        this.crew = e;
        this.seats = s;
        this.date = d;
        System.out.println("Volo creato: ID: " + id + ", Tratta: " + r.getDeparture() + " - " + r.getDestination() + ", posti: " + s + ", data: " + this.getDateString());
    }
    /* getter & setter */
    public Route getRoute()     { return this.route; }
    public String getID()       { return this.ID; }
    public Employer[] getCrew() { return this.crew; }
    public int getSeats()       { return this.seats; }
    public GregorianCalendar getDate()       { return this.date; }
    public void setRoute(Route r)     { this.route = r; }
    public void setID(String id)      { this.ID = id; }
    public void setCrew(Employer[] e) { this.crew = e; }
    public void setSeats(int s)       { this.seats = s; }
    public void setDate(GregorianCalendar d)       { this.date = d; }
    /* metodi di classe */
    public String getDateString() {
        return (this.date.get(1)+1900) + "-" + (this.date.get(2)+1) + "-" + this.date.get(5);    }
    public void setPilot(Employer e)   { this.crew[0] = e; }
    public void setCopilot(Employer e) { this.crew[1] = e; }
    public void setHost1(Employer e)   { this.crew[2] = e; }
    public void setHost2(Employer e)   { this.crew[3] = e; }
    public void setHost3(Employer e)   { this.crew[4] = e; }
    /* metodi di controllo */
    public static void controlCrew(Employer[] e) {
        if(!e[0].type.equals("Pilot")) throw new IllegalArgumentException("Errore, pilota mancante");
        if(!e[1].type.equals("Copilot")) throw new IllegalArgumentException("Errore, copilota mancante");
        if(!e[2].type.equals("Hostess")) throw new IllegalArgumentException("Errore, hostess 1 mancante");
        if(!e[3].type.equals("Hostess")) throw new IllegalArgumentException("Errore, hostess 2 mancante");
        if(!e[4].type.equals("Hostess")) throw new IllegalArgumentException("Errore, hostess 3 mancante");
    }
    /* metodi per il DB */
    private GregorianCalendar getDateFromString(String date) {
        int year = Integer.parseInt(date.substring(0, 4))-1900;
        int month = Integer.parseInt(date.substring(5, 7))-1;
        int day = Integer.parseInt(date.substring(8, 10));
        return new GregorianCalendar(year, month, day);
    }
    private String getStringFromDate(GregorianCalendar gc) {
        String dat = (gc.get(1)+1900) + "-" + (gc.get(2)+1) + "-" + gc.get(5);
        return dat;
    }

    public void createFromDB(int id, GregorianCalendar gc) {
        
    }
    public Employer[] getEmployersFromDB(int id, GregorianCalendar gc) throws SQLException {
        FConnect con = new FConnect();
        String[] keys = {"idvolo","decollovolo"};
        String dat = getStringFromDate(gc);
        String[] conds = {Integer.toString(id),dat};
        ResultSet rs = con.search("lavoripassati", keys, conds);
        String e1 = rs.getString(3);
        String e2 = rs.getString(4);
        String e3 = rs.getString(5);
        String e4 = rs.getString(6);
        String e5 = rs.getString(7);
        Employer pilot = new Employer();
        pilot.createFromDB(Integer.parseInt(e1));
    }
    public Map retrieve(int id, GregorianCalendar gc) throws SQLException {
        FConnect con = new FConnect();
        ResultSet rs = con.search(table, key, cond);
        Map<String,Object> map = new HashMap<>();
        while(rs.next()) {
            map = new HashMap<>();
            Route r = new Route();
            r.createFromDB(id);
            map.put("route", r);
            map.put("ID", rs.getInt(1));
            map.put("date", getDateFromString(rs.getString(2)));
            map.put("crew","crew da inserire");
        }
    }
    /* attributi */
    private Route route;
    private String ID;
    private Employer[] crew;
    private int seats;
    private GregorianCalendar date;
    /* attributi per il db */
    private static final String table = "volo";
    private static final String[] key = {"id","decollo"};
    private static final String[] cond = {"id = ","decollo = "};
}