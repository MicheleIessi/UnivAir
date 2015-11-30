/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package univair.Entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
    /**
     * Il costruttore Flight() costruisce un nuovo oggetto Flight prendendo come
     * input una Route(tratta), un ID, un array di Employer, un numero di posti
     * e una data di partenza
     * @param r La tratta alla quale appartiene il volo
     * @param id L'ID del volo appena creato
     * @param e Array di Employer che specifica l'equipaggio
     * @param s Il numero di posti disponibili
     * @param d La data di partenza.
     */
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
    /**
     * Il metodo getDateString() converte la data di partenza del volo in una 
     * stringa formattata secondo il pattern yyyy-mm-dd.
     * @return una stringa "traduzione" della data di partenza del volo.
     */
    public String getDateString() {
        return (this.date.get(1)+1900) + "-" + (this.date.get(2)+1) + "-" + this.date.get(5);    }
    public void setPilot(Employer e)   { this.crew[0] = e; }
    public void setCopilot(Employer e) { this.crew[1] = e; }
    public void setHost1(Employer e)   { this.crew[2] = e; }
    public void setHost2(Employer e)   { this.crew[3] = e; }
    public void setHost3(Employer e)   { this.crew[4] = e; }
    /* metodi di classe */
    /**
     * Il metodo controlCrew() effettua un controllo su un array di Employer per
     * verificare che sia presente un Employer di tipo 'Pilot', uno di tipo 
     * 'Copilot' e 3 di tipo 'Hostess'.
     * @param e L'array di Employer su cui fare il controllo.
     */
    public static void controlCrew(Employer[] e) {
        if(!e[0].type.equals("Pilot")) throw new IllegalArgumentException("Errore, pilota mancante");
        if(!e[1].type.equals("Copilot")) throw new IllegalArgumentException("Errore, copilota mancante");
        if(!e[2].type.equals("Hostess")) throw new IllegalArgumentException("Errore, hostess 1 mancante");
        if(!e[3].type.equals("Hostess")) throw new IllegalArgumentException("Errore, hostess 2 mancante");
        if(!e[4].type.equals("Hostess")) throw new IllegalArgumentException("Errore, hostess 3 mancante");
    }
    /* metodi per il DB */
    /**
     * Il metodo getDateFromString crea un oggetto GregorianCalendar a partire
     * da una stringa formattata secondo il pattern yyyy-mm-dd.
     * @param date La stringa che si vuole convertire
     * @return Un GregorianCalendar equivalente alla stringa convertita
     */
    private GregorianCalendar getDateFromString(String date) {
        int year = Integer.parseInt(date.substring(0, 4))-1900;
        int month = Integer.parseInt(date.substring(5, 7))-1;
        int day = Integer.parseInt(date.substring(8, date.length()));
        return new GregorianCalendar(year, month, day);
    }
    /**
     * Il metodo getStringFromDate è il duale di getDateFromString e converte un
     * GregorianCalendar dato come input in una stringa formattata secondo il 
     * pattern yyyy-mm-dd.
     * @param gc Il GregorianCalendar che si vuole convertire
     * @return Una stringa formattata secondo il pattern yyyy-mm-dd equivalente 
     * al GregorianCalendar in inputs
     */
    private String getStringFromDate(GregorianCalendar gc) {
        String dat = (gc.get(1)+1900) + "-" + (gc.get(2)+1) + "-" + gc.get(5);
        return dat;
    }
    /**
     * Il metodo createFromDB crea un oggetto dal Database prendendo come input
     * un intero che rappresenta l'ID. L'ID è la chiave primaria delle tabelle 
     * corrispondenti alle classi che implementano questa interfaccia. Usa il 
     * metodo retrieve() per recuperare la Map dalla cui poi si crea l'oggetto.
     * @param id L'ID del volo da cercare
     * @param gc La data di decollo del volo
     * @throws SQLException 
     */
    public void createFromDB(int id, GregorianCalendar gc) throws SQLException {
        Map<String,Object> map = retrieve(id, gc);
        Route r = (Route) map.get("route");
        String nid = (String) map.get("ID");
        GregorianCalendar dat = (GregorianCalendar) map.get("date");
        Employer[] e = (Employer[]) map.get("crew");
        int s = (int) map.get("seats");
        this.setRoute(r);
        this.setID(nid);
        this.setDate(dat);
        this.setCrew(e);
        this.setSeats(s);
        
    }
    public Employer[] getEmployersFromDB(int id, GregorianCalendar gc) throws SQLException, IllegalArgumentException {
        FConnect con = new FConnect();
        String[] keys = {"idvolo","decollovolo"};
        String dat = getStringFromDate(gc);
        String[] conds = {Integer.toString(id),dat};
        ResultSet rs = con.search("lavoripassati", keys, conds);
        rs.next();
        if(rs.getRow() == 0) {
            throw new IllegalArgumentException("Nessun risultato per l'id selezionato");
        }
        int e1 = Integer.parseInt(rs.getString(3));
        int e2 = Integer.parseInt(rs.getString(4));
        int e3 = Integer.parseInt(rs.getString(5));
        int e4 = Integer.parseInt(rs.getString(6));
        int e5 = Integer.parseInt(rs.getString(7));
        Employer pilot = new Employer();
        Employer copilot = new Employer();
        Employer hostess1 = new Employer();
        Employer hostess2 = new Employer();
        Employer hostess3 = new Employer();
        pilot.createFromDB(e1);
        copilot.createFromDB(e2);
        hostess1.createFromDB(e3);
        hostess2.createFromDB(e4);
        hostess3.createFromDB(e5);
        Employer[] e = {pilot,copilot,hostess1,hostess2,hostess3};
        con.close();
        return e;
    }
    public static int getRouteID(int id) throws SQLException {
        FConnect con = new FConnect();
        ResultSet rs = con.load("volo", "id = " + id);
        //System.out.println(rs.getRow());
        int rt = 0;
        while(rs.next())
             rt = rs.getInt(3);
        con.close();
        return rt;
    }
    public static ArrayList getFlightsFromRouteID(int id) throws SQLException {
        FConnect con = new FConnect();
        ResultSet rs = con.load(table, "idtratta = " + id);
        ArrayList list = new ArrayList();
        HashMap<String,Object> map;
        while(rs.next()) {
            map = new HashMap<>();
            map.put("ID", Integer.toString(rs.getInt(1)));
            map.put("date", rs.getString(2));
            map.put("seats", rs.getInt(4));
            list.add(map);
        }
        //System.out.println(list.size());
        if(list.isEmpty()) {
            con.close();
            throw new SQLException("Nessun risultato trovato per la tratta richiesta");
        }
        con.close();
        return list;
    }
    public Map retrieve(int id, GregorianCalendar gc) throws SQLException {
        FConnect con = new FConnect();
        String[] cond = {Integer.toString(id),getStringFromDate(gc)};
        ResultSet rs = con.search(table, key, cond);
        Map<String,Object> map = new HashMap<>();
        while(rs.next()) {
            map = new HashMap<>();
            Route r = new Route();
            Employer[] e = getEmployersFromDB(id, gc);
            r.createFromDB(getRouteID(id));
            map.put("route", r);
            map.put("ID", Integer.toString(rs.getInt(1)));
            map.put("date", getDateFromString(rs.getString(2)));
            map.put("crew", e);
            map.put("seats", rs.getInt(4));
        }
        con.close();
        return map;
    }
    public void store() throws SQLException {
        FConnect con = new FConnect();
        String[] val = {this.ID,this.getDateString()};
        if(!con.existsMultipleKey(table, key, val)) {        
            ArrayList<String> values = new ArrayList<>();
            values.add(this.ID);
            values.add(this.getDateString());
            values.add(Integer.toString(this.route.getIdFromDB()));
            values.add(Integer.toString(seats));
            con.store(table, values);    
        }
        else {
            con.close();
            System.out.println("volo con stesse keys già presente nel db GESTIRE ERROR FRAME Flight.store()");
        }
        con.close();
    }
    /* attributi */
    private Route route;
    private String ID;
    private Employer[] crew;
    private int seats;
    private GregorianCalendar date;
    /* attributi di classe (per il db) */
    private static final String table = "volo";
    private static final String[] key = {"id","decollo"};
}